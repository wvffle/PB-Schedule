package net.wvffle.android.pb.schedule.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import net.wvffle.android.pb.schedule.models.Schedule;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ExtraSubjectsViewModel extends ViewModel {
    MutableLiveData<List<Schedule>> filteredClasses = new MutableLiveData<>(Collections.emptyList());
    MutableLiveData<List<Schedule>> classes = new MutableLiveData<>(Collections.emptyList());
    MutableLiveData<Map<Long, Boolean>> selected = new MutableLiveData<>(new HashMap<>());
    MutableLiveData<String> filter = new MutableLiveData<>("");

    public LiveData<List<Schedule>> getClasses() {
        return classes;
    }

    public void setClasses(List<Schedule> classes) {
        this.classes.setValue(classes);

        Map<Long, Boolean> selected = this.selected.getValue();
        assert selected != null;

        for (Schedule clazz : classes) {
            if (!selected.containsKey(clazz.id)) {
                selected.put(clazz.id, false);
            }
        }

        this.selected.setValue(selected);
    }

    public LiveData<Map<Long, Boolean>> getSelected() {
        return selected;
    }

    public void setSelected(Map<Long, Boolean> selected) {
        this.selected.setValue(selected);
    }

    public void select(int position, boolean selected) {
        Objects.requireNonNull(this.selected.getValue()).put(
                Objects.requireNonNull(filteredClasses.getValue()).get(position).id,
                selected
        );

        // NOTE: Update the selected value
        this.selected.setValue(this.selected.getValue());
    }

    public LiveData<String> getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter.setValue(filter.toLowerCase());

        List<Schedule> classes = this.classes.getValue();
        assert classes != null;

        List<Schedule> filtered = classes.stream()
                .filter(schedule -> schedule.getSubject().getName().toLowerCase().contains(filter))
                .collect(Collectors.toList());

        filteredClasses.setValue(filtered);
    }

    public LiveData<List<Schedule>> getFilteredClasses() {
        return filteredClasses;
    }
}
