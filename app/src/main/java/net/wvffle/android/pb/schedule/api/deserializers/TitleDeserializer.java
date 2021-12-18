package net.wvffle.android.pb.schedule.api.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;


import net.wvffle.android.pb.schedule.api.model.ModelFactory;
import net.wvffle.android.pb.schedule.api.model.ModelType;
import net.wvffle.android.pb.schedule.models.Title;

import java.lang.reflect.Type;

public class TitleDeserializer implements JsonDeserializer<Title> {
    @Override
    public Title deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return (Title) ModelFactory.createModel(json.getAsJsonObject(), ModelType.TITLE);
    }
}
