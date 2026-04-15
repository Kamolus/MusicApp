package com.springmusicapp.security.keycloak;

import com.springmusicapp.core.base.UserRoleAssignmentEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class KeycloakRoleListener {

    private final KeycloakRoleService keycloakRoleService;

    public KeycloakRoleListener(KeycloakRoleService keycloakRoleService) {
        this.keycloakRoleService = keycloakRoleService;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onUserRoleAssignment(UserRoleAssignmentEvent event) {
        keycloakRoleService.assignRealmRole(event.userId(), event.roleName());
    }
}
