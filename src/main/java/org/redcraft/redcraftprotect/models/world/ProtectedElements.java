package org.redcraft.redcraftprotect.models.world;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.redcraft.redcraftprotect.RedCraftProtect;
import org.redcraft.redcraftprotect.utils.BeaconUtils;
import org.redcraft.redcraftprotect.utils.ProtectedInteractionResult;

import java.util.*;


public class ProtectedElements {
    public HashMap<Location, ProtectedElement> elements = new HashMap<>();

    public ProtectedInteractionResult getInteractionResult(Block block) {
        return getInteractionResult(block, null);
    }

    public ProtectedInteractionResult getInteractionResult(Block block, UUID breaker) {
        if (BeaconUtils.beaconBlocks.contains(block.getType())) {
            return BeaconUtils.getInteractionResult(block, breaker);
        }
        if (!RedCraftProtect.getInstance().protectedBlocks.contains(block.getType())) {
            return new ProtectedInteractionResult(ProtectedElement.Permission.BREAK, breaker);
        }
        Location location = block.getLocation();
        ProtectedElement protectedElement = this.get(location);
        String playerName = Bukkit.getServer().getOfflinePlayer(protectedElement.owner.player).getName();

        if (protectedElement == null) {
            return new ProtectedInteractionResult(ProtectedElement.Permission.BREAK, breaker);
        }

        String errorString = "You can't break this block";

        if (block.getType().equals(Material.BEACON)) {
            errorString = BeaconUtils.getBeaconError(location, playerName);
        }
        return new ProtectedInteractionResult(protectedElement.getPermissionsForPlayer(breaker), breaker, errorString);
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


    public ArrayList<UUID> getPlayersAdded(Location location) {
        ProtectedElement protectedElement = this.get(location);
        if (protectedElement == null) {
            return new ArrayList<>();
        }
        ArrayList<UUID> players = new ArrayList<>();
        players.addAll(protectedElement.trusted);
        players.addAll(protectedElement.added);
        players.add(protectedElement.owner.player);
        return players;
    }

    public ArrayList<UUID> getPlayersTrusted(Location location) {
        ProtectedElement protectedElement = this.get(location);
        if (protectedElement == null) {
            return new ArrayList<>();
        }
        ArrayList<UUID> players = new ArrayList<>(protectedElement.trusted);
        players.add(protectedElement.owner.player);
        return players;
    }

    public void remove(Location location) {
        this.elements.remove(location);
    }

    public void remove(Block block) {
        this.elements.remove(block.getLocation());
    }

    public boolean canBlocksInteract(Location location1, Location location2) {

        if (this.get(location1) == null && this.get(location2) == null) {
            return true;
        }
        ArrayList<UUID> players1 = this.getPlayersAdded(location1);
        ArrayList<UUID> players2 = this.getPlayersAdded(location2);
        return !Collections.disjoint(List.of(players1), List.of(players2));
    }

    public ArrayList<ProtectedElement> getAll() {
        return new ArrayList<>(this.elements.values());
    }
}
