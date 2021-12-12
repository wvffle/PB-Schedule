package net.wvffle.android.pb.schedule.api.db;

import com.google.gson.JsonObject;

import java.util.Date;

public class Degree  {
    private String hash;
    private final String name;

    public Degree(String hash, String name) {
        this.hash = hash;
        this.name = name;
    }

    public static Degree fromJson(JsonObject degree) {
        return new Room(
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

