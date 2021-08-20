package org.redcraft.redcraftprotect.models;

import java.util.BitSet;

public class Permissions {
    private BitSet permissions;

    public Permissions() {
        int numberBits = Permission.values().length * PermissionUser.values().length;
        this.permissions = new BitSet(numberBits);
    }

    public Permissions(BitSet permissions) {
        this.permissions = permissions;
    }

    private int getOffset(Permission permission, PermissionUser user) {
        int userOffset = user.getUserOffset() << 2;
        int permissionOffset = permission.getPermissionOffset();
        return userOffset + permissionOffset;
    }

    public void setPermission(Permission permission, PermissionUser user, boolean value) {
        int offset = getOffset(permission, user);
        this.permissions.set(offset);
    }

    public void addPermissions(Permission permission, PermissionUser user) {
        this.setPermission(permission, user, true);
    }

    public void removePermissions(Permission permission, PermissionUser user) {
        this.setPermission(permission, user, false);
    }

    public boolean hasPermission(Permission permission, PermissionUser user) {
        int offset = getOffset(permission, user);
        return this.permissions.get(offset);
    }

    public BitSet getPermissions() {
        return this.permissions;
    }

    public void setPermissions(BitSet permissions) {
        this.permissions = permissions;
    }
}