package net.wvffle.android.pb.schedule.api.model;

import com.google.gson.JsonObject;

import java.lang.reflect.InvocationTargetException;

import io.sentry.Sentry;

public class ModelFactory<T> {
    /**
     * Create a model from JSON
     * @param json JsonObject object
     * @param type ModelType type
     * @return a new model instance
     */
    public static Model createModel(JsonObject json, ModelType type) {
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
