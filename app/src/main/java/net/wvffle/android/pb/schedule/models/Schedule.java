package net.wvffle.android.pb.schedule.models;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import net.wvffle.android.pb.schedule.api.converters.ClassTypeConverter;
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

    /**
     * Create a new Schedule from JSON
     * @param schedule JsonObject containing schedule data
     * @return new schedule model
     */
    public static Schedule fromJson(JsonObject schedule) {
        JsonElement room = schedule.get("room");
        JsonElement degree = schedule.get("degree");
        JsonElement teacher = schedule.get("teacher");
        JsonElement subject = schedule.get("subject");
        JsonElement speciality = schedule.get("speciality");

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
                (room instanceof JsonNull) ? 0 : room.getAsLong(),
                (degree instanceof JsonNull) ? 0 : degree.getAsLong(),
                (teacher instanceof JsonNull) ? 0 : teacher.getAsLong(),
                (subject instanceof JsonNull) ? 0 : subject.getAsLong(),
                (speciality instanceof JsonNull) ? 0 : speciality.getAsLong()
        );
    }

    /**
     * Return hash of the schedule
     * @return String hash of the schedule
     */
    public String getHash() {
        return hash;
    }

    /**
     * Return day number of the schedule
     * @return Day number of the schedule
     */
    public int getDay() {
        return day;
    }

    /**
     * Return hour index of the schedule
     * @return Hour index of the schedule
     */
    public int getHour() {
        return hour;
    }

    /**
     * Return number of intervals of the schedule
     * @return Number of intervals of the schedule
     */
    public int getIntervals() {
        return intervals;
    }

    /**
     * Return week flags of the schedule
     * @return Week flags of the schedule
     */
    public int getWeekFlags() {
        return weekFlags;
    }

    /**
     * Return teacher of the schedule
     * @return Teacher of the schedule
     */
    public Teacher getTeacher() {
        return teacher.getTarget();
    }

    /**
     * Return room of the schedule
     * @return Room of the schedule
     */
    public Room getRoom() {
        return room.getTarget();
    }

    /**
     * Return subject of the schedule
     * @return Subject of the schedule
     */
    public Subject getSubject() {
        return subject.getTarget();
    }

    /**
     * Return class type of the schedule
     * @return ClassType of the schedule
     */
    public ClassType getType() {
        return type;
    }

    /**
     * Return group number of the schedule
     * @return Group number of the schedule
     */
    public int getGroup() {
        return group;
    }

    /**
     * Return degree of the schedule
     * @return Degree of the schedule
     */
    public Degree getDegree() {
        return degree.getTarget();
    }

    /**
     * Return semester number of the schedule
     * @return semester number of the schedule
     */
    public int getSemester() {
        return semester;
    }

    /**
     * Return speciality of the schedule
     * @return Speciality of the schedule
     */
    public Speciality getSpeciality() {
        return speciality.getTarget();
    }

    /**
     * Check if schedule is available in even weeks
     * @return Whether or not the schedule is available
     */
    public boolean isEvenWeeks () {
        return (weekFlags & WeekFlags.EVEN) > 0;
    }

    /**
     * Check if schedule is available in odd weeks
     * @return Whether or not the schedule is available
     */
    public boolean isOddWeeks () {
        return (weekFlags & WeekFlags.ODD) > 0;
    }

    /**
     * Check if schedule is available every week
     * @return Whether or not the schedule is available
     */
    public boolean isEveryWeek () {
        return isEvenWeeks() && isOddWeeks();
    }
}