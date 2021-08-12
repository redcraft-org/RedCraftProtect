package org.redcraft.redcraftprotect.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.redcraft.redcraftprotect.models.world.ProtectedElement;

import java.util.UUID;

public class ProtectedInteractionResult {

    public String message;
    public ProtectedElement.Permission permission;
    public UUID playerUUID = null;
    public UUID ownerUUID = null;

    public ProtectedInteractionResult(ProtectedElement.Permission permission) {
        this.message = ProtectedElement.getPermissionErrorString(permission);
        this.permission = permission;
    }

    public ProtectedInteractionResult(ProtectedElement.Permission permission, UUID playerUUID) {
        this.message = "This block is unbreakable";
        this.permission = permission;
        this.playerUUID = playerUUID;
    }

    public ProtectedInteractionResult(ProtectedElement.Permission permission, UUID playerUUID, String message) {
        this.message = message;
        this.permission = permission;
        this.playerUUID = playerUUID;
    }

    public void sendMessage() {
        if (this.playerUUID == null && this.permission != ProtectedElement.Permission.NONE) {
            return;
        }
        Player player = Bukkit.getPlayer(this.playerUUID);
        if (player != null) {
            player.sendMessage("Permission " + this.permission.toString() + " " + this.message);
        }
    }

    public String getMessage() {
        return ProtectedElement.getPermissionErrorString(this.permission);
    }

    public boolean isBreakable() {
        return this.permission == ProtectedElement.Permission.BREAK;
    }

    public boolean isEditable() {
        return this.permission == ProtectedElement.Permission.EDIT || this.isBreakable();
    }

    public boolean isOpenable() {
        return this.permission == ProtectedElement.Permission.OPEN || this.isEditable();
    }


}
