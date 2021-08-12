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
import org.redcraft.redcraftprotect.utils.BeaconUtils;
import org.redcraft.redcraftprotect.utils.LocationUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockPlaceListener implements Listener {
    

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockPlace(BlockPlaceEvent event) {


        if (RedCraftProtect.getInstance().protectedBlocks.contains(event.getBlock().getType())) {
            ProtectedElement protectedElement = new ProtectedElement(event.getPlayer().getUniqueId(), event.getBlock().getLocation(), event.getBlock().getType());
            RedCraftProtect.getInstance().protectedElements.add(protectedElement);
        }
    }
}
