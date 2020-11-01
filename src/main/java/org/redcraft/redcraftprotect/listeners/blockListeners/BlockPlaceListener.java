package org.redcraft.redcraftprotect.listeners.blockListeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.redcraft.redcraftprotect.RedCraftProtect;
import org.redcraft.redcraftprotect.models.world.ProtectedElement;

public class BlockPlaceListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (RedCraftProtect.getInstance().protectedBlocks.contains(event.getBlock().getType().name())){
            ProtectedElement protectedElement = new ProtectedElement(event.getPlayer().getUniqueId(), event.getBlock().getLocation(), event.getBlock().getType().name());
            RedCraftProtect.getInstance().protectedElements.add(protectedElement);
        }
    }
}
