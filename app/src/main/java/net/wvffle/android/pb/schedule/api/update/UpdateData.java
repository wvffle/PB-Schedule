package net.wvffle.android.pb.schedule.api.update;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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

public class UpdateData implements Serializable {
    protected final Map<ModelType, List<Model>> data = new HashMap<>();

    public UpdateData(JsonObject json) {
        JsonObject object = json.getAsJsonObject();
        for (String key : object.keySet()) {
            ModelType type = ModelType.valueOfName(key);
            assert type != null;

            List<Model> list = new ArrayList<>();
            data.put(type, list);

            for (JsonElement element : object.getAsJsonArray(key)) {
                try {
                    list.add(ModelFactory.createCollectionEntry(element.getAsJsonObject(), type));
                } catch (Exception e) {
                    // TODO: Add Sentry
                }
            }
        }
    }

    public List<? extends Room> getRooms() {
        return data.get(ModelType.ROOM)
                .stream()
                .map(Room.class::cast)
                .collect(Collectors.toList());
    }

    public List<Title> getTitles() {
        return data.get(ModelType.TITLE)
                .stream()
                .map(Title.class::cast)
                .collect(Collectors.toList());
    }

    public List<Degree> getDegrees() {
        return data.get(ModelType.DEGREE)
                .stream()
                .map(Degree.class::cast)
                .collect(Collectors.toList());
    }

    public List<Subject> getSubjects() {
        return data.get(ModelType.SUBJECT)
                .stream()
                .map(Subject.class::cast)
                .collect(Collectors.toList());
    }

    public List<Teacher> getTeachers() {
        return data.get(ModelType.TEACHER)
                .stream()
                .map(Teacher.class::cast)
                .collect(Collectors.toList());
    }

    public List<Schedule> getSchedules() {
        return data.get(ModelType.SCHEDULE)
                .stream()
                .map(Schedule.class::cast)
                .collect(Collectors.toList());
    }

    public List<Speciality> getSpecialities() {
        return data.get(ModelType.SPECIALITY)
                .stream()
                .map(Speciality.class::cast)
                .collect(Collectors.toList());
    }
}
