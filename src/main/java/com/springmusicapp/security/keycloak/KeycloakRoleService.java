package com.springmusicapp.security.keycloak;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class KeycloakRoleService {

    private final Keycloak keycloak;
    private final String realm;

    public KeycloakRoleService(Keycloak keycloak, @Value("${keycloak.realm}") String realm) {
        this.keycloak = keycloak;
        this.realm = realm;
    }

    public void assignRealmRole(String userId, String roleName) {
        RealmResource realmResource = keycloak.realm(realm);

        RoleRepresentation roleToAssign = realmResource.roles().get(roleName).toRepresentation();

        UserResource userResource = realmResource.users().get(userId);

        userResource.roles().realmLevel().add(Collections.singletonList(roleToAssign));

        System.out.println("Role assigned " + roleName + " to " + userId);
    }
}