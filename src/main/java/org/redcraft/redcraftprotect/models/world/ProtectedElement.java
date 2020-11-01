package org.redcraft.redcraftprotect.models.world;

import org.bukkit.Location;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class ProtectedElement {

    public UUID owner;
    public Location location;
    public String blockName;
    public ArrayList<UUID> trusted;
    public ArrayList<UUID> added;
    public LocalDateTime localDateTime;


    public ProtectedElement(UUID owner, Location location, String blockName)
    {
        this.owner = owner;
        this.location = location;
        this.blockName = blockName;
        this.trusted = new ArrayList<>();
        this.localDateTime = LocalDateTime.now();
    }

    public boolean isBreakableBy(UUID player){
        return player != null && (player == this.owner || this.trusted.contains(player));
    }

    public boolean isOwner(UUID player){
        return player == this.owner;
    }

    public boolean isTrusted(UUID player){
        return this.trusted.contains(player);
    }

    public void addTrusted(UUID player){
        if (! this.isTrusted(player)) {
            this.trusted.add(player);
        }
    }


}
