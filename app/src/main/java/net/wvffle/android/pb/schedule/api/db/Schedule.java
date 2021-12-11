package net.wvffle.android.pb.schedule.api.db;


public class Schedule  {
    private String hash;
    private final int day;
    private final int hour;
    private final int intervals;
    private final int weekFlags;
    private final String teacher;
    private final String room;
    private final String subject;
    private final String type;
    private final int group;
    private final String degree;
    private final int semestr;
    private final String speciality;
    private final int updatedAt;

    public Schedule(String hash, int day, int hour, int intervals, int weekFlags, String teacher, String room, String subject,
                    String type, int group, String degree, int semestr, String speciality, int updatedAt) {
        this.hash = hash;
        this.day = day;
        this.hour = hour;
        this.intervals = intervals;
        this.weekFlags = weekFlags;
        this.teacher = teacher;
        this.room = room;
        this.subject = subject;
        this.type = type;
        this.group = group;
        this.degree = degree;
        this.semestr = semestr;
        this.speciality = speciality;
        this.updatedAt = updatedAt;
    }

    public String getHash() {
        return hash;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getIntervals() {
        return intervals;
    }

    public int getWeekFlags() {
        return weekFlags;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getRoom() {
        return room;
    }

    public String getSubject() {
        return subject;
    }

    public String getType() {
        return type;
    }

    public int getGroup() {
        return group;
    }

    public String getDegree() {
        return degree;
    }

    public int getSemestr() {
        return semestr;
    }

    public String getSpeciality() {
        return speciality;
    }

    public int getUpdatedAt() {
        return updatedAt;
    }
}