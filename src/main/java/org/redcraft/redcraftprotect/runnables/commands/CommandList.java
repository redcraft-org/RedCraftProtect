package org.redcraft.redcraftprotect.runnables.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.redcraft.redcraftprotect.RedCraftProtect;
import org.redcraft.redcraftprotect.models.world.ProtectedElement;


public class CommandList implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Bukkit.broadcastMessage("List:");
        for (ProtectedElement protectedElement: RedCraftProtect.getInstance().protectedElements.getAll()) {
            sender.sendMessage(protectedElement.blockName);
        }
        return true;
    }

}
