package com.springmusicapp.domain.band.service;

import com.springmusicapp.core.base.InvitationStatus;
import com.springmusicapp.core.exception.BusinessLogicException;
import com.springmusicapp.core.exception.ResourceNotFoundException;
import com.springmusicapp.domain.band.dto.BandInvitationDTO;
import com.springmusicapp.domain.band.mapper.BandInvitationMapper;
import com.springmusicapp.domain.band.repository.BandInvitationRepository;
import com.springmusicapp.domain.band.model.BandInvitation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class BandInvitationService {

    private final BandInvitationRepository invitationRepository;
    private final BandInvitationMapper invitationMapper;

    public BandInvitationService(BandInvitationRepository invitationRepository, BandInvitationMapper invitationMapper) {
        this.invitationRepository = invitationRepository;
        this.invitationMapper = invitationMapper;
    }

    private BandInvitation getInvitationAndVerifyOwnership(UUID invitationId) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentUserId = jwt.getSubject();

        BandInvitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new ResourceNotFoundException("Invitation", "id", invitationId.toString()));

        if (!invitation.getInvitee().getId().equals(currentUserId)) {
            throw new BusinessLogicException("You are not the recipient of this invitation", "ERR_ACCESS_DENIED");
        }

        return invitation;
    }

    @Transactional
    public void acceptInvitation(UUID invitationId) {
        BandInvitation invitation = getInvitationAndVerifyOwnership(invitationId);

        try {
            invitation.accept();
        } catch (IllegalStateException e) {
            throw new BusinessLogicException(e.getMessage(), "ERR_INVALID_INVITATION_STATE");
        }

        invitationRepository.save(invitation);
    }

    @Transactional
    public void rejectInvitation(UUID invitationId) {
        BandInvitation invitation = getInvitationAndVerifyOwnership(invitationId);

        try {
            invitation.reject();
        } catch (IllegalStateException e) {
            throw new BusinessLogicException(e.getMessage(), "ERR_INVALID_INVITATION_STATE");
        }

        invitationRepository.save(invitation);
    }

    public List<BandInvitationDTO> getMyPendingInvitations() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentUserId = jwt.getSubject();

        return invitationRepository.findAllByInviteeIdAndStatus(currentUserId, InvitationStatus.PENDING)
                .stream().map(invitationMapper::toDto).toList();
    }
}