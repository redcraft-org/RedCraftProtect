package org.redcraft.redcraftprotect.models.world;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.redcraft.redcraftprotect.RedCraftProtect;
import org.redcraft.redcraftprotect.RedCraftProtectUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class ProtectedElement {

    public RedCraftProtectUser owner;
    public Location location;
    public Material block;
    public ArrayList<UUID> trusted;
    public ArrayList<UUID> added;
    public ArrayList<UUID> removed;
    public LocalDateTime localDateTime;

    public ProtectedElement(UUID player, Location location, Material block) {
        this.owner = RedCraftProtect.getInstance().redCraftProtectUsers.getUser(player);
        this.location = location;
        this.block = block;
        this.trusted = new ArrayList<>();
        this.added = new ArrayList<>();
        this.removed = new ArrayList<>();
        this.localDateTime = LocalDateTime.now();
    }

    public static String getPermissionErrorString(Permission permission) {
        switch (permission) {
            case BREAK -> {
                return "";
            }
            case EDIT -> {
                return "You cannot break this block only edit it";
            }
            case OPEN -> {
                return "You cannot break this block only look at it";
            }
            default -> {
                return "You cannot break this block";
            }
        }
    }

    public Permission getPermissionsForPlayer(UUID player) {
        if (this.isBreakableBy(player)) {
            return Permission.BREAK;
        }
        if (this.isInteractable(player)) {
            return Permission.EDIT;
        }
        return Permission.NONE;
    }

    public boolean isAllowed(UUID player) {
        return !(player == null || this.removed.contains(player));
    }

    public boolean isBreakableBy(UUID player) {
        boolean isAllowed = isAllowed(player);
        return isAllowed && (player.equals(this.owner.player) || this.owner.isTrustedFriend(player) || this.trusted.contains(player));
    }

    public boolean isInteractable(UUID player) {
        boolean canInteract = player.equals(this.owner.player) || this.owner.isTrustedFriend(player) || this.trusted.contains(player);
        canInteract = canInteract || this.owner.isAddedFriend(player) || this.added.contains(player);
        return isAllowed(player) && canInteract;
    }

    public boolean isOwner(UUID player) {
        return player.equals(this.owner.player);
    }

    public boolean isTrusted(UUID player) {
        return this.trusted.contains(player);
    }

    public boolean isAdded(UUID player) {
        return this.trusted.contains(player);
    }

    public boolean isRemoved(UUID player) {
        return this.trusted.contains(player);
    }

    public void addTrusted(UUID player) {
        if (!this.isTrusted(player)) {
            this.trusted.add(player);
        }
    }

    public enum Permission {
        BREAK, EDIT, OPEN, NONE;
    }


}
