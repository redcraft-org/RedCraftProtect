package org.redcraft.redcraftprotect.listeners.blockListeners;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.redcraft.redcraftprotect.RedCraftProtect;
import org.redcraft.redcraftprotect.utils.ProtectedInteractionResult;

public class BlockBreakListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        ProtectedInteractionResult interactionResult = RedCraftProtect.getInstance().protectedElements.getInteractionResult(block, event.getPlayer().getUniqueId());
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
