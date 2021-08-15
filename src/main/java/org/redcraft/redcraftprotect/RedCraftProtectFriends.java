package org.redcraft.redcraftprotect;

import org.redcraft.redcraftprotect.models.world.ProtectedElement;

import java.util.HashMap;
import java.util.UUID;

public class RedCraftProtectFriends {

    public HashMap<UUID, RedCraftProtectFriend> friends;

    public RedCraftProtectFriends() {
        this.friends = new HashMap<>();
    }

    public RedCraftProtectFriends(HashMap<UUID, RedCraftProtectFriend> friends) {
        this.friends = friends;
    }

    public RedCraftProtectFriend get(UUID player) {
        return this.friends.getOrDefault(player, null);
    }

    public RedCraftProtectFriends add(RedCraftProtectFriend friendToAdd) {
        HashMap<UUID, RedCraftProtectFriend> friends = new HashMap<UUID, RedCraftProtectFriend>(this.friends);
        friends.put(friendToAdd.player, friendToAdd);
        return new RedCraftProtectFriends(friends);
    }

    public RedCraftProtectFriends add(RedCraftProtectFriends friendsToAdd) {
        HashMap<UUID, RedCraftProtectFriend> friends = new HashMap<UUID, RedCraftProtectFriend>(this.friends);
        friends.putAll(friendsToAdd.friends);
        return new RedCraftProtectFriends(friends);
    }

    public Boolean hasPermission(UUID player, ProtectedElement.Permission permission) {
        RedCraftProtectFriend friend = this.friends.get(player);
        if (friend != null) {
            return friend.hasPermission(permission);
        }
        return false;
    }

    public RedCraftProtectFriends filter(ProtectedElement.Permission permission) {
        HashMap<UUID, RedCraftProtectFriend> friends = new HashMap<UUID, RedCraftProtectFriend>(this.friends);
        friends.values().removeIf(o -> permission.isHigherOrEqual(o.permission));
        return new RedCraftProtectFriends(friends);
    }
}
