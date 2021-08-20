package org.redcraft.redcraftprotect.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.redcraft.redcraftprotect.models.Permission;

import java.util.UUID;

public class ProtectedInteractionResult {

    public String message;
    public Permission permission;
    public UUID playerUUID = null;

    public ProtectedInteractionResult(Permission permission) {
        this.message = Permission.getPermissionErrorString(permission);
        this.permission = permission;
    }

    public ProtectedInteractionResult(Permission permission, UUID playerUUID) {
        this.message = "This block is unbreakable";
        this.permission = permission;
        this.playerUUID = playerUUID;
    }

    public ProtectedInteractionResult(Permission permission, UUID playerUUID, String message) {
        this.message = message;
        this.permission = permission;
        this.playerUUID = playerUUID;
    }

    public void sendMessage() {
        if (this.playerUUID == null && this.permission != Permission.NONE) {
            return;
        }
        Player player = Bukkit.getPlayer(this.playerUUID);
        if (player != null) {
            player.sendMessage("Permission " + this.permission.toString() + " " + this.message);
        }
    }

    public String getMessage() {
        return Permission.getPermissionErrorString(this.permission);
    }

    public boolean isBreakable() {
        return this.permission == Permission.BREAK;
    }

    public boolean isEditable() {
        return this.permission == Permission.EDIT || this.isBreakable();
    }

    public boolean isInteractable() {
        return this.permission == Permission.OPEN || this.isEditable();
    }


}
