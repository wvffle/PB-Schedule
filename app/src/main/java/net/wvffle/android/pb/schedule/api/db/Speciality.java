package net.wvffle.android.pb.schedule.api.db;


import java.util.Date;

public class Speciality {
    private String hash;
    private final String name;

    public Speciality(String hash, String name) {
        this.hash = hash;
        this.name = name;
    }

    public String getHash() {
        return hash;
    }

    public String getName() {
        return name;
    }
}
