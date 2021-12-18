package net.wvffle.android.pb.schedule.api.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;


import net.wvffle.android.pb.schedule.api.model.ModelFactory;
import net.wvffle.android.pb.schedule.api.model.ModelType;
import net.wvffle.android.pb.schedule.models.Degree;

import java.lang.reflect.Type;


class DegreeDeserializer implements JsonDeserializer<Degree> {
    @Override
    public Degree deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return (Degree) ModelFactory.createModel(json.getAsJsonObject(), ModelType.DEGREE);
    }
}
