package org.redcraft.redcraftprotect.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.redcraft.redcraftprotect.RedCraftProtect;

public class BeaconUtils {

    public final static ArrayList<Material> beaconBlocks = new ArrayList<>(Arrays.asList(Material.IRON_BLOCK, Material.EMERALD, Material.GOLD_BLOCK, Material.DIAMOND_BLOCK, Material.NETHERITE_BLOCK));

    public static List<Block> geNearbyBeacons(Location location) {
        List<Chunk> nearbyChunks = LocationUtils.getNearbyChunks(location.getChunk(), 1);
        List<Block> nearbyBeacons = new ArrayList<>();
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

    public static boolean isPartOfBeaconStructure(Location blockLocation, Location beaconLocation) {
        Location normalized = blockLocation.subtract(beaconLocation);
        if (normalized.getBlockY() > -1 || normalized.getBlockY() < -4) {
            return false;
        }
        return (Math.abs(normalized.getBlockX()) <= -normalized.getBlockY() && Math.abs(normalized.getBlockZ()) <= -normalized.getBlockY());
    }

    public static boolean isBlockBreakableByPlayer(Block block, UUID breaker) {
        List<Block> nearbyBeacons = geNearbyBeacons(block.getLocation());
        for (Block nearbyBeacon : nearbyBeacons) {
            if (isPartOfBeaconStructure(block.getLocation(), nearbyBeacon.getLocation())) {
                if (!RedCraftProtect.getInstance().protectedElements.isBlockBreakable(nearbyBeacon, breaker)) {
                    Bukkit.getPlayer(breaker).sendMessage("This block is owned by " + Bukkit.getPlayer(RedCraftProtect.getInstance().protectedElements.get(block).owner.player).getDisplayName());
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isBlockBreakable(Block block) {
        List<Block> nearbyBeacons = geNearbyBeacons(block.getLocation());
        for (Block nearbyBeacon : nearbyBeacons) {
            if (isPartOfBeaconStructure(block.getLocation(), nearbyBeacon.getLocation())) {
                return false;
            }
        }
        return true;
    }

}
