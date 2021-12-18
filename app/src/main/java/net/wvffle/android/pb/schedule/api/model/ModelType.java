package net.wvffle.android.pb.schedule.api.model;

import net.wvffle.android.pb.schedule.models.Degree;
import net.wvffle.android.pb.schedule.models.Room;
import net.wvffle.android.pb.schedule.models.Schedule;
import net.wvffle.android.pb.schedule.models.Speciality;
import net.wvffle.android.pb.schedule.models.Subject;
import net.wvffle.android.pb.schedule.models.Teacher;
import net.wvffle.android.pb.schedule.models.Title;

public enum ModelType {
    ROOM(Room.class),
    DEGREE(Degree.class),
    SCHEDULE(Schedule.class),
    SPECIALITY(Speciality.class),
    SUBJECT(Subject.class),
    TEACHER(Teacher.class),
    TITLE(Title.class);

    private final Class<? extends Model> clazz;
    ModelType(Class<? extends Model> clazz) {
        this.clazz = clazz;
    }

    public Class<? extends Model> getModelClass() {
        return clazz;
    }

    /**
     * Return a ModelType for given name
     * @param name the name string
     * @return ModelType
     */
    public static ModelType valueOfName (String name) {
        switch (name) {
            case "room":
            case "rooms":
                return ROOM;

            case "degree":
            case "degrees":
                return DEGREE;

            case "schedule":
            case "schedules":
                return SCHEDULE;

            case "speciality":
            case "specialities":
                return SPECIALITY;

            case "subject":
            case "subjects":
                return SUBJECT;

            case "teacher":
            case "teachers":
                return TEACHER;

            case "title":
            case "titles":
                return TITLE;

        }

        return null;
    }
}
