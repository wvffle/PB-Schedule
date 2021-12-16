package net.wvffle.android.pb.schedule.api;

import com.google.gson.JsonArray;
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

import java.util.HashMap;
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

    public static void getUpdates () {
        String res = HTTPClient.get(HOST + "/updates");
        assert res != null;

        JsonArray updates = JsonParser.parseString(res).getAsJsonArray();
        for (JsonElement element : updates) {
            JsonObject update = element.getAsJsonObject();
            System.out.println(update.get("hash").getAsString());
            System.out.println(update.get("date").getAsLong());
            System.out.println("---");
        }
    }

    private static Model getCollectionEntry (ModelType type, String hash) {
        String res = HTTPClient.get(HOST + routeMap.get(type) + "/" + hash);
        assert res != null;

        return ModelFactory.createCollectionEntry(
                JsonParser.parseString(res).getAsJsonObject(),
                type
        );
    }

    public static Room getRoom (String hash) {
        return (Room) getCollectionEntry(ModelType.ROOM, hash);
    }

    public static Title getTitle (String hash) {
        return (Title) getCollectionEntry(ModelType.TITLE, hash);
    }

    public static Degree getDegree (String hash) {
        return (Degree) getCollectionEntry(ModelType.DEGREE, hash);
    }

    public static Subject getSubject (String hash) {
        return (Subject) getCollectionEntry(ModelType.SUBJECT, hash);
    }

    public static Teacher getTeacher (String hash) {
        return (Teacher) getCollectionEntry(ModelType.TEACHER, hash);
    }

    public static Schedule getSchedule (String hash) {
        return (Schedule) getCollectionEntry(ModelType.SCHEDULE, hash);
    }

    public static Speciality getSpeciality (String hash) {
        return (Speciality) getCollectionEntry(ModelType.SPECIALITY, hash);
    }
}
