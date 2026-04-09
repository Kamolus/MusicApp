package com.springmusicapp.domain.contract.service;

import com.springmusicapp.core.base.InvitationStatus;
import com.springmusicapp.core.exception.BusinessLogicException;
import com.springmusicapp.core.exception.ResourceNotFoundException;
import com.springmusicapp.domain.band.Band;
import com.springmusicapp.domain.band.BandRepository;
import com.springmusicapp.domain.contract.dto.ContractDTO;
import com.springmusicapp.domain.contract.dto.ContractNegotiationDTO;
import com.springmusicapp.domain.contract.dto.CounterOfferDTO;
import com.springmusicapp.domain.contract.dto.CreateContractDTO;
import com.springmusicapp.domain.contract.mapper.ContractMapper;
import com.springmusicapp.domain.contract.model.Contract;
import com.springmusicapp.domain.contract.model.ContractNegotiation;
import com.springmusicapp.domain.contract.repository.ContractNegotiationRepository;
import com.springmusicapp.domain.label.model.BandManager;
import com.springmusicapp.domain.label.repository.BandManagerRepository;
import com.springmusicapp.domain.musician.Musician;
import com.springmusicapp.domain.musician.MusicianRepository;
import com.springmusicapp.domain.user.model.User;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import org.springframework.security.access.AccessDeniedException;
import java.util.UUID;

@Service
public class ContractNegotiationService {

    private final ContractNegotiationRepository negotiationRepository;
    private final BandRepository bandRepository;
    private final BandManagerRepository managerRepository;
    private final MusicianRepository musicianRepository;
    private final ContractService contractService;

    public ContractNegotiationService(
            ContractNegotiationRepository negotiationRepository,
            BandRepository bandRepository,
            BandManagerRepository managerRepository,
            MusicianRepository musicianRepository,
            ContractService contractService) {
        this.negotiationRepository = negotiationRepository;
        this.bandRepository = bandRepository;
        this.managerRepository = managerRepository;
        this.musicianRepository = musicianRepository;
        this.contractService = contractService;
    }

    @Transactional
    public ContractNegotiationDTO createOffer(CreateContractDTO dto, String loggedInManagerId) {
        BandManager manager = managerRepository.findById(loggedInManagerId)
                .orElseThrow(() -> new ResourceNotFoundException("Manager", "id", loggedInManagerId));

        Band band = bandRepository.findById(dto.bandId())
                .orElseThrow(() -> new ResourceNotFoundException("Band", "id", dto.bandId()));

        ContractNegotiation negotiation = new ContractNegotiation();

        negotiation.setManager(manager);
        negotiation.setBand(band);

        negotiation.setProposedDuration(dto.proposedDurationInMonths());
        negotiation.setProposedBasePayment(dto.proposedBasePayment());
        negotiation.setProposedPayPerPerformance(dto.proposedPayPerPerformance());

        negotiation.setStatus(InvitationStatus.AWAITING_BAND_APPROVAL);

        return toDto(negotiationRepository.save(negotiation));
    }

    @Transactional
    public ContractNegotiationDTO counterOfferByBand(UUID negotiationId, CounterOfferDTO dto, String loggedInMusicianId) {
        ContractNegotiation negotiation = negotiationRepository.findById(negotiationId)
                .orElseThrow(() -> new ResourceNotFoundException("Negotiation", "id", negotiationId));

        Musician musician = musicianRepository.findById(loggedInMusicianId)
                .orElseThrow(() -> new ResourceNotFoundException("Musician", "id", loggedInMusicianId));

        if (musician.getCurrentBand() == null || !negotiation.getBand().getId().equals(musician.getCurrentBand().getId())) {
            throw new AccessDeniedException("You can only negotiate contracts for your own band");
        }

        if (negotiation.getStatus() != InvitationStatus.AWAITING_BAND_APPROVAL) {
            throw new BusinessLogicException("Negotiations have been completed or the manager has not responded yet","ERR_CLOSED_OR_MANAGER_NOT_APPROVED");
        }

        negotiation.applyCounterOffer(
                dto.proposedDurationInMonths(),
                dto.proposedBasePayment(),
                dto.proposedPayPerPerformance(),
                InvitationStatus.AWAITING_MANAGER_APPROVAL
        );

        return toDto(negotiationRepository.save(negotiation));
    }

    @Transactional
    public ContractDTO acceptNegotiation(UUID negotiationId) {
        ContractNegotiation negotiation = negotiationRepository.findById(negotiationId)
                .orElseThrow(() -> new ResourceNotFoundException("Negotiation", "id", negotiationId));

        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        Musician currentMusician = musicianRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Musician", "email", currentUserEmail));

        if (currentMusician.getCurrentBand() == null ||
                !currentMusician.getCurrentBand().getId().equals(negotiation.getBand().getId())) {
            throw new AccessDeniedException("You don't have permission to accept this negotiation");
        }

        negotiation.accept();
        negotiationRepository.save(negotiation);

        Contract newContract = contractService.createContractFromNegotiation(negotiation);

        return ContractMapper.toDTO(newContract);
    }

    public void removeContractOffer(UUID id){
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String currentUserId = jwt.getSubject();

        BandManager bandManager = managerRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Band manager", "id", currentUserId));

        ContractNegotiation negotiation = negotiationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Negotiation", "id", id));

        if(negotiation.getManager() == null || !negotiation.getManager().getId().equals(currentUserId)){
            throw new AccessDeniedException("You don't have permission to cancel this negotiation");
        }

        if (!negotiation.getStatus().equals(InvitationStatus.PENDING)) {
            throw new BusinessLogicException("Offer has already been accepted or rejected", "ERR_OFFER_ALREADY_CONSIDERED");
        }

        negotiationRepository.deleteById(id);
    }

    private ContractNegotiationDTO toDto(ContractNegotiation n) {
        if (n == null) return null;

        return new ContractNegotiationDTO(
                n.getId(), n.getManager().getId(), n.getBand().getId(),
                n.getProposedDuration(), n.getProposedBasePayment(),
                n.getProposedPayPerPerformance(), n.getStatus()
        );
    }
}
