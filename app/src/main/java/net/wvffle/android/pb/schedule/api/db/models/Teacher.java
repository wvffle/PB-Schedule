package net.wvffle.android.pb.schedule.api.db.models;

import com.google.gson.JsonObject;

import net.wvffle.android.pb.schedule.api.syncedcollectionentry.SyncedCollectionEntry;

import io.objectbox.annotation.ConflictStrategy;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;
import io.objectbox.annotation.Unique;
import io.objectbox.relation.ToOne;

public class Teacher implements SyncedCollectionEntry {
    @Id
    public long id;

    @Index
    @Unique(onConflict = ConflictStrategy.REPLACE)
    private final String hash;
    private final String surname;
    private final String firstName;
    private final String initials;

    private final String titleHash;
    private ToOne<Title> title;

    public Teacher(long id, String hash, String surname, String firstName, String initials, String titleHash) {
        this.id = id;
        this.hash = hash;
        this.surname = surname;
        this.firstName = firstName;
        this.initials = initials;
        this.titleHash = titleHash;
        this.title.setTarget(null);
    }

    public static Teacher fromJson(JsonObject teacher) {
        return new Teacher(
                0,
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
        return title.getTarget();
    }

    public String getTitleHash() {
        return titleHash;
    }
}
