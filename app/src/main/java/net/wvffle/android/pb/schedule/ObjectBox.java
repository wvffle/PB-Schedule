package net.wvffle.android.pb.schedule;

import android.content.Context;
import android.util.Log;

import net.wvffle.android.pb.schedule.models.Degree;
import net.wvffle.android.pb.schedule.models.MyObjectBox;
import net.wvffle.android.pb.schedule.models.Room;
import net.wvffle.android.pb.schedule.models.Schedule;
import net.wvffle.android.pb.schedule.models.Speciality;
import net.wvffle.android.pb.schedule.models.Subject;
import net.wvffle.android.pb.schedule.models.Teacher;
import net.wvffle.android.pb.schedule.models.Title;
import net.wvffle.android.pb.schedule.models.Update;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;

public class ObjectBox {
    private static BoxStore boxStore;

    public static void init (Context context) {
        boxStore = MyObjectBox.builder()
                .androidContext(context)
                .build();

        if (BuildConfig.DEBUG) {
            boolean started = new AndroidObjectBrowser(boxStore).start(context);
            Log.i("ObjectBrowser", "Started: " + started);
        }
    }

    public static Box<Room> getRoomBox () {
        return boxStore.boxFor(Room.class);
    }

    public static Box<Degree> getDegreeBox () {
        return boxStore.boxFor(Degree.class);
    }

    public static Box<Schedule> getScheduleBox () {
        return boxStore.boxFor(Schedule.class);
    }

    public static Box<Speciality> getSpecialityBox () {
        return boxStore.boxFor(Speciality.class);
    }

    public static Box<Subject> getSubjectBox () {
        return boxStore.boxFor(Subject.class);
    }

    public static Box<Teacher> getTeacherBox () {
        return boxStore.boxFor(Teacher.class);
    }

    public static Box<Title> getTitleBox () {
        return boxStore.boxFor(Title.class);
    }

    public static Box<Update> getUpdateBox () {
        return boxStore.boxFor(Update.class);
    }
}
