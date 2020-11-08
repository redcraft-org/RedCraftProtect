package org.redcraft.redcraftprotect.models.world;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.redcraft.redcraftprotect.RedCraftProtect;

import java.util.*;


public class ProtectedElements {
    public HashMap<Location, ProtectedElement> elements = new HashMap<>();


    public boolean isBlockBreakable(Block block, UUID breaker) {
        if (!RedCraftProtect.getInstance().protectedBlocks.contains(block.getType())) {
            return true;
        }
        ProtectedElement protectedElement = this.get(block.getLocation());
        if (protectedElement != null && protectedElement.isBreakableBy(breaker)) {
            return true;
        }
        return false;
    }

    public void add(ProtectedElement protectedElement) {
        this.elements.put(protectedElement.location, protectedElement);
    }

    public ProtectedElement get(Location location) {
        return this.elements.getOrDefault(location, null);
    }

    public ProtectedElement get(Block block) {
        return this.elements.getOrDefault(block.getLocation(), null);
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
        ArrayList<UUID> players = new ArrayList<>();
        players.addAll(protectedElement.trusted);
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
        return !Collections.disjoint(Arrays.asList(players1), Arrays.asList(players2));
    }

    public ArrayList<ProtectedElement> getAll() {
        return new ArrayList<ProtectedElement>(this.elements.values());
    }
}
