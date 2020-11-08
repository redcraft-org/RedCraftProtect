package org.redcraft.redcraftprotect.utils;

import org.bukkit.Chunk;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class LocationUtils {


    public static List<Chunk> getNearbyChunks(Chunk chunk, int radius) {
        List<Chunk> nearbyChunks = new ArrayList<Chunk>();

        World world = chunk.getWorld();

        Integer startX = chunk.getX() - radius;
        Integer endX = chunk.getX() + radius;

        Integer startZ = chunk.getZ() - radius;
        Integer endZ = chunk.getZ() + radius;

        for (int x = startX; x <= endX; x++){
            for (int z = startZ; z <= endZ; z++){
                nearbyChunks.add(world.getChunkAt(x, z));
            }
        }

        return nearbyChunks;
    }
}
