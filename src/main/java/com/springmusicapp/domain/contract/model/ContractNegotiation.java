package com.springmusicapp.domain.contract.model;

import com.springmusicapp.core.base.BaseInvitation;
import com.springmusicapp.core.base.InvitationStatus;
import com.springmusicapp.domain.band.model.Band;
import com.springmusicapp.domain.label.model.BandManager;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "contract_negotiations")
public class ContractNegotiation extends BaseInvitation {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private BandManager manager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "band_id")
    private Band band;

    private int proposedDuration;
    private double proposedBasePayment;
    private double proposedPayPerPerformance;

    @Override
    public void accept() {
        if (getStatus() == InvitationStatus.REJECTED || getStatus() == InvitationStatus.CANCELLED) {
            throw new IllegalStateException("Negotiation has been cancelled");
        }
        setStatus(InvitationStatus.ACCEPTED);
        updateTimestamp();
    }

    @Override
    public void reject() {
        if (getStatus() == InvitationStatus.ACCEPTED || getStatus() == InvitationStatus.CANCELLED) {
            throw new IllegalStateException("Negotiation has been cancelled");
        }
        setStatus(InvitationStatus.REJECTED);
        updateTimestamp();
    }

    public void applyCounterOffer(int duration, double basePayment, double payPerPerformance, InvitationStatus newStatus) {
        this.proposedDuration = duration;
        this.proposedBasePayment = basePayment;
        this.proposedPayPerPerformance = payPerPerformance;
        setStatus(newStatus);
        updateTimestamp();
    }

}
