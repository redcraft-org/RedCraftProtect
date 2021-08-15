package org.redcraft.redcraftprotect.utils;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.redcraft.redcraftprotect.RedCraftProtect;
import org.redcraft.redcraftprotect.models.world.Permission;

import java.util.*;


public class BeaconUtils {

    public final static ArrayList<Material> beaconBlocks = new ArrayList<>(Arrays.asList(Material.IRON_BLOCK, Material.EMERALD, Material.GOLD_BLOCK, Material.DIAMOND_BLOCK, Material.NETHERITE_BLOCK));


    // TODO cache with location hashmap and don't forget to remove on break
    private static List<Block> getNearbyBeacons(Location location) {
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
        nearbyBeacons.sort(Comparator.comparing((Block block) -> block.getLocation().distance(location)));
        return nearbyBeacons;
    }

    public static Block getClosestNearbyBeacon(Block block) {
        return getClosestNearbyBeacon(block.getLocation());
    }

    public static Block getClosestNearbyBeacon(Location location) {
        List<Block> nearbyBeacons = getNearbyBeacons(location);
        if (nearbyBeacons.size() == 0) {
            return null;
        }
        Block closestBeacon = nearbyBeacons.get(0);
        if (!closestBeacon.getLocation().equals(location)) {
            return closestBeacon;
        }
        if (nearbyBeacons.size() == 1) {
            return null;
        }
        return nearbyBeacons.get(1);
    }

    public static boolean isMinimalBeaconPlacingDistance(Location location) {
        Block closestBeacon = getClosestNearbyBeacon(location);
        return closestBeacon == null || closestBeacon.getLocation().distance(location) >= 10;
    }

    public static boolean isPartOfBeaconStructure(Location blockLocation, Location beaconLocation) {
        Location normalized = blockLocation.subtract(beaconLocation);
        if (normalized.getBlockY() > -1 || normalized.getBlockY() < -4) {
            return false;
        }
        return (Math.abs(normalized.getBlockX()) <= -normalized.getBlockY() && Math.abs(normalized.getBlockZ()) <= -normalized.getBlockY());
    }

    public static boolean isBlockBreakableByPlayer(Block block, UUID breaker) {
        List<Block> nearbyBeacons = getNearbyBeacons(block.getLocation());
        for (Block nearbyBeacon : nearbyBeacons) {
            if (isPartOfBeaconStructure(block.getLocation(), nearbyBeacon.getLocation())) {
                if (!RedCraftProtect.getInstance().protectedElements.isBlockBreakable(nearbyBeacon, breaker)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isBlockBreakable(Block block) {
        List<Block> nearbyBeacons = getNearbyBeacons(block.getLocation());
        for (Block nearbyBeacon : nearbyBeacons) {
            if (isPartOfBeaconStructure(block.getLocation(), nearbyBeacon.getLocation())) {
                return false;
            }
        }
        return true;
    }

    public static ProtectedInteractionResult getInteractionResult(Block block) {
        return getInteractionResult(block, null);
    }

    public static ProtectedInteractionResult getInteractionResult(Block block, UUID breaker) {
        List<Block> nearbyBeacons = getNearbyBeacons(block.getLocation());
        for (Block nearbyBeacon : nearbyBeacons) {
            if (!isPartOfBeaconStructure(block.getLocation(), nearbyBeacon.getLocation())) {
                continue;
            }
            ProtectedInteractionResult interactionResult = RedCraftProtect.getInstance().protectedElements.getInteractionResult(nearbyBeacon, breaker);
            if (!interactionResult.isBreakable()) {
                return new ProtectedInteractionResult(Permission.NONE, breaker, interactionResult.message);
            }
        }
        return new ProtectedInteractionResult(Permission.BREAK, breaker);
    }

    public static String getBeaconError(Location location, String playerName) {
        String errorString = "This block belongs to the beacon situated at ";
        errorString += location.getBlockX() + "X ";
        errorString += location.getBlockY() + "Y ";
        errorString += location.getBlockZ() + "Z ";
        errorString += " and belongs to " + playerName;
        return errorString;
    }

}
