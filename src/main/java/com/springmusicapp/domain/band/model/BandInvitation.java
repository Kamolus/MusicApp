package com.springmusicapp.domain.band.model;

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

    @Column(length = 500)
    private String message;

    public BandInvitation() {}

    public BandInvitation(Band band, Musician invitee, String message) {
        this.band = band;
        this.invitee = invitee;
        this.message = message;
    }

    @Override
    public void accept() {
        if (getStatus() != InvitationStatus.PENDING) {
            throw new IllegalStateException("Invitation is no longer pending");
        }

        BandMembership membership = new BandMembership(this.band, this.invitee, BandRole.MEMBER);
        this.band.getMemberships().add(membership);

        setStatus(InvitationStatus.ACCEPTED);
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
