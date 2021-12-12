package net.wvffle.android.pb.schedule.api.db;

import com.google.gson.JsonObject;

import java.util.Date;

public class Speciality {
    private String hash;
    private final String name;

    public Speciality(String hash, String name) {
        this.hash = hash;
        this.name = name;
    }

    public static Speciality fromJson(JsonObject speciality) {
        return new Speciality(
                speciality.get("hash").getAsString(),
                speciality.get("name").getAsString()
        );
    }

    public String getHash() {
        return hash;
    }

    public String getName() {
        return name;
    }
}
