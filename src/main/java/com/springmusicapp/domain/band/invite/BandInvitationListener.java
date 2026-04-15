package com.springmusicapp.domain.band.invite;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class BandInvitationListener {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onBandInvitationCreated(BandInvitationCreatedEvent event) {

        System.out.println("BandInvitationCreatedEvent from " + event.bandName() + " to " + event.inviteeId());

    }
}