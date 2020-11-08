package org.redcraft.redcraftprotect;

import java.util.HashMap;
import java.util.UUID;

public class RedCraftProtectUsers {
    public HashMap<UUID, RedCraftProtectUser> redCraftProtectUsers;

    public RedCraftProtectUsers() {
        this.redCraftProtectUsers = new HashMap<>();
    }

    public RedCraftProtectUser addAndGetUser(UUID player) {
        RedCraftProtectUser user = new RedCraftProtectUser(player);
        this.redCraftProtectUsers.put(player, user);
        return user;
    }

    public RedCraftProtectUser getUser(UUID player) {
        return this.redCraftProtectUsers.getOrDefault(player, this.addAndGetUser(player));
    }
}
