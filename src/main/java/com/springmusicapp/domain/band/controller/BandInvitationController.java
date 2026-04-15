package com.springmusicapp.domain.band.controller;

import com.springmusicapp.domain.band.dto.BandInvitationDTO;
import com.springmusicapp.domain.band.service.BandInvitationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/invitations")
public class BandInvitationController {

    private final BandInvitationService invitationService;

    public BandInvitationController(BandInvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @PostMapping("/{invitationId}/accept")
    public ResponseEntity<Void> acceptInvitation(@PathVariable UUID invitationId) {
        invitationService.acceptInvitation(invitationId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{invitationId}/reject")
    public ResponseEntity<Void> rejectInvitation(@PathVariable UUID invitationId) {
        invitationService.rejectInvitation(invitationId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get pending invitations for current user")
    @GetMapping("/me")
    public ResponseEntity<List<BandInvitationDTO>> getMyInvitations() {
        return ResponseEntity.ok(invitationService.getMyPendingInvitations());
    }
}