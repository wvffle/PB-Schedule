package net.wvffle.android.pb.schedule.api.setup;

import net.wvffle.android.pb.schedule.models.Degree;
import net.wvffle.android.pb.schedule.util.Serializer;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.sentry.Sentry;

public class SetupData implements Serializable {
    protected List<GroupPair> groups = new ArrayList<>();
    protected Degree degree;
    protected int semester;

    public static SetupData fromString(String string) {
        try {
            return (SetupData) Serializer.getInstance().fromString(string);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            Sentry.captureException(e);
        }

        return null;
    }

    public String toString() {
        try {
            return Serializer.getInstance().toString(this);
        } catch (IOException e) {
            e.printStackTrace();
            Sentry.captureException(e);
        }

        return null;
    }

    public List<GroupPair> getGroups() {
        return groups;
    }

    public Degree getDegree() {
        return degree;
    }

    public int getSemester() {
        return semester;
    }
}
