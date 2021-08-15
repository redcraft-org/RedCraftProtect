package org.redcraft.redcraftprotect.listeners.inventoryListeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryEvent;

public class InventoryClickListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInventoryClick(InventoryEvent event) {
        Bukkit.broadcastMessage("wergvwergvwerg");
    }
}
