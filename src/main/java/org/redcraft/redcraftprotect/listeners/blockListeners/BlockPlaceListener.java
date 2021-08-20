package org.redcraft.redcraftprotect.listeners.blockListeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.redcraft.redcraftprotect.RedCraftProtect;
import org.redcraft.redcraftprotect.models.Permission;
import org.redcraft.redcraftprotect.models.world.ProtectedElement;
import org.redcraft.redcraftprotect.models.world.ProtectedElements;
import org.redcraft.redcraftprotect.utils.BeaconUtils;
import org.redcraft.redcraftprotect.utils.ProtectedInteractionResult;

import java.util.List;
import java.util.UUID;

import static org.redcraft.redcraftprotect.models.world.ProtectedElement.isChestLikeContainer;

public class BlockPlaceListener implements Listener {


    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();

        ProtectedElements elements = RedCraftProtect.getInstance().protectedElements;

        // TODO export to BeaconUtils
        if (block.getType().equals(Material.BEACON)) {
            List<Block> nearbyBeacons = BeaconUtils.getNearbyBeacons(block.getLocation());
            for (Block closestBeacon : nearbyBeacons) {
                if (closestBeacon == null || closestBeacon.getLocation().distance(block.getLocation()) > 10) {
                    continue;
                }
                ProtectedInteractionResult interactionResult = elements.getInteractionResult(closestBeacon, player.getUniqueId(), Permission.EDIT);
                if (!interactionResult.isEditable()) {
                    player.sendMessage("You can't place this beacon, it is too close to someone else's");
                    event.setCancelled(true);
                    return;
                }

            }
        }

        // TODO add support for barrels and shulker boxes
        // TODO check if block placed doesn't affect the bellow tile
        // TODO example if glass is placed above a chest, the later is still openable
        // Checks if click will affect possible container bellow
        Block blockBellow = block.getLocation().subtract(0, 1, 0).getBlock();
        Material blockBellowType = blockBellow.getType();
        if (isChestLikeContainer(blockBellowType)) {
            block = blockBellow;
            UUID playerUUID = player.getUniqueId();
            ProtectedInteractionResult interactionResult = elements.getInteractionResult(block, playerUUID, Permission.EDIT);
            if (!interactionResult.isBreakable()) {
                player.sendMessage("You can't place a block above this container");
                event.setCancelled(true);
                return;
            }
        }
        boolean defaultPlacable = true;
        if (defaultPlacable && RedCraftProtect.getInstance().protectedBlocks.contains(block.getType())) {
            ProtectedElement protectedElement = new ProtectedElement(player.getUniqueId(), block.getLocation(), block.getType());
            RedCraftProtect.getInstance().protectedElements.add(protectedElement);
        }
    }
}
