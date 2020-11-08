package org.redcraft.redcraftprotect.listeners.entityListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.redcraft.redcraftprotect.RedCraftProtect;

public class EntityExplodeListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityExplode(EntityExplodeEvent event) {
        event.blockList().removeIf(block -> !RedCraftProtect.getInstance().protectedElements.isBlockBreakable(block, null));
    }

}
