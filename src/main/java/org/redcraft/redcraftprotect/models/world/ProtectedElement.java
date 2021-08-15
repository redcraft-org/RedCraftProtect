package org.redcraft.redcraftprotect.models.world;

import org.bukkit.Location;
import org.bukkit.Material;
import org.redcraft.redcraftprotect.RedCraftProtect;
import org.redcraft.redcraftprotect.RedCraftProtectFriends;
import org.redcraft.redcraftprotect.RedCraftProtectUser;

import java.time.LocalDateTime;
import java.util.List;
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

    public static boolean isChestLikeContainer(Material block) {
        List<Material> containers = List.of(new Material[]{
                Material.CHEST,
                Material.TRAPPED_CHEST,
        });
        return containers.contains(block);
    }

    public static boolean isContainer(Material block) {
        List<Material> containers = List.of(new Material[]{
                Material.CHEST,
                Material.TRAPPED_CHEST,
                Material.HOPPER,
                Material.SHULKER_BOX,
                Material.BARREL,
                Material.FURNACE,
                Material.BEACON,
                Material.BLAST_FURNACE,
                Material.SMOKER
        });
        return containers.contains(block);
    }

    public Permission getPermissionsForPlayer(UUID player) {
        if (this.owner.player.equals(player)) {
            return Permission.BREAK;
        }
        if (this.friends.friendList.containsKey(player)) {
            return friends.get(player).permission;
        }
        if (this.owner.friends.friendList.containsKey(player)) {
            return friends.get(player).permission;
        }
        return Permission.NONE;
    }

    public boolean hasPermission(UUID player, Permission permission) {
        if (this.owner.player.equals(player)) {
            return true;
        }
        return this.friends.hasPermission(player, permission) || this.owner.friends.hasPermission(player, permission);
    }

    public boolean isBreakableBy(UUID player) {
        if (this.owner.player.equals(player)) {
            return true;
        }
        return this.hasPermission(player, Permission.BREAK) || this.owner.hasPermission(player, Permission.BREAK);
    }

    public boolean isInteractable(UUID player) {
        if (this.owner.player.equals(player)) {
            return true;
        }
        return this.hasPermission(player, Permission.OPEN) || this.owner.hasPermission(player, Permission.OPEN);
    }

    public boolean isEditable(UUID player) {
        if (this.owner.player.equals(player)) {
            return true;
        }
        return this.hasPermission(player, Permission.EDIT) || this.owner.hasPermission(player, Permission.EDIT);
    }

    public boolean isOwner(UUID player) {
        return player.equals(this.owner.player);
    }

}
