package net.wvffle.android.pb.schedule.api.db;


import java.util.Date;

public class Title  {
    private String hash;
    private final String name;
    private final Date updatedAt;

    public Title(String hash, String name, int updatedAt) {
        this.hash = hash;
        this.name = name;
        this.updatedAt = new Date(updatedAt);
    }

    public String getHash() {
        return hash;
    }

    public String getName() {
        return name;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }
}
