package net.wvffle.android.pb.schedule.api.setup;

import androidx.annotation.NonNull;

import net.wvffle.android.pb.schedule.models.Degree;
import net.wvffle.android.pb.schedule.util.Serializer;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.sentry.Sentry;

public class SetupData implements Serializable, Cloneable {
    protected List<GroupPair> groups = new ArrayList<>();
    protected List<Long> selectedSchedules = new ArrayList<>();
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

    @NonNull
    public String toString() {
        try {
            return Serializer.getInstance().toString(this);
        } catch (IOException e) {
            e.printStackTrace();
            Sentry.captureException(e);
        }

        return "";
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

    public List<Long> getSelectedSchedules() {
        return selectedSchedules;
    }

    @NonNull
    public SetupData clone() throws CloneNotSupportedException {
        SetupData data = (SetupData) super.clone();
        data.selectedSchedules = new ArrayList<>(selectedSchedules);
        data.groups = new ArrayList<>(groups);
        data.semester = semester;
        data.degree = degree;

        return data;
    }
}
