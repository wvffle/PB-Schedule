package net.wvffle.android.pb.schedule.api.syncedcollectionentry.entries;


import com.google.gson.JsonObject;

import net.wvffle.android.pb.schedule.api.syncedcollectionentry.SyncedCollectionEntry;

public class Room implements SyncedCollectionEntry {
    private final String hash ;
    private final String name;

    public Room(String hash, String name) {
        this.hash = hash;
        this.name = name;
    }

    public static Room fromJson(JsonObject room) {
        return new Room(
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
