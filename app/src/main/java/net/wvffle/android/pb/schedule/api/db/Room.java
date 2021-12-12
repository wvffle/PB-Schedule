package net.wvffle.android.pb.schedule.api.db;


import com.google.gson.JsonObject;

import java.util.Date;

public class Room  {
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
