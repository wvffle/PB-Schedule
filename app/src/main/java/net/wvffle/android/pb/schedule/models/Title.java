package net.wvffle.android.pb.schedule.models;

import com.google.gson.JsonObject;

import net.wvffle.android.pb.schedule.api.model.Model;

import io.objectbox.annotation.ConflictStrategy;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;
import io.objectbox.annotation.Unique;

@Entity
public class Title implements Model {
    @Id(assignable = true)
    public long id;

    @Index
    @Unique(onConflict = ConflictStrategy.REPLACE)
    private final String hash;
    private final String name;

    public Title(long id, String hash, String name) {
        this.id = id;
        this.hash = hash;
        this.name = name;
    }

    /**
     * Create a new Title from JSON
     * @param title JsonObject containing title data
     * @return new title model
     */
    public static Title fromJson(JsonObject title) {
        return new Title(
                title.get("id").getAsLong(),
                title.get("hash").getAsString(),
                title.get("name").getAsString()
        );
    }

    /**
     * Return hash of the title
     * @return String hash of the title
     */
    public String getHash() {
        return hash;
    }

    /**
     * Return name of the title
     *
     * @return String name of the title
     */
    public String getName() {
        return name;
    }

    /**
     * Return id of the title
     *
     * @return Long id of the title
     */
    @Override
    public Long getId() {
        return id;
    }
}
