import java.util.ArrayList;
import java.util.List;

import org.bukkit.Chunk;

import junit.framework.*;

public class LocationUtilsTest extends TestCase {
	protected void setUp() {
        // TODO
    }

    public void testGetNearbyChunks() {
        List<Chunk> expectedNearbyChunks = new ArrayList<Chunk>();
        String actualNearByChunks = null;
        assertEquals(expectedNearbyChunks, actualNearByChunks);
    }
}