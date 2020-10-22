package org.redcraft.redcraftprotect.models.database;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "rcp_player_preferences")
public class PlayerPreferences extends DatabaseModel {
    @Id
    @Column(name = "id", unique = true)
    public UUID uuid;

    @Column(name = "protect_on_place")
    public Boolean protectOnPlace;

    public PlayerPreferences(UUID uuid) {
        this.uuid = uuid;
    }
}
