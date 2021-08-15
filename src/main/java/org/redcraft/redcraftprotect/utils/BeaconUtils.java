package org.redcraft.redcraftprotect.utils;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.redcraft.redcraftprotect.RedCraftProtect;
import org.redcraft.redcraftprotect.models.world.Permission;
import org.redcraft.redcraftprotect.models.world.ProtectedElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class BeaconUtils {

    public final static ArrayList<Material> beaconBlocks = new ArrayList<>(Arrays.asList(Material.IRON_BLOCK, Material.EMERALD, Material.GOLD_BLOCK, Material.DIAMOND_BLOCK, Material.NETHERITE_BLOCK));

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
            if (isPartOfBeaconStructure(block.getLocation(), nearbyBeacon.getLocation())) {
                ProtectedInteractionResult interactionResult = RedCraftProtect.getInstance().protectedElements.getInteractionResult(nearbyBeacon, breaker);
                if (!interactionResult.isBreakable()) {
                    return new ProtectedInteractionResult(Permission.NONE, breaker, interactionResult.message);
                }
            }
        }
        return new ProtectedInteractionResult(Permission.BREAK, breaker, "houhouhou");
    }

    public static String getBeaconError(Location location, String playerName) {
        String errorString = "This block belongs to the beacon situated at ";
        errorString += +location.getBlockX() + "X ";
        errorString += +location.getBlockY() + "Y ";
        errorString += +location.getBlockZ() + "Z ";
        errorString += " and belongs to " + playerName;
        return errorString;
    }

}
