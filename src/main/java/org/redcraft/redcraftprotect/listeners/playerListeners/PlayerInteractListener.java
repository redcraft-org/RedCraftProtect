package org.redcraft.redcraftprotect.listeners.playerListeners;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.redcraft.redcraftprotect.RedCraftProtect;

public class PlayerInteractListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        if (block == null) {
            return;
        }

        if (!RedCraftProtect.getInstance().protectedElements.isBlockBreakable(block, event.getPlayer().getUniqueId())) {
            event.getPlayer().sendMessage("Interact: This block is owned by " + Bukkit.getPlayer(RedCraftProtect.getInstance().protectedElements.get(block).owner.player).getDisplayName());
            event.setCancelled(true);
        }
    }
}
