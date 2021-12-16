package net.wvffle.android.pb.schedule.api.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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

    public UpdateDiff(JsonObject json, UpdateData data) {
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

    public Map<ModelType, List<String>> getAdded () {
        return added;
    }

    public Map<ModelType, List<String>> getRemoved () {
        return removed;
    }

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
}
