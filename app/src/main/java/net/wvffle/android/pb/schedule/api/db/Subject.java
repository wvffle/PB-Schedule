package net.wvffle.android.pb.schedule.api.db;


public class Subject extends ScheduleEntry {
    public static final String TAG_NAME = "tabela_przedmioty";
    private final int id;
    private final String name;
    private final String short_name;
    private final int updatedAt;

    public Subject(int id, String name, String short_name, int updatedAt) {
        this.id = id;
        this.name = name;
        this.short_name = short_name;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getShort_name() {
        return name;
    }

    public int getUpdatedAt() {
        return updatedAt;
    }
}
