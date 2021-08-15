package org.redcraft.redcraftprotect;

import org.redcraft.redcraftprotect.models.world.ProtectedElement;

import java.time.LocalDateTime;
import java.util.UUID;

public class RedCraftProtectFriend {

    public UUID player;
    public ProtectedElement.Permission permission;
    public LocalDateTime localDateTime;

    public RedCraftProtectFriend(UUID player, ProtectedElement.Permission permission) {
        this.player = player;
        this.permission = permission;
        this.localDateTime = LocalDateTime.now();
    }

    public RedCraftProtectFriend(RedCraftProtectUser user, ProtectedElement.Permission permission) {
        this.player = user.player;
        this.permission = permission;
        this.localDateTime = LocalDateTime.now();
    }


    public boolean hasPermission(ProtectedElement.Permission permission) {
        return this.permission.isHigherOrEqual(permission);
    }
}
