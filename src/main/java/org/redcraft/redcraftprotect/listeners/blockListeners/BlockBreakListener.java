package org.redcraft.redcraftprotect.listeners.blockListeners;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.redcraft.redcraftprotect.RedCraftProtect;
import org.redcraft.redcraftprotect.models.world.ProtectedElements;

public class BlockBreakListener implements Listener {



    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (block == null) {
            return;
        }
        if (! RedCraftProtect.getInstance().protectedElements.isBlockBreakable(block,event.getPlayer().getUniqueId()))
        {
            event.getPlayer().sendMessage("This block is owned by " + Bukkit.getPlayer(RedCraftProtect.getInstance().protectedElements.get(block).owner).getDisplayName());
            event.setCancelled(true);
            return;
        }
        RedCraftProtect.getInstance().protectedElements.remove(block);

    }
}
