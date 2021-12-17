package net.wvffle.android.pb.schedule.api.update;

import com.google.gson.JsonObject;

public class UpdateEntry {
    private final long id;
    private final String hash;

    private UpdateEntry(long id, String hash) {
        this.id = id;
        this.hash = hash;
    }

    public static UpdateEntry fromJson (JsonObject json) {
        return new UpdateEntry(
                json.get("id").getAsLong(),
                json.get("hash").getAsString()
        );
    }

    public long getId() {
        return id;
    }

    public String getHash() {
        return hash;
    }
}
