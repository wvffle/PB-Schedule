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

    public static Room fromJson(JsonObject room) {
        return new Room(
                room.get("id").getAsLong(),
                room.get("hash").getAsString(),
                room.get("name").getAsString()
        );
    }

    public String getHash() {
        return hash;
    }

    public String getName() {
        return name;
    }
}
