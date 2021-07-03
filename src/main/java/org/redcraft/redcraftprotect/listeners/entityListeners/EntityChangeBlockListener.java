package org.redcraft.redcraftprotect.listeners.entityListeners;


import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.redcraft.redcraftprotect.RedCraftProtect;

public class EntityChangeBlockListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityChangeBlockEvent(EntityChangeBlockEvent event) {

        if (!RedCraftProtect.getInstance().protectedElements.isBlockBreakable(event.getBlock(), null)) {
            Bukkit.broadcastMessage(event.getBlock().getType().toString());
            event.setCancelled(true);
        }
    }
}
