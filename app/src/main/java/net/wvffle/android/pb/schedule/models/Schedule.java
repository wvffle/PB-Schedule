package net.wvffle.android.pb.schedule.models;

import com.google.gson.JsonObject;

import net.wvffle.android.pb.schedule.api.model.converters.ClassTypeConverter;
import net.wvffle.android.pb.schedule.enums.ClassType;
import net.wvffle.android.pb.schedule.enums.WeekFlags;
import net.wvffle.android.pb.schedule.api.model.Model;

import io.objectbox.annotation.ConflictStrategy;
import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;
import io.objectbox.annotation.Unique;
import io.objectbox.relation.ToOne;

@Entity
public class Schedule implements Model {
    @Id(assignable = true)
    public long id;

    @Index
    @Unique(onConflict = ConflictStrategy.REPLACE)
    private final String hash;

    @Convert(converter = ClassTypeConverter.class, dbType = String.class)
    private final ClassType type;

    private final int day;
    private final int hour;
    private final int group;
    private final int semester;
    private final int intervals;
    private final int weekFlags;

    public ToOne<Room> room;
    public ToOne<Degree> degree;
    public ToOne<Teacher> teacher;
    public ToOne<Subject> subject;
    public ToOne<Speciality> speciality;

    public Schedule(long id, String hash, ClassType type, int day, int hour, int group,
                    int semester, int intervals, int weekFlags, long roomId, long degreeId,
                    long teacherId, long subjectId, long specialityId) {

        this.id = id;
        this.hash = hash;
        this.type = type;

        this.day = day;
        this.hour = hour;
        this.group = group;
        this.semester = semester;
        this.intervals = intervals;
        this.weekFlags = weekFlags;
        this.teacher.setTargetId(teacherId);
        this.room.setTargetId(roomId);
        this.subject.setTargetId(subjectId);
        this.degree.setTargetId(degreeId);
        this.speciality.setTargetId(specialityId);
    }

    public static Schedule fromJson(JsonObject schedule) {
        return new Schedule(
                schedule.get("id").getAsLong(),
                schedule.get("hash").getAsString(),
                ClassType.valueOfName(schedule.get("type").getAsString()),
                schedule.get("day").getAsInt(),
                schedule.get("hour").getAsInt(),
                schedule.get("group").getAsInt(),
                schedule.get("semester").getAsInt(),
                schedule.get("intervals").getAsInt(),
                schedule.get("weekFlags").getAsInt(),
                schedule.get("room").getAsLong(),
                schedule.get("degree").getAsLong(),
                schedule.get("teacher").getAsLong(),
                schedule.get("subject").getAsLong(),
                schedule.get("speciality").getAsLong()
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
        return teacher.getTarget();
    }

    public Room getRoom() {
        return room.getTarget();
    }

    public Subject getSubject() {
        return subject.getTarget();
    }

    public ClassType getType() {
        return type;
    }

    public int getGroup() {
        return group;
    }

    public Degree getDegree() {
        return degree.getTarget();
    }

    public int getSemester() {
        return semester;
    }

    public Speciality getSpeciality() {
        return speciality.getTarget();
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