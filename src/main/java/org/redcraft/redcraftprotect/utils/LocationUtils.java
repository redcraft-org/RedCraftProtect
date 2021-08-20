package org.redcraft.redcraftprotect.utils;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

import java.util.ArrayList;
import java.util.List;

public class LocationUtils {


    public static List<Chunk> getNearbyChunks(Chunk chunk, int radius) {
        List<Chunk> nearbyChunks = new ArrayList<Chunk>();

        World world = chunk.getWorld();

        int startX = chunk.getX() - radius;
        int endX = chunk.getX() + radius;

        int startZ = chunk.getZ() - radius;
        int endZ = chunk.getZ() + radius;

        for (int x = startX; x <= endX; x++){
            for (int z = startZ; z <= endZ; z++){
                nearbyChunks.add(world.getChunkAt(x, z));
            }
        }

        return nearbyChunks;
    }

    public static List<Block> getTileEntitiesFromChunks(List<Chunk> chunks) {
        List<Block> nearbyTileEntities = new ArrayList<Block>();
        for (Chunk chunk : chunks) {
            BlockState[] tileEnTities = chunk.getTileEntities();
            for (BlockState blockState : tileEnTities) {
                nearbyTileEntities.add(blockState.getBlock());
            }
        }
        return nearbyTileEntities;
    }

    public static List<Block> getNearbyTileEntitiesFromChunk(Chunk chunk, int radius) {
        List<Chunk> nearbyChunks = getNearbyChunks(chunk, radius);

        return getTileEntitiesFromChunks(nearbyChunks);
    }

    public static List<Block> getFilteredNearbyTileEntitiesFromChunk(Chunk chunk, int radius, Material material) {
        List<Block> tileEntities = getNearbyTileEntitiesFromChunk(chunk, radius);

        // TODO filter
        tileEntities.removeIf(block -> !block.getType().equals(material));

        return tileEntities;
    }
}
