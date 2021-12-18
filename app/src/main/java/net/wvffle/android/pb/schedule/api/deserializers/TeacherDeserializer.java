package net.wvffle.android.pb.schedule.api.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;


import net.wvffle.android.pb.schedule.models.Teacher;

import java.lang.reflect.Type;

@SuppressWarnings("unchecked")
public class ModelDeserializer<T extends Model> implements JsonDeserializer<Teacher> {
    @Override
    public Teacher deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return (Teacher) Teacher.fromJson(json.getAsJsonObject());
    }
}