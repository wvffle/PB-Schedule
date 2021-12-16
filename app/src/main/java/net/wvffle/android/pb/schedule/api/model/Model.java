package net.wvffle.android.pb.schedule.api.model;

import com.google.gson.JsonObject;

public interface Model {
    static Model fromJson (JsonObject object) {
        return null;
    }

    String getHash ();
}
