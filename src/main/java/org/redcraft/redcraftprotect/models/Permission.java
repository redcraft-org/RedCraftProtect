package org.redcraft.redcraftprotect.models;

public enum Permission {
    BREAK(3),
    REMOVE(2),
    ADD(1),
    OPEN(0);

    private final Integer permissionLevel;

    Permission(int permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    public static String getPermissionErrorString(Permission permission) {
        switch (permission) {
            case BREAK -> {
                return "You can't edit this block";
            }
            case REMOVE -> {
                return "You can't remove items from this block";
            }
            case ADD -> {
                return "You can't add items to this block";
            }
            case OPEN -> {
                return "You can't open this block";
            }
            default -> {
                return "You can't break this block";
            }
        }
    }

    public String getPermissionErrorString() {
        return getPermissionErrorString(this);
    }

    public boolean isHigherOrEqual(Permission other) {
        return this.permissionLevel >= other.permissionLevel;
    }

    public int getPermissionOffset() {
        return this.permissionLevel;
    }
}