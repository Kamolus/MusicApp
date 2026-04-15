package com.springmusicapp.security.keycloak;

import com.springmusicapp.core.base.UserDeletedEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/webhooks/keycloak")
public class KeycloakWebhookController {

    @Value("${app.keycloak.webhook.secret}")
    private String webhookSecret;

    private final ApplicationEventPublisher eventPublisher;

    public KeycloakWebhookController(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @PostMapping(value = {"/events", "/events/"})
    public ResponseEntity<String> handleKeycloakEvent(
            @RequestParam(value = "secret", required = false) String secret,
            @RequestBody Map<String, Object> payload) {

        if (secret == null || !webhookSecret.equals(secret)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong password");
        }

        String type = (String) payload.get("type");

        if ("USER-DELETE".equals(type)) {
            String resourcePath = (String) payload.get("resourcePath");

            if (resourcePath != null && resourcePath.startsWith("users/")) {
                String extractedId = resourcePath.replace("users/", "");
                eventPublisher.publishEvent(new UserDeletedEvent(extractedId));
            }
        }

        return ResponseEntity.ok("Accepted");
    }
}