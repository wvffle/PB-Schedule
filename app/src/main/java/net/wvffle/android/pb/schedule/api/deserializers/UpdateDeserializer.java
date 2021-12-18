package net.wvffle.android.pb.schedule.api.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import net.wvffle.android.pb.schedule.models.Update;

import java.lang.reflect.Type;
import java.text.ParseException;

import io.sentry.Sentry;

public class UpdateDeserializer implements JsonDeserializer<Update> {
    @Override
    public Update deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            return Update.fromJson(json.getAsJsonObject());
        } catch (ParseException e) {
            e.printStackTrace();
            Sentry.captureException(e);
        }
        return null;
    }
}
