package net.wvffle.android.pb.schedule.api.db;


public class Speciality extends ScheduleEntry {
    public static final String TAG_NAME = "tabela_specjalnosci";
    private final int id;
    private final String name;
    private final int updatedAt;

    public Speciality(int id, String name, int updatedAt) {
        this.id = id;
        this.name = name;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getUpdatedAt() {
        return updatedAt;
    }
}