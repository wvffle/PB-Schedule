package net.wvffle.android.pb.schedule.api.db;


import java.util.Date;

public class Subject  {
    private String hash;
    private final String name;
    private final String shortName;
    private final Date updatedAt;

    public Subject(String hash, String name, String shortName, int updatedAt) {
        this.hash = hash;
        this.name = name;
        this.shortName = shortName;
        this.updatedAt = new Date(updatedAt);
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

    public Date getUpdatedAt() {
        return updatedAt;
    }
}
