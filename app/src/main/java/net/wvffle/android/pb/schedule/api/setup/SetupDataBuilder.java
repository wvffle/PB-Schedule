package net.wvffle.android.pb.schedule.api.setup;

import static net.wvffle.android.pb.schedule.util.IntegersUtil.getIntFromEnd;

import net.wvffle.android.pb.schedule.models.Degree;

import java.util.Arrays;
import java.util.List;

public class SetupDataBuilder {
    private final SetupData data;

    public SetupDataBuilder() {
        data = new SetupData();
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

    public SetupData build() {
        return data;
    }

}
