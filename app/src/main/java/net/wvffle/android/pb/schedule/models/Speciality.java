package net.wvffle.android.pb.schedule.models;

import com.google.gson.JsonObject;

import net.wvffle.android.pb.schedule.api.model.Model;

import io.objectbox.annotation.ConflictStrategy;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;
import io.objectbox.annotation.Unique;

@Entity
public class Speciality implements Model {
    @Id(assignable = true)
    public long id;

    @Index
    @Unique(onConflict = ConflictStrategy.REPLACE)
    private final String hash;
    private final String name;

    public Speciality(long id, String hash, String name) {
        this.id = id;
        this.hash = hash;
        this.name = name;
    }

    public static Speciality fromJson(JsonObject speciality) {
        return new Speciality(
                speciality.get("id").getAsLong(),
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
