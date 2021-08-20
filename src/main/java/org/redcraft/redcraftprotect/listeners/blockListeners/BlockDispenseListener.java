package org.redcraft.redcraftprotect.listeners.blockListeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.ItemStack;
import org.redcraft.redcraftprotect.RedCraftProtect;

import java.util.List;

import static org.redcraft.redcraftprotect.models.world.ProtectedElement.isChestLikeContainer;

public class BlockDispenseListener implements Listener {
    private static final List<Material> containers = List.of(new Material[]{
            Material.SHULKER_BOX
    });

    private boolean willPlaceBlock(BlockDispenseEvent event) {
        ItemStack stack = event.getItem();
        return containers.contains(stack.getType());
    }

    private Block getBlockAffected(Block dispenser) {
        BlockFace face = ((Dispenser) dispenser.getState().getBlockData()).getFacing();
        return dispenser.getRelative(face);
    }

    private Block getBlockBellow(Block block) {
        return block.getLocation().subtract(0, 1, 0).getBlock();
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockDispense(BlockDispenseEvent event) {
        if (!willPlaceBlock(event)) {
            return;
        }
        Block block = event.getBlock();
        Block blockAffected = getBlockAffected(block);
        if (!blockAffected.getType().equals(Material.AIR)) {
            Bukkit.broadcastMessage("Block isn't air");
            return;
        }
        Block blockBellow = getBlockBellow(blockAffected);

        // Checks if click will affect possible container bellow
        Material blockBellowType = blockBellow.getType();
        if (!isChestLikeContainer(blockBellowType)) {
            return;
        }
        if (RedCraftProtect.getInstance().protectedElements.canBlocksInteract(block, blockBellow)) {
            Bukkit.broadcastMessage("Blocks can't interact");
            event.setCancelled(true);
        }

    }
}
