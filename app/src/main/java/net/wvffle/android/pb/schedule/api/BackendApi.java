package net.wvffle.android.pb.schedule.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import net.wvffle.android.pb.schedule.api.model.Model;
import net.wvffle.android.pb.schedule.api.model.ModelFactory;
import net.wvffle.android.pb.schedule.api.model.ModelType;
import net.wvffle.android.pb.schedule.api.update.UpdateEntry;
import net.wvffle.android.pb.schedule.models.Degree;
import net.wvffle.android.pb.schedule.models.Room;
import net.wvffle.android.pb.schedule.models.Schedule;
import net.wvffle.android.pb.schedule.models.Speciality;
import net.wvffle.android.pb.schedule.models.Subject;
import net.wvffle.android.pb.schedule.models.Teacher;
import net.wvffle.android.pb.schedule.models.Title;
import net.wvffle.android.pb.schedule.models.Update;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BackendApi {
    private static final String HOST = "https://but-schedule-server.herokuapp.com";
    private static final Map<ModelType, String> routeMap = new HashMap<>();

    static {
        routeMap.put(ModelType.ROOM, "/rooms");
        routeMap.put(ModelType.TITLE, "/titles");
        routeMap.put(ModelType.DEGREE, "/degrees");
        routeMap.put(ModelType.SUBJECT, "/subjects");
        routeMap.put(ModelType.TEACHER, "/teachers");
        routeMap.put(ModelType.SCHEDULE, "/schedules");
        routeMap.put(ModelType.SPECIALITY, "/specialities");
    }

    public static List<UpdateEntry> getUpdates () {
        String res = HTTPClient.get(HOST + "/updates");
        assert res != null;

        List<UpdateEntry> updates = new ArrayList<>();
        for (JsonElement element : JsonParser.parseString(res).getAsJsonArray()) {
            updates.add(UpdateEntry.fromJson(element.getAsJsonObject()));
        }

        return updates;
    }

    public static Update getUpdate (String hash) {
        String res = HTTPClient.get(HOST + "/updates/" + hash);
        assert res != null;

        try {
            return Update.fromJson(JsonParser.parseString(res).getAsJsonObject());
        } catch (ParseException e) {
            e.printStackTrace();
            // TODO: Add Sentry
        }

        return null;
    }

    private static Model getModelByType(ModelType type, String hash) {
        String res = HTTPClient.get(HOST + routeMap.get(type) + "/" + hash);
        assert res != null;

        return ModelFactory.createCollectionEntry(
                JsonParser.parseString(res).getAsJsonObject(),
                type
        );
    }

    public static Room getRoom (String hash) {
        return (Room) getModelByType(ModelType.ROOM, hash);
    }

    public static Title getTitle (String hash) {
        return (Title) getModelByType(ModelType.TITLE, hash);
    }

    public static Degree getDegree (String hash) {
        return (Degree) getModelByType(ModelType.DEGREE, hash);
    }

    public static Subject getSubject (String hash) {
        return (Subject) getModelByType(ModelType.SUBJECT, hash);
    }

    public static Teacher getTeacher (String hash) {
        return (Teacher) getModelByType(ModelType.TEACHER, hash);
    }

    public static Schedule getSchedule (String hash) {
        return (Schedule) getModelByType(ModelType.SCHEDULE, hash);
    }

    public static Speciality getSpeciality (String hash) {
        return (Speciality) getModelByType(ModelType.SPECIALITY, hash);
    }
}
