import java.util.ArrayList;
import java.util.List;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.redcraft.redcraftprotect.utils.LocationUtils;

import be.seeseemelk.mockbukkit.WorldMock;
import junit.framework.*;

public class LocationUtilsTest extends TestCase {
    World testWorld;
    Chunk testChunk;

	protected void setUp() {
        testWorld = new WorldMock(Material.STONE, 64);
        testChunk = testWorld.getChunkAt(15, 20);
    }

    public void testGetNearbyChunks() {
        List<Chunk> expectedNearbyChunks = new ArrayList<Chunk>();

        expectedNearbyChunks.add(testWorld.getChunkAt(14, 19));
        expectedNearbyChunks.add(testWorld.getChunkAt(14, 20));
        expectedNearbyChunks.add(testWorld.getChunkAt(14, 21));

        expectedNearbyChunks.add(testWorld.getChunkAt(15, 19));
        expectedNearbyChunks.add(testWorld.getChunkAt(15, 20));
        expectedNearbyChunks.add(testWorld.getChunkAt(15, 21));

        expectedNearbyChunks.add(testWorld.getChunkAt(16, 19));
        expectedNearbyChunks.add(testWorld.getChunkAt(16, 20));
        expectedNearbyChunks.add(testWorld.getChunkAt(16, 21));

        List<Chunk> actualNearByChunks = LocationUtils.getNearbyChunks(testChunk, 1);
        assertEquals(expectedNearbyChunks, actualNearByChunks);
    }
}
