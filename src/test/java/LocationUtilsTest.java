import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;

import junit.framework.*;

public class LocationUtilsTest extends TestCase {
    Chunk testChunk;

	protected void setUp() {
        World world = new WorldMock(Material.STONE, 128);
        World world = new WorldMock(Material.STONE, 128);
        testChunk = null;
    }

    public void testGetNearbyChunks() {
        List<Chunk> expectedNearbyChunks = new ArrayList<Chunk>();
        expectedNearbyChunks = null;
        String actualNearByChunks = null;
        assertEquals(expectedNearbyChunks, actualNearByChunks);
    }
}