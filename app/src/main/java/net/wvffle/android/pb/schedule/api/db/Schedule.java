package net.wvffle.android.pb.schedule.api.db;


public class Schedule extends ScheduleEntry {
    public static final String TAG_NAME = "tabela_rozklad";
    private final int id;
    private final int day;
    private final int hour;
    private final int amount;
    private final int week;
    private final int id_teacher;
    private final int id_room;
    private final int id_subject;
    private final String type;
    private final int group;
    private final int id_studies;
    private final int term;
    private final int id_speciality;
    private final int updatedAt;

    public Schedule(int id, int day, int hour, int amount, int week, int id_teacher, int id_room, int id_subject,
                    String type, int group, int id_studies, int term, int id_speciality, int updatedAt) {
        this.id = id;
        this.day = day;
        this.hour = hour;
        this.amount = amount;
        this.week = week;
        this.id_teacher = id_teacher;
        this.id_room = id_room;
        this.id_subject = id_subject;
        this.type = type;
        this.group = group;
        this.id_studies = id_studies;
        this.term = term;
        this.id_speciality = id_speciality;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getAmount() {
        return amount;
    }

    public int getWeek() {
        return week;
    }

    public int getId_teacher() {
        return id_teacher;
    }

    public int getId_room() {
        return id_room;
    }

    public int getId_subject() {
        return id_subject;
    }

    public String getType() {
        return type;
    }

    public int getGroup() {
        return group;
    }

    public int getId_studies() {
        return id_studies;
    }

    public int getTerm() {
        return term;
    }

    public int getId_speciality() {
        return id_speciality;
    }

    public int getUpdatedAt() {
        return updatedAt;
    }
}