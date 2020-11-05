package org.redcraft.redcraftprotect;

import java.util.ArrayList;
import java.util.UUID;

public class RedCraftProtectUser {

    public UUID player;
    public ArrayList<UUID> trustedFriends;
    public ArrayList<UUID> addedFriends;

    public RedCraftProtectUser(UUID player) {
        this.player = player;
        this.trustedFriends = new ArrayList<>();
        this.addedFriends = new ArrayList<>();
    }

    public boolean isTrustedFriend(UUID player) {
        return this.trustedFriends.contains(player);
    }

    public boolean isAddedFriend(UUID player) {
        return this.addedFriends.contains(player);
    }

    public void addTrustedFriend(UUID player) {
        this.trustedFriends.add(player);
    }

    public void addAddedFriend(UUID player) {
        this.addedFriends.add(player);
    }

    public void removeTrustedFriend(UUID player) {
        this.trustedFriends.remove(player);
    }

    public void removeAddedFriend(UUID player) {
        this.addedFriends.remove(player);
    }


}
