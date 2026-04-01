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
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ContractNegotiationDTO createOffer(CreateContractDTO dto, UUID loggedInManagerId) {
        BandManager manager = managerRepository.findById(loggedInManagerId)
                .orElseThrow(() -> new IllegalArgumentException("Manager not found"));

        Band band = bandRepository.findById(dto.bandId())
                .orElseThrow(() -> new IllegalArgumentException("Band not found"));

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
    public ContractNegotiationDTO counterOfferByBand(UUID negotiationId, CounterOfferDTO dto, UUID loggedInMusicianId) {
        ContractNegotiation negotiation = negotiationRepository.findById(negotiationId)
                .orElseThrow(() -> new IllegalArgumentException("Negotiation not found"));

        Musician musician = musicianRepository.findById(loggedInMusicianId)
                .orElseThrow(() -> new IllegalArgumentException("Musician not found"));

        if (musician.getCurrentBand() == null || !negotiation.getBand().getId().equals(musician.getCurrentBand().getId())) {
            throw new AccessDeniedException("You can only negotiate contracts for your own band!");
        }

        if (negotiation.getStatus() != InvitationStatus.AWAITING_BAND_APPROVAL) {
            throw new BusinessLogicException("Negotiation is closed or manager don't answer yet","ERR_CLOSED_OR_MANAGER_NOT_APPROVED");
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
                .orElseThrow(() -> new IllegalArgumentException("Negotiation not found"));

        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        Musician currentMusician = musicianRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Musician", "email", currentUserEmail));

        if (currentMusician.getCurrentBand() == null ||
                !currentMusician.getCurrentBand().getId().equals(negotiation.getBand().getId())) {

            throw new AccessDeniedException("Access denied");
        }

        negotiation.accept();
        negotiationRepository.save(negotiation);

        Contract newContract = contractService.createContractFromNegotiation(negotiation);

        return ContractMapper.toDTO(newContract);
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
