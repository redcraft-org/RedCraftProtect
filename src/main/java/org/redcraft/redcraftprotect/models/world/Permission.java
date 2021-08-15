package org.redcraft.redcraftprotect.models.world;

public enum Permission {
    BREAK(4),
    EDIT(3),
    OPEN(2),
    NONE(1);
    private final Integer permissionLevel;

    Permission(int permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    public static String getPermissionErrorString(Permission permission) {
        switch (permission) {
            case EDIT -> {
                return "You can't edit this block";
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


}
