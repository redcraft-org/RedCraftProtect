package org.redcraft.redcraftprotect.models.world;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.redcraft.redcraftprotect.RedCraftProtect;
import org.redcraft.redcraftprotect.RedCraftProtectFriend;
import org.redcraft.redcraftprotect.RedCraftProtectFriends;
import org.redcraft.redcraftprotect.utils.BeaconUtils;
import org.redcraft.redcraftprotect.utils.ProtectedInteractionResult;

import java.util.*;


public class ProtectedElements {
    public HashMap<Location, ProtectedElement> elements = new HashMap<>();

    public ProtectedInteractionResult getInteractionResult(Block block) {
        return getInteractionResult(block, Permission.BREAK);
    }


    public ProtectedInteractionResult getInteractionResult(Block block, UUID breaker) {
        return getInteractionResult(block, breaker, Permission.BREAK);
    }

    public ProtectedInteractionResult getInteractionResult(Block block, Permission neededPermission) {
        return getInteractionResult(block, null, neededPermission);
    }

    public ProtectedInteractionResult getInteractionResult(Block block, UUID breaker, Permission neededPermission) {
        if (BeaconUtils.beaconBlocks.contains(block.getType())) {
            return BeaconUtils.getInteractionResult(block, breaker);
        }
        if (!RedCraftProtect.getInstance().protectedBlocks.contains(block.getType())) {
            return new ProtectedInteractionResult(Permission.BREAK, breaker);
        }
        Location location = block.getLocation();
        ProtectedElement protectedElement = this.get(location);
        if (protectedElement == null) {

            return new ProtectedInteractionResult(Permission.BREAK, breaker);
        }
        String playerName = Bukkit.getServer().getOfflinePlayer(protectedElement.owner.player).getName();
        String errorString = neededPermission.getPermissionErrorString();

        if (block.getType().equals(Material.BEACON)) {
            errorString = BeaconUtils.getBeaconError(location, playerName);
        }
        Permission permission = protectedElement.getPermissionsForPlayer(breaker);
        return new ProtectedInteractionResult(permission, breaker, errorString);
    }

    public boolean isBlockBreakable(Block block, UUID breaker) {
        if (BeaconUtils.beaconBlocks.contains(block.getType())) {
            if (breaker == null) {
                return BeaconUtils.isBlockBreakable(block);
            }
            return BeaconUtils.isBlockBreakableByPlayer(block, breaker);
        }
        if (!RedCraftProtect.getInstance().protectedBlocks.contains(block.getType())) {
            return true;
        }

        ProtectedElement protectedElement = this.get(block.getLocation());

        return protectedElement != null && protectedElement.isBreakableBy(breaker);
    }

    public void add(ProtectedElement protectedElement) {
        this.elements.put(protectedElement.location, protectedElement);
    }

    public ProtectedElement get(Location location) {
        return this.elements.getOrDefault(location, null);
    }

    public ProtectedElement get(Block block) {
        return this.get(block.getLocation());
    }

    public RedCraftProtectFriends getFriends(Location location) {
        ProtectedElement protectedElement = this.get(location);
        if (protectedElement == null) {
            return new RedCraftProtectFriends();
        }
        return protectedElement.friends.add(protectedElement.friends);
    }

    public void remove(Location location) {
        this.elements.remove(location);
    }

    public void remove(Block block) {
        this.elements.remove(block.getLocation());
    }

    public boolean canBlocksInteract(Location location1, Location location2) {
        return canBlocksInteract(location1, location2, Permission.EDIT);
    }

    public boolean canBlocksInteract(Location location1, Location location2, Permission permission) {
        ProtectedElement block1 = this.get(location1);
        ProtectedElement block2 = this.get(location2);
        if (block1 == null || block2 == null) {
            return true;
        }
        // Checks if both owners are same
        if (block1.owner.player.equals(block2.owner.player)) {
            return true;
        }

        RedCraftProtectFriend owner1 = new RedCraftProtectFriend(block1.owner, Permission.BREAK);
        RedCraftProtectFriend owner2 = new RedCraftProtectFriend(block2.owner, Permission.BREAK);
        RedCraftProtectFriends friends1 = this.getFriends(location1).add(owner1).filter(permission);
        RedCraftProtectFriends friends2 = this.getFriends(location2).add(owner2).filter(permission);
        return !Collections.disjoint(List.of(friends1.friendList), List.of(friends2.friendList));
    }

    public ArrayList<ProtectedElement> getAll() {
        return new ArrayList<>(this.elements.values());
    }
}
