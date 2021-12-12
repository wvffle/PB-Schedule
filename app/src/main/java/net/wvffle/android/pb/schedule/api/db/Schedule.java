package net.wvffle.android.pb.schedule.api.db;


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
    private final Date updatedAt;

    public Schedule(String hash, int day, int hour, int intervals, int weekFlags, String teacher,
                    String room, String subject, String type, int group, String degree, int semester,
                    String speciality, int updatedAt) {

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
        this.updatedAt = new Date(updatedAt);
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

    public Date getUpdatedAt() {
        return updatedAt;
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