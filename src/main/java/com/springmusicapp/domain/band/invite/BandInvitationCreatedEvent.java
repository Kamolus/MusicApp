package com.springmusicapp.domain.band.invite;

import java.util.UUID;

public record BandInvitationCreatedEvent(
        UUID invitationId,
        String inviteeId,
        UUID bandId,
        String bandName,
        String message
) {
}
