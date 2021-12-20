package net.wvffle.android.pb.schedule.api.setup;

import static net.wvffle.android.pb.schedule.util.IntegersUtil.getIntFromEnd;

import net.wvffle.android.pb.schedule.models.Degree;

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

    public SetupData build() {
        return data;
    }

}
