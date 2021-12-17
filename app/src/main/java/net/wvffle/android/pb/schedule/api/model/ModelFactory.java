package net.wvffle.android.pb.schedule.api.model;

import com.google.gson.JsonObject;

import java.lang.reflect.InvocationTargetException;

import io.sentry.Sentry;

public class ModelFactory {
    public static Model createCollectionEntry (JsonObject json, ModelType type) {
        try {
            Class<? extends Model> clazz = type.getModelClass();
            return clazz.cast(clazz.getDeclaredMethod("fromJson", JsonObject.class).invoke(null, json));
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            Sentry.captureException(e);
        }

        return null;
    }
}
