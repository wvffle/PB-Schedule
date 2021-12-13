package net.wvffle.android.pb.schedule.api.syncedcollectionentry.entries;


import com.google.gson.JsonObject;

import net.wvffle.android.pb.schedule.api.db.ObjectBox;
import net.wvffle.android.pb.schedule.api.syncedcollectionentry.SyncedCollectionEntry;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;

@Entity
public class Room implements SyncedCollectionEntry {
    @Id
    public long id;

    @Index
    private final String hash;
    private final String name;

    public Room(long id, String hash, String name) {
        this.id = id;
        this.hash = hash;
        this.name = name;
    }

    public static Room fromJson(JsonObject room) {
        return new Room(
                0,
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
