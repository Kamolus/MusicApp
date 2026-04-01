package com.springmusicapp.domain.band;

import com.springmusicapp.core.base.BaseInvitation;
import com.springmusicapp.core.base.InvitationStatus;
import com.springmusicapp.domain.musician.Musician;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "band_invitations")
public class BandInvitation extends BaseInvitation {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "band_id", nullable = false)
    private Band band;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invitee_id", nullable = false)
    private Musician invitee;

    public BandInvitation() {}

    public BandInvitation(Band band, Musician invitee) {
        this.band = band;
        this.invitee = invitee;
    }

    @Override
    public void accept() {
        if (getStatus() != InvitationStatus.PENDING) {
            throw new IllegalStateException("Invitation is no longer pending");
        }

        if (invitee.isAvailable()) {
            band.addMusician(invitee);
            setStatus(InvitationStatus.ACCEPTED);
            updateTimestamp();
        } else {
            throw new IllegalStateException("Musician is already in another band");
        }
    }

    @Override
    public void reject() {
        if (getStatus() != InvitationStatus.PENDING) {
            throw new IllegalStateException("Invitation is no longer pending");
        }
        setStatus(InvitationStatus.REJECTED);
        updateTimestamp();
    }
}
