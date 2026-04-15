package com.springmusicapp.domain.band.invite;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record InviteMusicianRequest(
        @Schema(description = "The Keycloak ID of the musician to be invited")
        @NotBlank(message = "Musician ID cannot be blank")
        String targetMusicianId,
        @Schema(description = "Optional welcome message attached to the invitation")
        String message
) {
}
