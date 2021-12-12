package net.wvffle.android.pb.schedule.api.db;

import com.google.gson.JsonObject;

import java.util.Date;

public class Subject {
    private String hash;
    private final String name;
    private final String shortName;

    public Subject(String hash, String name, String shortName) {
        this.hash = hash;
        this.name = name;
        this.shortName = shortName;
    }

    public static Subject fromJson(JsonObject subject) {
        return new Subject(
                subject.get("hash").getAsString(),
                subject.get("name").getAsString(),
                subject.get("shortName").getAsString()
        );
    }

    public String getHash() {
        return hash;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }
}
