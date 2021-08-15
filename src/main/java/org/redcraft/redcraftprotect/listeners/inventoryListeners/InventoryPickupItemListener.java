package org.redcraft.redcraftprotect.listeners.inventoryListeners;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.inventory.InventoryHolder;
import org.redcraft.redcraftprotect.RedCraftProtect;
import org.redcraft.redcraftprotect.models.world.Permission;
import org.redcraft.redcraftprotect.models.world.ProtectedElement;
import org.redcraft.redcraftprotect.models.world.ProtectedElements;

import java.util.UUID;

public class InventoryPickupItemListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInventoryPickupItem(InventoryPickupItemEvent event) {

        InventoryHolder holder = event.getInventory().getHolder();
        if (!(holder instanceof BlockInventoryHolder blockInventoryHolder)) {
            return;
        }
        Block block = blockInventoryHolder.getBlock();
        ProtectedElements elements = RedCraftProtect.getInstance().protectedElements;
        ProtectedElement element = elements.get(block);
        if (element == null) {
            return;
        }
        UUID itemOwnerUUID = event.getItem().getThrower();

        // TODO prevent spam of event that will cause lag
        if (!element.hasPermission(itemOwnerUUID, Permission.EDIT)) {
            event.setCancelled(true);
        }
    }
}
