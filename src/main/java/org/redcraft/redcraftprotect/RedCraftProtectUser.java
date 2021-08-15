package org.redcraft.redcraftprotect;

import org.bukkit.Bukkit;
import org.redcraft.redcraftprotect.models.world.ProtectedElement;

import java.util.UUID;

public class RedCraftProtectUser {

    public UUID player;
    public RedCraftProtectFriends friends;

    public RedCraftProtectUser(UUID player) {
        this.player = player;
        new RedCraftProtectFriends();
    }

    public boolean hasPermission(UUID player, ProtectedElement.Permission permission) {
        Bukkit.broadcastMessage("wefqwefqwefp;oiHASDP[OFJHP[Oasdjfh");
        if (player.equals(this.player)) {
            return true;
        }
        return this.friends.get(player).hasPermission(permission);
    }


}
