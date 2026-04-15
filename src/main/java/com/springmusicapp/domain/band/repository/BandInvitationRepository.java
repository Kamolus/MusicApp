package com.springmusicapp.domain.band.repository;

import com.springmusicapp.core.base.InvitationStatus;
import com.springmusicapp.domain.band.model.Band;
import com.springmusicapp.domain.band.model.BandInvitation;
import com.springmusicapp.domain.musician.Musician;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BandInvitationRepository extends JpaRepository<BandInvitation, UUID> {
    boolean existsByBandAndInviteeAndStatus(Band band, Musician invitee, InvitationStatus status);
    List<BandInvitation> findAllByInviteeIdAndStatus(String inviteeId, InvitationStatus status);
}
