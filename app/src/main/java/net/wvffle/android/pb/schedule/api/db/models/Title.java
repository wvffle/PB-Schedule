package net.wvffle.android.pb.schedule.api.db.models;

import com.google.gson.JsonObject;

import net.wvffle.android.pb.schedule.api.syncedcollectionentry.SyncedCollectionEntry;

import io.objectbox.annotation.ConflictStrategy;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;
import io.objectbox.annotation.Unique;

@Entity
public class Title implements SyncedCollectionEntry {
    @Id
    public long id;

    @Index
    @Unique(onConflict = ConflictStrategy.REPLACE)
    private final String hash;
    private final String name;

    public Title(long id, String hash, String name) {
        this.id = id;
        this.hash = hash;
        this.name = name;
    }

    public static Title fromJson(JsonObject title) {
        return new Title(
                0,
                title.get("hash").getAsString(),
                title.get("name").getAsString()
        );
    }

    public String getHash() {
        return hash;
    }

    public String getName() {
        return name;
    }
}
