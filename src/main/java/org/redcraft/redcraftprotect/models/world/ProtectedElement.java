package org.redcraft.redcraftprotect.models.world;

import org.bukkit.Location;
import org.bukkit.Material;
import org.redcraft.redcraftprotect.RedCraftProtect;
import org.redcraft.redcraftprotect.RedCraftProtectFriends;
import org.redcraft.redcraftprotect.RedCraftProtectUser;

import java.time.LocalDateTime;
import java.util.UUID;

public class ProtectedElement {

    public RedCraftProtectUser owner;
    public Location location;
    public Material block;
    public RedCraftProtectFriends friends;
    public LocalDateTime localDateTime;

    public ProtectedElement(UUID player, Location location, Material block) {
        this.owner = RedCraftProtect.getInstance().redCraftProtectUsers.getUser(player);
        this.location = location;
        this.block = block;
        this.friends = new RedCraftProtectFriends();
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

    public boolean hasPermission(UUID player, Permission permission) {
        if (this.owner.player.equals(player)) {
            return true;
        }
        return this.friends.hasPermission(player, permission);
    }

    public boolean isBreakableBy(UUID player) {
        if (this.owner.player.equals(player)) {
            return true;
        }
        return this.hasPermission(player, Permission.BREAK) && this.owner.hasPermission(player, Permission.BREAK);
    }

    public boolean isInteractable(UUID player) {
        if (this.owner.player.equals(player)) {
            return true;
        }
        return this.hasPermission(player, Permission.OPEN) && this.owner.hasPermission(player, Permission.OPEN);
    }

    public boolean isEditable(UUID player) {
        if (this.owner.player.equals(player)) {
            return true;
        }
        return this.hasPermission(player, Permission.EDIT) && this.owner.hasPermission(player, Permission.EDIT);
    }

    public boolean isOwner(UUID player) {
        return player.equals(this.owner.player);
    }

    public static boolean isChestLikeContainer(Material blockBellowType) {
        return blockBellowType.equals(Material.CHEST) ||
                blockBellowType.equals(Material.TRAPPED_CHEST);
    }


    public enum Permission {
        BREAK(4),
        EDIT(3),
        OPEN(2),
        NONE(1);
        private final Integer permissionLevel;

        Permission(int permissionLevel) {
            this.permissionLevel = permissionLevel;
        }

        public boolean isHigherOrEqual(Permission other) {
            return this.permissionLevel >= other.permissionLevel;
        }


    }

}
