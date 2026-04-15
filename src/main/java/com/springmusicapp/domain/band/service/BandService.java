package com.springmusicapp.domain.band.service;

import com.springmusicapp.core.base.InvitationStatus;
import com.springmusicapp.core.exception.BusinessLogicException;
import com.springmusicapp.core.exception.ResourceNotFoundException;
import com.springmusicapp.domain.band.dto.BandDTO;
import com.springmusicapp.domain.band.dto.CreateBandDTO;
import com.springmusicapp.domain.band.invite.BandInvitationCreatedEvent;
import com.springmusicapp.domain.band.invite.InviteMusicianRequest;
import com.springmusicapp.domain.band.mapper.BandMapper;
import com.springmusicapp.domain.band.model.Band;
import com.springmusicapp.domain.band.model.BandInvitation;
import com.springmusicapp.domain.band.model.BandMembership;
import com.springmusicapp.domain.band.model.BandRole;
import com.springmusicapp.domain.band.repository.BandInvitationRepository;
import com.springmusicapp.domain.band.repository.BandRepository;
import com.springmusicapp.domain.musician.Musician;
import com.springmusicapp.domain.musician.MusicianRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class BandService{

    private final BandRepository bandRepository;
    private final MusicianRepository musicianRepository;
    private final BandInvitationRepository invitationRepository;
    private final ApplicationEventPublisher eventPublisher;

    public BandService(BandRepository repository, MusicianRepository musicianRepository,
                       BandInvitationRepository invitationRepository, ApplicationEventPublisher eventPublisher) {
        this.bandRepository = repository;
        this.musicianRepository = musicianRepository;
        this.invitationRepository = invitationRepository;
        this.eventPublisher = eventPublisher;
    }

    public BandDTO findById(UUID id) {
        Band band = bandRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Band", "id", id));
        return BandMapper.toDto(band);
    }

    public List<BandDTO> findByName(String name) {
        List<Band> bands = bandRepository.findByName(name);
        return bands.stream().map(BandMapper::toDto).toList();
    }

    @PreAuthorize("hasRole('APP_ADMIN')")
    @Transactional
    public void assignMusician(UUID bandId, String targetMusicianId) {
        Band band = bandRepository.findById(bandId)
                .orElseThrow(() -> new ResourceNotFoundException("Band", "id", bandId.toString()));

        Musician targetMusician = musicianRepository.findById(targetMusicianId)
                .orElseThrow(() -> new ResourceNotFoundException("Musician", "id", targetMusicianId));

        boolean isAlreadyMember = band.getMemberships().stream()
                .anyMatch(m -> m.getMusician().getId().equals(targetMusicianId));

        if (isAlreadyMember) {
            throw new BusinessLogicException("This musician is already in the band", "ERR_ALREADY_MEMBER");
        }

        band.addMusician(targetMusician, BandRole.MEMBER);
        bandRepository.save(band);
    }

    @PreAuthorize("hasRole('APP_ADMIN')")
    @Transactional
    public void deleteBandByAdmin(UUID bandId) {
        Band band = bandRepository.findById(bandId)
                .orElseThrow(() -> new ResourceNotFoundException("Band", "id", bandId.toString()));

        bandRepository.delete(band);
    }

    @Transactional
    public void disbandTeam(UUID bandId) {
        Band band = bandRepository.findById(bandId)
                .orElseThrow(() -> new ResourceNotFoundException("Band", "id", bandId.toString()));

        BandMembership currentUserMembership = getCurrentUserMembershipInBand(band);

        if (currentUserMembership.getRole() != BandRole.FOUNDER) {
            throw new BusinessLogicException("Only the FOUNDER can disband the team", "ERR_INSUFFICIENT_PERMISSIONS");
        }

        bandRepository.delete(band);
    }

    @Transactional
    public void kickMember(UUID bandId, String targetMusicianId) {
        Band band = bandRepository.findById(bandId)
                .orElseThrow(() -> new ResourceNotFoundException("Band", "id", bandId.toString()));

        BandMembership executerMembership = getCurrentUserMembershipInBand(band);

        BandMembership targetMembership = band.getMemberships().stream()
                .filter(m -> m.getMusician().getId().equals(targetMusicianId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Member", "id", targetMusicianId));

        if (executerMembership.getRole() == BandRole.MEMBER) {
            throw new BusinessLogicException("Members cannot kick other users", "ERR_INSUFFICIENT_PERMISSIONS");
        }

        if (executerMembership.getRole() == BandRole.ADMIN && targetMembership.getRole() != BandRole.MEMBER) {
            throw new BusinessLogicException("Admins can only kick regular members", "ERR_INSUFFICIENT_PERMISSIONS");
        }

        if (targetMembership.getRole() == BandRole.FOUNDER) {
            throw new BusinessLogicException("Cannot kick the FOUNDER", "ERR_CANNOT_KICK_FOUNDER");
        }

        band.getMemberships().remove(targetMembership);

        bandRepository.save(band);
    }

    @Transactional
    public void inviteMusician(UUID bandId, InviteMusicianRequest request) {
        Band band = bandRepository.findById(bandId)
                .orElseThrow(() -> new ResourceNotFoundException("Band", "id", bandId.toString()));

        Musician targetMusician = musicianRepository.findById(request.targetMusicianId())
                .orElseThrow(() -> new ResourceNotFoundException("Musician", "id", request.targetMusicianId()));

        BandMembership executerMembership = getCurrentUserMembershipInBand(band);

        if (executerMembership.getRole() == BandRole.MEMBER) {
            throw new BusinessLogicException("Only Admins and Founders can invite new members", "ERR_INSUFFICIENT_PERMISSIONS");
        }

        boolean isAlreadyMember = band.getMemberships().stream()
                .anyMatch(m -> m.getMusician().getId().equals(request.targetMusicianId()));

        if (isAlreadyMember) {
            throw new BusinessLogicException("This musician is already in the band", "ERR_ALREADY_MEMBER");
        }

        boolean hasPendingInvitation = invitationRepository.existsByBandAndInviteeAndStatus(
                band, targetMusician, InvitationStatus.PENDING
        );

        if (hasPendingInvitation) {
            throw new BusinessLogicException("Musician already has a pending invitation to this band", "ERR_INVITATION_PENDING");
        }

        BandInvitation invitation = new BandInvitation(band, targetMusician, request.message());
        invitation = invitationRepository.save(invitation);

        eventPublisher.publishEvent(new BandInvitationCreatedEvent(
                invitation.getId(),
                targetMusician.getId(),
                band.getId(),
                band.getName(),
                request.message()
        ));
    }

    public BandDTO createBand(CreateBandDTO dto) {
        Band band = BandMapper.toEntity(dto);

        Band savedBand = bandRepository.save(band);

        return BandMapper.toDto(savedBand);
    }

    public List<BandDTO> findAll() {
        return bandRepository.findAll().stream().map(BandMapper::toDto).toList();
    }

    private BandMembership getCurrentUserMembershipInBand(Band band) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentUserId = jwt.getSubject();

        return band.getMemberships().stream()
                .filter(m -> m.getMusician().getId().equals(currentUserId))
                .findFirst()
                .orElseThrow(() -> new BusinessLogicException("Access Denied", "ERR_NOT_IN_BAND"));
    }
}
