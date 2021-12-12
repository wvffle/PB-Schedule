package net.wvffle.android.pb.schedule.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class BackendApi {
    private static final String HOST = "https://but-schedule-server.herokuapp.com";

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

    public static JsonObject getRoom (String hash) {
        String res = HTTPClient.get(HOST + "/rooms/" + hash);
        assert res != null;

        return JsonParser.parseString(res).getAsJsonObject();
    }
}
