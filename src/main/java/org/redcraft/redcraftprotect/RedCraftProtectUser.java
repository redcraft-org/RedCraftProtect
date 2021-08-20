package org.redcraft.redcraftprotect;

import org.redcraft.redcraftprotect.models.Permission;

import java.util.UUID;

public class RedCraftProtectUser {

    public UUID player;
    public RedCraftProtectFriends friends;

    public RedCraftProtectUser(UUID player) {
        this.player = player;
        this.friends = new RedCraftProtectFriends();
    }

    public boolean hasPermission(UUID player, Permission permission) {
        if (player.equals(this.player)) {
            return true;
        }
        return this.friends.get(player).hasPermission(permission);
    }


}
