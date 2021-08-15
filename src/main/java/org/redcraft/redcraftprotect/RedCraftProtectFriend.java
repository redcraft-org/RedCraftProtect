package org.redcraft.redcraftprotect;

import org.redcraft.redcraftprotect.models.world.Permission;

import java.time.LocalDateTime;
import java.util.UUID;

public class RedCraftProtectFriend {

    public UUID player;
    public Permission permission;
    public LocalDateTime localDateTime;

    public RedCraftProtectFriend(UUID player, Permission permission) {
        this.player = player;
        this.permission = permission;
        this.localDateTime = LocalDateTime.now();
    }

    public RedCraftProtectFriend(RedCraftProtectUser user, Permission permission) {
        this.player = user.player;
        this.permission = permission;
        this.localDateTime = LocalDateTime.now();
    }


    public boolean hasPermission(Permission permission) {
        return this.permission.isHigherOrEqual(permission);
    }
}
