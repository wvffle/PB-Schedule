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

    /**
     * Create a new Speciality from JSON
     * @param speciality JsonObject containing speciality data
     * @return new speciality model
     */
    public static Speciality fromJson(JsonObject speciality) {
        return new Speciality(
                speciality.get("id").getAsLong(),
                speciality.get("hash").getAsString(),
                speciality.get("name").getAsString()
        );
    }

    /**
     * Return hash of the speciality
     * @return String hash of the speciality
     */
    public String getHash() {
        return hash;
    }

    /**
     * Return name of the speciality
     *
     * @return String name of the speciality
     */
    public String getName() {
        return name;
    }

    /**
     * Return id of the speciality
     *
     * @return Long id of the speciality
     */
    @Override
    public Long getId() {
        return id;
    }
}
