package net.wvffle.android.pb.schedule.api.update;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.wvffle.android.pb.schedule.api.model.Model;
import net.wvffle.android.pb.schedule.api.model.ModelFactory;
import net.wvffle.android.pb.schedule.api.model.ModelType;
import net.wvffle.android.pb.schedule.models.Degree;
import net.wvffle.android.pb.schedule.models.Room;
import net.wvffle.android.pb.schedule.models.Schedule;
import net.wvffle.android.pb.schedule.models.Speciality;
import net.wvffle.android.pb.schedule.models.Subject;
import net.wvffle.android.pb.schedule.models.Teacher;
import net.wvffle.android.pb.schedule.models.Title;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.sentry.Sentry;

public class UpdateData implements Serializable {
    protected final Map<ModelType, List<Model>> data = new HashMap<>();
    private final JsonObject object;

    /**
     * Create new update data object from JSON
     * @param json JsonObject with the update data
     */
    public UpdateData(JsonObject json) {
        object = json.getAsJsonObject();
        for (String key : object.keySet()) {
            ModelType type = ModelType.valueOfName(key);
            assert type != null;

            List<Model> list = new ArrayList<>();
            data.put(type, list);

            for (JsonElement element : object.getAsJsonArray(key)) {
                try {
                    list.add(ModelFactory.createModel(element.getAsJsonObject(), type));
                } catch (Exception e) {
                    Sentry.captureException(e);
                }
            }
        }
    }

    /**
     * Get list of rooms that are currently in the API database
     * @return List of rooms
     */
    public List<Room> getRooms() {
        return data.get(ModelType.ROOM)
                .stream()
                .map(Room.class::cast)
                .collect(Collectors.toList());
    }

    /**
     * Get list of titles that are currently in the API database
     * @return List of titles
     */
    public List<Title> getTitles() {
        return data.get(ModelType.TITLE)
                .stream()
                .map(Title.class::cast)
                .collect(Collectors.toList());
    }

    /**
     * Get list of degrees that are currently in the API database
     * @return List of degrees
     */
    public List<Degree> getDegrees() {
        return data.get(ModelType.DEGREE)
                .stream()
                .map(Degree.class::cast)
                .collect(Collectors.toList());
    }

    /**
     * Get list of subjects that are currently in the API database
     * @return List of subjects
     */
    public List<Subject> getSubjects() {
        return data.get(ModelType.SUBJECT)
                .stream()
                .map(Subject.class::cast)
                .collect(Collectors.toList());
    }

    /**
     * Get list of teachers that are currently in the API database
     * @return List of teachers
     */
    public List<Teacher> getTeachers() {
        return data.get(ModelType.TEACHER)
                .stream()
                .map(Teacher.class::cast)
                .collect(Collectors.toList());
    }

    /**
     * Get list of schedule entries that are currently in the API database
     * @return List of schedule entries
     */
    public List<Schedule> getSchedules() {
        return data.get(ModelType.SCHEDULE)
                .stream()
                .map(Schedule.class::cast)
                .collect(Collectors.toList());
    }

    /**
     * Get list of specialities that are currently in the API database
     * @return List of specialities
     */
    public List<Speciality> getSpecialities() {
        return data.get(ModelType.SPECIALITY)
                .stream()
                .map(Speciality.class::cast)
                .collect(Collectors.toList());
    }

    /**
     * Serialize the update data into a string for database storage
     * @return serialized update data
     */
    public String serialize () {
        return object.toString();
    }

    // TODO [#32]: Add tests for serialization/deserialization of UpdateData
    /**
     * Deserialize a string into the update data
     * @return deserialized update data
     */
    public static UpdateData deserialize (String serialized) {
        return new UpdateData(JsonParser.parseString(serialized).getAsJsonObject());
    }
}
