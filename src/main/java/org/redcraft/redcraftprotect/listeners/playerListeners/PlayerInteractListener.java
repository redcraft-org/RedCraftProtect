package org.redcraft.redcraftprotect.listeners.playerListeners;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.redcraft.redcraftprotect.RedCraftProtect;
import org.redcraft.redcraftprotect.models.world.Permission;
import org.redcraft.redcraftprotect.models.world.ProtectedElement;
import org.redcraft.redcraftprotect.utils.ProtectedInteractionResult;

import java.util.UUID;

public class PlayerInteractListener implements Listener {


    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        if (block == null) {
            return;
        }

        boolean isRightClick = event.getAction().equals(Action.RIGHT_CLICK_BLOCK);
        if (!isRightClick) {
            return;
        }

        // TODO check if player actually can't open inventory if sneaking might be vulnerable
        Player player = event.getPlayer();
        if (player.isSneaking() || !ProtectedElement.isContainer(block.getType())) {
            return;
        }

        UUID playerUUID = player.getUniqueId();
        ProtectedInteractionResult interactionResult = RedCraftProtect.getInstance().protectedElements.getInteractionResult(block, playerUUID, Permission.OPEN);
        if (!interactionResult.isInteractable()) {
            event.getPlayer().sendMessage(interactionResult.message);
            event.setCancelled(true);
        }
    }
}
