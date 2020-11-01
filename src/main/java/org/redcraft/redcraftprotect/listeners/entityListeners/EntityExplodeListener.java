package org.redcraft.redcraftprotect.listeners.entityListeners;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.redcraft.redcraftprotect.RedCraftProtect;
import org.redcraft.redcraftprotect.models.world.ProtectedElements;

public class EntityExplodeListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityExplode(EntityExplodeEvent event) {
        event.blockList().removeIf(block -> !RedCraftProtect.getInstance().protectedElements.isBlockBreakable(block, null));
    }

}
