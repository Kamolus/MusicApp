package com.springmusicapp.domain.band.dto;

import com.springmusicapp.core.base.InvitationStatus;

import java.util.UUID;

public record BandInvitationDTO(
        UUID invitationId,
        UUID bandId,
        String bandName,
        String message,
        InvitationStatus status
) {
}
