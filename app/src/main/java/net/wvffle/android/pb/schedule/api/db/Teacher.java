package net.wvffle.android.pb.schedule.api.db;


import java.util.Date;

public class Teacher  {
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
