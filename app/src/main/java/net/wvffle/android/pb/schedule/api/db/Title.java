package net.wvffle.android.pb.schedule.api.db;

import com.google.gson.JsonObject;

import java.util.Date;

public class Title {
    private String hash;
    private final String name;

    public Title(String hash, String name) {
        this.hash = hash;
        this.name = name;
    }

    public static Title fromJson(JsonObject title) {
        return new Title(
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
