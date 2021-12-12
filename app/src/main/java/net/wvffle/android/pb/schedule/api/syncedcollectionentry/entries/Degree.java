package net.wvffle.android.pb.schedule.api.syncedcollectionentry.entries;

import com.google.gson.JsonObject;

import net.wvffle.android.pb.schedule.api.syncedcollectionentry.SyncedCollectionEntry;

public class Degree implements SyncedCollectionEntry {
    private String hash;
    private final String name;

    public Degree(String hash, String name) {
        this.hash = hash;
        this.name = name;
    }

    public static Degree fromJson(JsonObject degree) {
        return new Degree(
                degree.get("hash").getAsString(),
                degree.get("name").getAsString()
        );
    }

    public String getHash() {
        return hash;
    }

    public String getName() {
        return name;
    }
}

