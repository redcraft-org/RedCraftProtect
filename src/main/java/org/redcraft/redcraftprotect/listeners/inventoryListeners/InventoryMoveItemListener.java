package org.redcraft.redcraftprotect.listeners.inventoryListeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.redcraft.redcraftprotect.RedCraftProtect;

public class InventoryMoveItemListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInventoryMoveItem(InventoryMoveItemEvent event) {

        if (RedCraftProtect.getInstance().protectedElements.canBlocksInteract(event.getSource().getLocation(), event.getDestination().getLocation())) {
            return;
        }
        event.setCancelled(true);
    }
}
