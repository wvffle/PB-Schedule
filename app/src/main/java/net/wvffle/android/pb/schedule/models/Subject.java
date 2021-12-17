package net.wvffle.android.pb.schedule.models;

import com.google.gson.JsonObject;

import net.wvffle.android.pb.schedule.api.model.Model;

import io.objectbox.annotation.ConflictStrategy;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;
import io.objectbox.annotation.Unique;

@Entity
public class Subject  implements Model {
    @Id(assignable = true)
    public long id;

    @Index
    @Unique(onConflict = ConflictStrategy.REPLACE)
    private final String hash;
    private final String name;
    private final String shortName;

    public Subject(long id, String hash, String name, String shortName) {
        this.id = id;
        this.hash = hash;
        this.name = name;
        this.shortName = shortName;
    }

    /**
     * Create a new Subject from JSON
     * @param subject JsonObject containing subject data
     * @return new subject model
     */
    public static Subject fromJson(JsonObject subject) {
        return new Subject(
                subject.get("id").getAsLong(),
                subject.get("hash").getAsString(),
                subject.get("name").getAsString(),
                subject.get("shortName").getAsString()
        );
    }

    /**
     * Return hash of the subject
     * @return String hash of the subject
     */
    public String getHash() {
        return hash;
    }

    /**
     * Return name of the subject
     * @return String name of the subject
     */
    public String getName() {
        return name;
    }

    /**
     * Return short name of the subject
     * @return String short name of the subject
     */
    public String getShortName() {
        return shortName;
    }
}
