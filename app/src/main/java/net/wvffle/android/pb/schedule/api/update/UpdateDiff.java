package net.wvffle.android.pb.schedule.api.update;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.wvffle.android.pb.schedule.api.model.Model;
import net.wvffle.android.pb.schedule.api.model.ModelType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class UpdateDiff implements Serializable {
    private final Map<ModelType, List<String>> added = new HashMap<>();
    private final Map<ModelType, List<String>> removed = new HashMap<>();
    private final Map<String, Model> data = new HashMap<>();
    private final JsonObject json;
    private final UpdateData updateData;

    /**
     * Create new update diff object from JSON
     * @param json JsonObject with the update diff
     */
    public UpdateDiff(JsonObject json, UpdateData data) {
        this.json = json;
        updateData = data;

        for (List<Model> list : data.data.values()) {
            for (Model model : list) {
                this.data.put(model.getHash(), model);
            }
        }

        JsonObject object = json.getAsJsonObject();
        for (String key : object.keySet()) {
            ModelType type = ModelType.valueOfName(key);
            List<String> addedList = new ArrayList<>();
            List<String> removedList = new ArrayList<>();

            for (JsonElement element : object.getAsJsonArray(key)) {
                JsonObject diff = element.getAsJsonObject();
                List<String> list = diff.get("type").getAsString().equals("+")
                        ? addedList
                        : removedList;

                list.add(diff.get("hash").getAsString());
            }

            added.put(type, addedList);
            removed.put(type, removedList);
        }
    }

    /**
     * Return added model hashes
     * @return map of lists of added model hashes
     */
    public Map<ModelType, List<String>> getAdded () {
        return added;
    }

    /**
     * Return removed model hashes
     * @return map of lists of removed model hashes
     */
    public Map<ModelType, List<String>> getRemoved () {
        return removed;
    }

    /**
     * Return added models
     * @return map of lists of added models
     */
    public Map<ModelType, List<Model>> getAddedModels () {
        Map<ModelType, List<Model>> result = new HashMap<>();

        for (Map.Entry<ModelType, List<String>> entry : added.entrySet()) {
            result.put(
                    entry.getKey(),
                    entry.getValue()
                            .stream()
                            .map(this.data::get)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList())
            );
        }

        return result;
    }

    /**
     * Return removed models
     * @return map of lists of removed models
     */
    public Map<ModelType, List<Model>> getRemovedModels () {
        Map<ModelType, List<Model>> result = new HashMap<>();


        for (Map.Entry<ModelType, List<String>> entry : removed.entrySet()) {
            result.put(
                    entry.getKey(),
                    entry.getValue()
                            .stream()
                            .map(this.data::get)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList())
            );
        }

        return result;
    }

    // TODO [$61bd0a25820545066013c453]: Make UpdateDiff serialized value not depend on UpdateData
    //       Currently we're storing the UpdateData twice in the database.
    /**
     * Serialize the update diff into a string for database storage
     * @return serialized update diff
     */
    public String serialize () {
        JsonObject container = new JsonObject();
        container.addProperty("data", updateData.serialize());
        container.add("json", json);
        return container.toString();
    }

    // TODO [$61bd0a25820545066013c454]: Add tests for serialization/deserialization of UpdateDiff
    /**
     * Deserialize a string into the update diff
     * @return deserialized update diff
     */
    public static UpdateDiff deserialize (String serialized) {
        JsonObject container = JsonParser.parseString(serialized).getAsJsonObject();
        return new UpdateDiff(
                container.getAsJsonObject("json"),
                UpdateData.deserialize(container.get("data").getAsString())
        );
    }
}
