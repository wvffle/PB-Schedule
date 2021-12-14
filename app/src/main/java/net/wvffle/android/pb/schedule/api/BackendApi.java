package net.wvffle.android.pb.schedule.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.wvffle.android.pb.schedule.api.syncedcollectionentry.SyncedCollectionEntry;
import net.wvffle.android.pb.schedule.api.syncedcollectionentry.SyncedCollectionEntryFactory;
import net.wvffle.android.pb.schedule.api.syncedcollectionentry.SyncedCollectionEntryType;
import net.wvffle.android.pb.schedule.api.db.models.Degree;
import net.wvffle.android.pb.schedule.api.db.models.Room;
import net.wvffle.android.pb.schedule.api.db.models.Schedule;
import net.wvffle.android.pb.schedule.api.db.models.Speciality;
import net.wvffle.android.pb.schedule.api.db.models.Subject;
import net.wvffle.android.pb.schedule.api.db.models.Teacher;
import net.wvffle.android.pb.schedule.api.db.models.Title;

import java.util.HashMap;
import java.util.Map;

public class BackendApi {
    private static final String HOST = "https://but-schedule-server.herokuapp.com";
    private static final Map<SyncedCollectionEntryType, String> routeMap = new HashMap<>();

    static {
        routeMap.put(SyncedCollectionEntryType.ROOM, "/rooms");
        routeMap.put(SyncedCollectionEntryType.TITLE, "/titles");
        routeMap.put(SyncedCollectionEntryType.DEGREE, "/degrees");
        routeMap.put(SyncedCollectionEntryType.SUBJECT, "/subjects");
        routeMap.put(SyncedCollectionEntryType.TEACHER, "/teachers");
        routeMap.put(SyncedCollectionEntryType.SCHEDULE, "/schedules");
        routeMap.put(SyncedCollectionEntryType.SPECIALITY, "/specialities");
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

    private static SyncedCollectionEntry getCollectionEntry (SyncedCollectionEntryType type, String hash) {
        String res = HTTPClient.get(HOST + routeMap.get(type) + "/" + hash);
        assert res != null;

        return SyncedCollectionEntryFactory.createCollectionEntry(
                JsonParser.parseString(res).getAsJsonObject(),
                type
        );
    }

    public static Room getRoom (String hash) {
        return (Room) getCollectionEntry(SyncedCollectionEntryType.ROOM, hash);
    }

    public static Title getTitle (String hash) {
        return (Title) getCollectionEntry(SyncedCollectionEntryType.TITLE, hash);
    }

    public static Degree getDegree (String hash) {
        return (Degree) getCollectionEntry(SyncedCollectionEntryType.DEGREE, hash);
    }

    public static Subject getSubject (String hash) {
        return (Subject) getCollectionEntry(SyncedCollectionEntryType.SUBJECT, hash);
    }

    public static Teacher getTeacher (String hash) {
        return (Teacher) getCollectionEntry(SyncedCollectionEntryType.TEACHER, hash);
    }

    public static Schedule getSchedule (String hash) {
        return (Schedule) getCollectionEntry(SyncedCollectionEntryType.SCHEDULE, hash);
    }

    public static Speciality getSpeciality (String hash) {
        return (Speciality) getCollectionEntry(SyncedCollectionEntryType.SPECIALITY, hash);
    }
}
