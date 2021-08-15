package org.redcraft.redcraftprotect.listeners.playerListeners;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.redcraft.redcraftprotect.RedCraftProtect;
import org.redcraft.redcraftprotect.models.world.ProtectedElement;
import org.redcraft.redcraftprotect.utils.ProtectedInteractionResult;

import java.util.UUID;

import static org.redcraft.redcraftprotect.models.world.ProtectedElement.isChestLikeContainer;

public class PlayerInteractListener implements Listener {


    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();

        if (block == null) {
            return;
        }

        // TODO add support for barrels and shulker boxes
        //checks if click will affect possible container bellow
        if (!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getBlockFace().equals(BlockFace.UP))) {
            return;
        }
        if (!isChestLikeContainer(block.getType())) {
            return;
        }
        UUID playerUUID = event.getPlayer().getUniqueId();

        ProtectedInteractionResult interactionResult = RedCraftProtect.getInstance().protectedElements.getInteractionResult(block, playerUUID, ProtectedElement.Permission.OPEN);
        if (!interactionResult.isInteractable()) {
            event.getPlayer().sendMessage("You cannot place a block here");
            event.setCancelled(true);
        }
    }
}
