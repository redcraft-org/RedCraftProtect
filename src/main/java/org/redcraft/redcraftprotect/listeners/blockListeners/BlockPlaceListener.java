package org.redcraft.redcraftprotect.listeners.blockListeners;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.redcraft.redcraftprotect.RedCraftProtect;
import org.redcraft.redcraftprotect.models.world.ProtectedElement;
import org.redcraft.redcraftprotect.utils.LocationUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockPlaceListener implements Listener {
    final ArrayList<Material> beaconBlocks = new ArrayList<>(Arrays.asList(Material.IRON_BLOCK, Material.GOLD_BLOCK, Material.DIAMOND_BLOCK, Material.NETHERITE_BLOCK));

    public List<Block> isPartOfBeacon(Block block) {
        List<Chunk> nearbyChunks = LocationUtils.getNearbyChunks(block.getChunk(), 1);
        List<Block> nearbyBeacons = new ArrayList<Block>();
        for (Chunk chunk : nearbyChunks) {
            BlockState[] tileEnTities = chunk.getTileEntities();
            for (BlockState blockState : tileEnTities) {
                if (blockState.getType().equals(Material.BEACON)) {
                    nearbyBeacons.add(blockState.getBlock());
                }
            }
        }
        return nearbyBeacons;
    }

    public boolean isPartOfBeaconStructure(Location blockLocation, Location beaconLocation) {
        Location normalized = blockLocation.subtract(beaconLocation);
        if (normalized.getBlockY() > -1 || normalized.getBlockY() < -4) {
            return false;
        }
        return (Math.abs(normalized.getBlockX()) <= -normalized.getBlockY() && Math.abs(normalized.getBlockZ()) <= -normalized.getBlockY());
    }


    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockPlace(BlockPlaceEvent event) {

        if (event.getBlock().getType().equals(Material.BEACON)) {
            return;
        }

        if (beaconBlocks.contains(event.getBlock().getType())) {
            return;
        }

        if (RedCraftProtect.getInstance().protectedBlocks.contains(event.getBlock().getType())) {
            ProtectedElement protectedElement = new ProtectedElement(event.getPlayer().getUniqueId(), event.getBlock().getLocation(), event.getBlock().getType());
            RedCraftProtect.getInstance().protectedElements.add(protectedElement);
            return;
        }
    }
}
