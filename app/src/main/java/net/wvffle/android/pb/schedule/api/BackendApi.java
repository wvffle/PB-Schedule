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

import io.sentry.Sentry;

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

    /**
     * Return a list of updates
     * @return List of UpdateEntry objects
     */
    public static List<UpdateEntry> getUpdates () {
        String res = HTTPClient.get(HOST + "/updates");
        assert res != null;

        List<UpdateEntry> updates = new ArrayList<>();
        for (JsonElement element : JsonParser.parseString(res).getAsJsonArray()) {
            updates.add(UpdateEntry.fromJson(element.getAsJsonObject()));
        }

        return updates;
    }

    /**
     * Return an update
     * @param hash String hash of an update
     * @return Update model
     */
    public static Update getUpdate (String hash) {
        String res = HTTPClient.get(HOST + "/updates/" + hash);
        assert res != null;

        try {
            return Update.fromJson(JsonParser.parseString(res).getAsJsonObject());
        } catch (ParseException e) {
            e.printStackTrace();
            Sentry.captureException(e);
        }

        return null;
    }

    private static Model getModelByType(ModelType type, String hash) {
        String res = HTTPClient.get(HOST + routeMap.get(type) + "/" + hash);
        assert res != null;

        return ModelFactory.createModel(
                JsonParser.parseString(res).getAsJsonObject(),
                type
        );
    }

    /**
     * Return a room
     * @param hash String hash of a room
     * @return Room model
     */
    public static Room getRoom (String hash) {
        return (Room) getModelByType(ModelType.ROOM, hash);
    }

    /**
     * Return a title
     * @param hash String hash of a title
     * @return Title model
     */
    public static Title getTitle (String hash) {
        return (Title) getModelByType(ModelType.TITLE, hash);
    }

    /**
     * Return a degree
     * @param hash String hash of a degree
     * @return Degree model
     */
    public static Degree getDegree (String hash) {
        return (Degree) getModelByType(ModelType.DEGREE, hash);
    }

    /**
     * Return a subject
     * @param hash String hash of a subject
     * @return Subject model
     */
    public static Subject getSubject (String hash) {
        return (Subject) getModelByType(ModelType.SUBJECT, hash);
    }

    /**
     * Return a teacher
     * @param hash String hash of a teacher
     * @return Teacher model
     */
    public static Teacher getTeacher (String hash) {
        return (Teacher) getModelByType(ModelType.TEACHER, hash);
    }

    /**
     * Return a schedule
     * @param hash String hash of a schedule
     * @return Schedule model
     */
    public static Schedule getSchedule (String hash) {
        return (Schedule) getModelByType(ModelType.SCHEDULE, hash);
    }

    /**
     * Return a speciality
     * @param hash String hash of a speciality
     * @return Speciality model
     */
    public static Speciality getSpeciality (String hash) {
        return (Speciality) getModelByType(ModelType.SPECIALITY, hash);
    }
}
