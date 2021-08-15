package org.redcraft.redcraftprotect.listeners.blockListeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.redcraft.redcraftprotect.RedCraftProtect;
import org.redcraft.redcraftprotect.models.world.Permission;
import org.redcraft.redcraftprotect.models.world.ProtectedElement;
import org.redcraft.redcraftprotect.models.world.ProtectedElements;
import org.redcraft.redcraftprotect.utils.ProtectedInteractionResult;

import java.util.UUID;

import static org.redcraft.redcraftprotect.models.world.ProtectedElement.isChestLikeContainer;

public class BlockPlaceListener implements Listener {


    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();

        if (RedCraftProtect.getInstance().protectedBlocks.contains(block.getType())) {
            ProtectedElement protectedElement = new ProtectedElement(player.getUniqueId(), block.getLocation(), block.getType());
            RedCraftProtect.getInstance().protectedElements.add(protectedElement);
        }

        // TODO add support for barrels and shulker boxes

        // TODO check if block placed doesn't affect the bellow tile
        // TODO example if glass is placed above a chest, the later is still openable

        // Checks if click will affect possible container bellow
        Block blockPlacedAgainst = event.getBlockAgainst();
        if (blockPlacedAgainst.getY() <= block.getY()) {
            return;
        }
        Block blockBellow = block.getLocation().subtract(0, 1, 0).getBlock();
        Material blockBellowType = blockBellow.getType();
        if (!isChestLikeContainer(blockBellowType)) {
            return;
        }
        block = blockBellow;
        ProtectedElements elements = RedCraftProtect.getInstance().protectedElements;
        UUID playerUUID = player.getUniqueId();
        ProtectedInteractionResult interactionResult = elements.getInteractionResult(block, playerUUID, Permission.EDIT);
        if (!interactionResult.isBreakable()) {
            player.sendMessage("You can't place a block above this container");
            event.setCancelled(true);
        }
    }
}
