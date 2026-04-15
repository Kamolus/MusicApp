package com.springmusicapp.core.base;

public record UserRoleAssignmentEvent(
        String userId,
        String roleName
) {
}
