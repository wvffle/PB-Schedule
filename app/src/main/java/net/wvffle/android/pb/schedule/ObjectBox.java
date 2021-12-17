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

/**
 * Facade for MyObjectBox
 */
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

    /**
     * Return a box of rooms
     * @return Box of rooms
     */
    public static Box<Room> getRoomBox () {
        return boxStore.boxFor(Room.class);
    }

    /**
     * Return a box of degrees
     * @return Box of degrees
     */
    public static Box<Degree> getDegreeBox () {
        return boxStore.boxFor(Degree.class);
    }

    /**
     * Return a box of schedules
     * @return Box of schedules
     */
    public static Box<Schedule> getScheduleBox () {
        return boxStore.boxFor(Schedule.class);
    }

    /**
     * Return a box of specialities
     * @return Box of specialities
     */
    public static Box<Speciality> getSpecialityBox () {
        return boxStore.boxFor(Speciality.class);
    }

    /**
     * Return a box of subjects
     * @return Box of subjects
     */
    public static Box<Subject> getSubjectBox () {
        return boxStore.boxFor(Subject.class);
    }

    /**
     * Return a box of teachers
     * @return Box of teachers
     */
    public static Box<Teacher> getTeacherBox () {
        return boxStore.boxFor(Teacher.class);
    }

    /**
     * Return a box of titles
     * @return Box of titles
     */
    public static Box<Title> getTitleBox () {
        return boxStore.boxFor(Title.class);
    }

    /**
     * Return a box of updates
     * @return Box of updates
     */
    public static Box<Update> getUpdateBox () {
        return boxStore.boxFor(Update.class);
    }
}
