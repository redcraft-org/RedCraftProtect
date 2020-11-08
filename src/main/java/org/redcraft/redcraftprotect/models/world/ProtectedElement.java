package org.redcraft.redcraftprotect.models.world;

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

    public boolean isAllowed(UUID player) {
        return !(player == null || this.removed.contains(player));
    }

    public boolean isBreakableBy(UUID player) {
        return isAllowed(player) && (player == this.owner.player || this.owner.isTrustedFriend(player) || this.trusted.contains(player));
    }

    public boolean isInteractable(UUID player) {
        boolean canInteract = player == this.owner.player || this.owner.isTrustedFriend(player) || this.trusted.contains(player);
        canInteract = canInteract || this.owner.isAddedFriend(player) || this.added.contains(player);
        return isAllowed(player) && canInteract;
    }

    public boolean isOwner(UUID player) {
        return player == this.owner.player;
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


}
