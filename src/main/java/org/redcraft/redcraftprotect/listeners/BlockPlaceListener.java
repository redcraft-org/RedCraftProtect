package org.redcraft.redcraftprotect.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onServerDisconnectEvent(BlockPlaceEvent event) {
        // TODO handle event
    }
}
