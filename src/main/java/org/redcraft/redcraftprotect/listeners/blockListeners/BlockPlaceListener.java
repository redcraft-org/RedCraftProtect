package org.redcraft.redcraftprotect.listeners.blockListeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.redcraft.redcraftprotect.RedCraftProtect;
import org.redcraft.redcraftprotect.models.world.ProtectedElement;

import java.util.ArrayList;
import java.util.Arrays;

public class BlockPlaceListener implements Listener {
    final ArrayList<Material> beaconBlocks = new ArrayList<>(Arrays.asList(Material.IRON_BLOCK, Material.GOLD_BLOCK, Material.DIAMOND_BLOCK)); //Material.NETHERITE_BLOCK;

    public boolean isPartOfBeacon(Location block, Location beaconPosition) {
        Location normalized = block.subtract(beaconPosition);
        if (normalized.getBlockY() > -1 || normalized.getBlockY() < -4) {
            return false;
        }
        return (Math.abs(normalized.getBlockX()) <= -normalized.getBlockY() && Math.abs(normalized.getBlockZ()) <= -normalized.getBlockY());
    }


    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockPlace(BlockPlaceEvent event) {

        if (event.getBlock().equals(Material.BEACON)) {
            return;
        }

        if (beaconBlocks.contains(event.getBlock())) {
            return;
        }

        if (RedCraftProtect.getInstance().protectedBlocks.contains(event.getBlock().getType())) {
            ProtectedElement protectedElement = new ProtectedElement(event.getPlayer().getUniqueId(), event.getBlock().getLocation(), event.getBlock().getType());
            RedCraftProtect.getInstance().protectedElements.add(protectedElement);
            return;
        }
    }
}
