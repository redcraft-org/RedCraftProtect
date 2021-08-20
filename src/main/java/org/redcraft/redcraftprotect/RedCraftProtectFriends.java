package org.redcraft.redcraftprotect;

import org.redcraft.redcraftprotect.models.Permission;

import java.util.HashMap;
import java.util.UUID;

public class RedCraftProtectFriends {

    public HashMap<UUID, RedCraftProtectFriend> friendList;

    public RedCraftProtectFriends() {
        this.friendList = new HashMap<>();
    }

    public RedCraftProtectFriends(HashMap<UUID, RedCraftProtectFriend> friendList) {
        this.friendList = friendList;
    }

    public RedCraftProtectFriend get(UUID player) {
        return this.friendList.getOrDefault(player, null);
    }

    public RedCraftProtectFriends add(RedCraftProtectFriend friendToAdd) {
        HashMap<UUID, RedCraftProtectFriend> friends = new HashMap<UUID, RedCraftProtectFriend>(this.friendList);
        friends.put(friendToAdd.player, friendToAdd);
        return new RedCraftProtectFriends(friends);
    }

    public RedCraftProtectFriends add(RedCraftProtectFriends friendsToAdd) {
        HashMap<UUID, RedCraftProtectFriend> friends = new HashMap<UUID, RedCraftProtectFriend>(this.friendList);
        friends.putAll(friendsToAdd.friendList);
        return new RedCraftProtectFriends(friends);
    }

    public Boolean hasPermission(UUID player, Permission permission) {
        RedCraftProtectFriend friend = this.friendList.get(player);
        if (friend != null) {
            return friend.hasPermission(permission);
        }
        return false;
    }

    public RedCraftProtectFriends filter(Permission permission) {
        HashMap<UUID, RedCraftProtectFriend> friends = new HashMap<UUID, RedCraftProtectFriend>(this.friendList);
        friends.values().removeIf(o -> permission.isHigherOrEqual(o.permission));
        return new RedCraftProtectFriends(friends);
    }
}
