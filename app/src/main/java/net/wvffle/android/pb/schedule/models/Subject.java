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

    public static Subject fromJson(JsonObject subject) {
        return new Subject(
                subject.get("id").getAsLong(),
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
