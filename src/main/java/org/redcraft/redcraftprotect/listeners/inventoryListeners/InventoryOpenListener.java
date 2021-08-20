package org.redcraft.redcraftprotect.listeners.inventoryListeners;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.inventory.InventoryHolder;
import org.redcraft.redcraftprotect.RedCraftProtect;
import org.redcraft.redcraftprotect.models.Permission;
import org.redcraft.redcraftprotect.utils.ProtectedInteractionResult;

import java.util.UUID;

public class InventoryOpenListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInventoryOpen(InventoryOpenEvent event) {
        Player player = (Player) event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        InventoryHolder holder = event.getInventory().getHolder();
        if (!(holder instanceof BlockInventoryHolder blockInventoryHolder)) {
            return;
        }
        Block block = blockInventoryHolder.getBlock();
        ProtectedInteractionResult interactionResult = RedCraftProtect.getInstance().protectedElements.getInteractionResult(block, playerUUID, Permission.OPEN);
        if (!interactionResult.isInteractable()) {
            event.getPlayer().sendMessage(interactionResult.message);
            event.setCancelled(true);
        }
    }
}
