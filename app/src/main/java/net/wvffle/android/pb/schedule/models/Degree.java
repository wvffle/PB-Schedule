package net.wvffle.android.pb.schedule.models;

import com.google.gson.JsonObject;

import net.wvffle.android.pb.schedule.api.model.Model;

import java.io.Serializable;

import io.objectbox.annotation.ConflictStrategy;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;
import io.objectbox.annotation.Unique;

@Entity
public class Degree implements Model, Serializable {
    @Id(assignable = true)
    public long id;

    @Index
    @Unique(onConflict = ConflictStrategy.REPLACE)
    private final String hash;
    private final String name;

    public Degree(long id, String hash, String name) {
        this.id = id;
        this.hash = hash;
        this.name = name;
    }

    /**
     * Create a new Degree from JSON
     * @param degree JsonObject containing degree data
     * @return new degree model
     */
    public static Degree fromJson(JsonObject degree) {
        return new Degree(
                degree.get("id").getAsLong(),
                degree.get("hash").getAsString(),
                degree.get("name").getAsString()
        );
    }

    /**
     * Return hash of the degree
     *
     * @return String hash of the degree
     */
    public String getHash() {
        return hash;
    }

    /**
     * Return id of the degree
     *
     * @return Long id of the degree
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * Return name of the degree
     *
     * @return String name of the degree
     */
    public String getName() {
        return name;
    }
}

