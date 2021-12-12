package net.wvffle.android.pb.schedule.api.db;


import java.util.Date;

public class Teacher  {
    private String hash;
    private final String surname;
    private final String firstName;
    private final String initials;
    private final String title;
    private final Date updatedAt;

    public Teacher(String hash, String surname, String firstName, String initials, String title, int updatedAt) {
        this.hash = hash;
        this.surname = surname;
        this.firstName = firstName;
        this.initials = initials;
        this.title = title;
        this.updatedAt = new Date(updatedAt);
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

    public String getTitle() {
        return  null;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }
}
