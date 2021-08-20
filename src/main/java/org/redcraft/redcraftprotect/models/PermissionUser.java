package org.redcraft.redcraftprotect.models;

public enum PermissionUser {
    OWNER(2),
    GROUP(1),
    PUBLIC(0);

    private final Integer userOffset;

    PermissionUser(int userOffset) {
        this.userOffset = userOffset;
    }

    public int getUserOffset() {
        return this.userOffset;
    }
}
