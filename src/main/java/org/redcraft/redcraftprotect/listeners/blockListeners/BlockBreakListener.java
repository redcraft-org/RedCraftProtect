package org.redcraft.redcraftprotect.listeners.blockListeners;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.redcraft.redcraftprotect.RedCraftProtect;
import org.redcraft.redcraftprotect.models.Permission;
import org.redcraft.redcraftprotect.models.world.ProtectedElements;
import org.redcraft.redcraftprotect.utils.ProtectedInteractionResult;

import java.util.UUID;

public class BlockBreakListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        UUID playerUUID = event.getPlayer().getUniqueId();
        ProtectedElements elements = RedCraftProtect.getInstance().protectedElements;
        ProtectedInteractionResult interactionResult = elements.getInteractionResult(block, playerUUID, Permission.BREAK);
        if (!interactionResult.isBreakable()) {
            event.getPlayer().sendMessage(interactionResult.message);
            event.setCancelled(true);
            return;
        }

        if (RedCraftProtect.getInstance().protectedBlocks.contains(block.getType())) {
            RedCraftProtect.getInstance().protectedElements.remove(block);
        }

    }
}
