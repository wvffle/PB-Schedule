package net.wvffle.android.pb.schedule.api.syncedcollectionentry.entries;

import com.google.gson.JsonObject;

import net.wvffle.android.pb.schedule.api.syncedcollectionentry.SyncedCollectionEntry;

public class Teacher implements SyncedCollectionEntry {
    private String hash;
    private final String surname;
    private final String firstName;
    private final String initials;
    private final String title;

    public Teacher(String hash, String surname, String firstName, String initials, String title) {
        this.hash = hash;
        this.surname = surname;
        this.firstName = firstName;
        this.initials = initials;
        this.title = title;
    }

    public static Teacher fromJson(JsonObject teacher) {
        return new Teacher(
                teacher.get("hash").getAsString(),
                teacher.get("surname").getAsString(),
                teacher.get("firstName").getAsString(),
                teacher.get("initials").getAsString(),
                teacher.get("title").getAsString()
        );
    }

    public String getHash() {
        return hash;
    }

    public String getSurname() {
        return surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getInitials() {
        return initials;
    }

    public Title getTitle() {
        return null;
    }

    public String getTitleHash() {
        return title;
    }
}
