package net.wvffle.android.pb.schedule.api.setup;

import static net.wvffle.android.pb.schedule.util.IntegersUtil.getIntFromEnd;

import net.wvffle.android.pb.schedule.models.Degree;

import java.util.Arrays;
import java.util.List;

import io.sentry.Sentry;

public class SetupDataBuilder {
    private final SetupData data;

    public SetupDataBuilder() {
        data = new SetupData();
    }

    private SetupDataBuilder(SetupData setupData) {
        SetupData data = null;

        try {
            data = setupData.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            Sentry.captureException(e);
        }

        this.data = data == null
                ? new SetupData()
                : data;
    }

    public static SetupDataBuilder from(SetupData setupData) {
        return new SetupDataBuilder(setupData);
    }

    public SetupDataBuilder setDegree(Degree degree) {
        data.degree = degree;
        return this;
    }

    public SetupDataBuilder setSemester(String semester) {
        data.semester = getIntFromEnd(semester);
        return this;
    }

    public SetupDataBuilder setSemester(int semester) {
        data.semester = semester;
        return this;
    }

    public SetupDataBuilder addGroupPairs(GroupPair... groups) {
        data.groups.addAll(Arrays.asList(groups));
        return this;
    }

    public SetupDataBuilder setGroupPairs(List<GroupPair> groups) {
        data.groups = groups;
        return this;
    }

    public SetupDataBuilder addSelectedSchedules(Long... selectedSchedules) {
        data.selectedSchedules.addAll(Arrays.asList(selectedSchedules));
        return this;
    }

    public SetupDataBuilder setSelectedSchedules(List<Long> selectedSchedules) {
        data.selectedSchedules = selectedSchedules;
        return this;
    }

    public SetupData build() {
        return data;
    }

}
