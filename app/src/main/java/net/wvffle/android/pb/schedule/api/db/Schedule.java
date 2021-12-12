package net.wvffle.android.pb.schedule.api.db;

import com.google.gson.JsonObject;

import net.wvffle.android.pb.schedule.api.db.enums.ClassType;
import net.wvffle.android.pb.schedule.api.db.enums.WeekFlags;

import java.util.Date;

public class Schedule  {
    private String hash;
    private final int day;
    private final int hour;
    private final int intervals;
    private final int weekFlags;
    private final String teacher;
    private final String room;
    private final String subject;
    private final ClassType type;
    private final int group;
    private final String degree;
    private final int semester;
    private final String speciality;

    public Schedule(String hash, int day, int hour, int intervals, int weekFlags, String teacher,
                    String room, String subject, String type, int group, String degree, int semester,
                    String speciality) {

        this.hash = hash;
        this.day = day;
        this.hour = hour;
        this.intervals = intervals;
        this.weekFlags = weekFlags;
        this.teacher = teacher;
        this.room = room;
        this.subject = subject;
        this.type = ClassType.valueOfName(type);
        this.group = group;
        this.degree = degree;
        this.semester = semester;
        this.speciality = speciality;
    }

    public static Schedule fromJson(JsonObject schedule) {
        return new Schedule(
                schedule.get("hash").getAsString(),
                schedule.get("day").getAsInt(),
                schedule.get("hour").getAsInt(),
                schedule.get("intervals").getAsInt(),
                schedule.get("weekFlags").getAsInt(),
                schedule.get("teacher").getAsString(),
                schedule.get("room").getAsString(),
                schedule.get("subject").getAsString(),
                schedule.get("type").getAsType(),
                schedule.get("group").getAsInt(),
                schedule.get("degree").getAsString(),
                schedule.get("semestr").getAsInt(),
                schedule.get("speciality").getAsString()

        );
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

    public Teacher getTeacher() {
        return null;
    }

    public String getTeacherHash () {
        return teacher;
    }

    public Room getRoom() {
        return null;
    }

    public String getRoomHash () {
        return room;
    }

    public Subject getSubject() {
        return null;
    }

    public String getSubjectHash () {
        return subject;
    }

    public ClassType getType() {
        return type;
    }

    public int getGroup() {
        return group;
    }

    public String getDegree() {
        return degree;
    }

    public int getSemester() {
        return semester;
    }

    public Speciality getSpeciality() {
        return null;
    }

    public String getSpecialityHash () {
        return speciality;
    }

    public boolean isEven () {
        return (weekFlags & WeekFlags.EVEN) > 0;
    }

    public boolean isOdd () {
        return (weekFlags & WeekFlags.ODD) > 0;
    }

    public boolean isFull () {
        return isEven() && isOdd();
    }

    public boolean isHalf () {
        return !isFull();
    }
}