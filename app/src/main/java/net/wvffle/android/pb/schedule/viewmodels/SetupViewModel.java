package net.wvffle.android.pb.schedule.viewmodels;

import android.os.Handler;
import android.widget.CompoundButton;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import net.wvffle.android.pb.schedule.ObjectBox;
import net.wvffle.android.pb.schedule.api.setup.GroupPair;
import net.wvffle.android.pb.schedule.api.setup.SetupData;
import net.wvffle.android.pb.schedule.api.setup.SetupDataBuilder;
import net.wvffle.android.pb.schedule.models.Degree;
import net.wvffle.android.pb.schedule.models.Update;
import net.wvffle.android.pb.schedule.models.Update_;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import io.objectbox.query.QueryBuilder;

public class SetupViewModel extends ViewModel {
    private final MutableLiveData<Update> update = new MutableLiveData<>(
            ObjectBox.getUpdateBox()
                    .query()
                    .order(Update_.id, QueryBuilder.DESCENDING)
                    .build()
                    .findFirst()
    );
    private final MutableLiveData<List<Degree>> degrees = new MutableLiveData<>();
    private final MutableLiveData<Degree> degree = new MutableLiveData<>(null);

    private final MutableLiveData<Integer> maxSteps = new MutableLiveData<>(1);
    private final MutableLiveData<List<String>> semesters = new MutableLiveData<>();
    private final MutableLiveData<List<GroupPair>> groups = new MutableLiveData<>();

    private final MutableLiveData<Integer> checkedGroups = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> semester = new MutableLiveData<>(-1);
    private final SetupDataBuilder setupDataBuilder = new SetupDataBuilder();

    public MutableLiveData<Update> getUpdate() {
        return update;
    }

    public MutableLiveData<List<Degree>> getDegrees() {
        return degrees;
    }

    public void setDegrees(List<Degree> degreeList) {
        degrees.setValue(degreeList);
    }

    public MutableLiveData<Integer> getMaxSteps() {
        return maxSteps;
    }

    public void setMaxStep(int maxStep) {
        maxSteps.setValue(maxStep);
    }

    public MutableLiveData<Degree> getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) {
        this.degree.setValue(degree);
        setupDataBuilder.setDegree(degree);
    }

    public MutableLiveData<List<String>> getSemesters() {
        return semesters;
    }

    public void setSemesters(List<String> semesters) {
        this.semesters.setValue(semesters);
    }

    public MutableLiveData<List<GroupPair>> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupPair> groups) {
        this.groups.setValue(groups);
    }

    public MutableLiveData<Integer> getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester.setValue(semester);
        setupDataBuilder.setSemester(semester);
    }

    public void onGroupChecked(CompoundButton button, boolean check) {
        new Handler().postDelayed(() -> {
            List<GroupPair> selected = Objects.requireNonNull(groups.getValue()).stream()
                    .filter(GroupPair::isSelected)
                    .collect(Collectors.toList());

            checkedGroups.postValue(selected.size());
            setupDataBuilder.setGroupPairs(selected);
        }, 0);
    }

    public MutableLiveData<Integer> getCheckedGroups() {
        return checkedGroups;
    }

    public SetupData buildSetupData() {
        return setupDataBuilder.build();
    }

    public void setSelectedIds(List<Long> selectedIds) {
        setupDataBuilder.setSelectedSchedules(selectedIds);
    }
}
