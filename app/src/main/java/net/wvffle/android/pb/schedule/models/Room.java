package net.wvffle.android.pb.schedule.models;


import com.google.gson.JsonObject;

import net.wvffle.android.pb.schedule.api.model.Model;

import io.objectbox.annotation.ConflictStrategy;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;
import io.objectbox.annotation.Unique;

@Entity
public class Room implements Model {
    @Id(assignable = true)
    public long id;

    @Index
    @Unique(onConflict = ConflictStrategy.REPLACE)
    private final String hash;
    private final String name;

    public Room(long id, String hash, String name) {
        this.id = id;
        this.hash = hash;
        this.name = name;
    }

    /**
     * Create a new Room from JSON
     * @param room JsonObject containing room data
     * @return new room model
     */
    public static Room fromJson(JsonObject room) {
        return new Room(
                room.get("id").getAsLong(),
                room.get("hash").getAsString(),
                room.get("name").getAsString()
        );
    }

    /**
     * Return hash of the room
     *
     * @return String hash of the room
     */
    public String getHash() {
        return hash;
    }

    /**
     * Return id of the room
     *
     * @return Long id of the room
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * Return name of the room
     *
     * @return String name of the room
     */
    public String getName() {
        return name;
    }
}
