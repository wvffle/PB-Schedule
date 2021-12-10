package net.wvffle.android.pb.schedule.api.db;


public class Teacher extends ScheduleEntry {
    public static final String TAG_NAME = "tabela_nauczyciele";
    private final int id;
    private final String surname;
    private final String first_name;
    private final String first_initial;
    private final int id_title;
    private final int updatedAt;

    public Teacher(int id, String surname, String first_name, String first_initial, int updatedAt, int id_title) {
        this.id = id;
        this.surname = surname;
        this.first_name = first_name;
        this.first_initial = first_initial;
        this.id_title = id_title;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getFirst_initial() {
        return first_name;
    }

    public int getId_title() {
        return id;
    }

    public int getUpdatedAt() {
        return updatedAt;
    }
}