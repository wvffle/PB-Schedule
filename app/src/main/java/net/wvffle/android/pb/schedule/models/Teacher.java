package net.wvffle.android.pb.schedule.models;

import com.google.gson.JsonObject;

import net.wvffle.android.pb.schedule.api.model.Model;

import io.objectbox.annotation.ConflictStrategy;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;
import io.objectbox.annotation.Unique;
import io.objectbox.relation.ToOne;

@Entity
public class Teacher implements Model {
    @Id(assignable = true)
    public long id;

    @Index
    @Unique(onConflict = ConflictStrategy.REPLACE)
    private final String hash;
    private final String surname;
    private final String firstName;
    private final String initials;

    public ToOne<Title> title;

    public Teacher(long id, String hash, String surname, String firstName, String initials, long titleId) {
        this.id = id;
        this.hash = hash;
        this.surname = surname;
        this.firstName = firstName;
        this.initials = initials;
        this.title.setTargetId(titleId);
    }

    /**
     * Create a new Teacher from JSON
     * @param teacher JsonObject containing teacher data
     * @return new teacher model
     */
    public static Teacher fromJson(JsonObject teacher) {
        return new Teacher(
                0,
                teacher.get("hash").getAsString(),
                teacher.get("surname").getAsString(),
                teacher.get("name").getAsString(),
                teacher.get("initials").getAsString(),
                teacher.get("title").getAsLong()
        );
    }

    /**
     * Return hash of the teacher
     * @return String hash of the teacher
     */
    public String getHash() {
        return hash;
    }

    /**
     * Return last name of the teacher
     * @return String last name of the teacher
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Return first name of the teacher
     * @return String first name of the teacher
     */
    public String getFirstName() {
        return firstName;
    }


    /**
     * Return initials of the teacher
     * @return String initials of the teacher
     */
    public String getInitials() {
        return initials;
    }

    /**
     * Return title of the teacher
     * @return Title of the teacher
     */
    public Title getTitle() {
        return title.getTarget();
    }
}
