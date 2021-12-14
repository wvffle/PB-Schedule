package net.wvffle.android.pb.schedule.api.syncedcollectionentry;

import net.wvffle.android.pb.schedule.api.db.models.Degree;
import net.wvffle.android.pb.schedule.api.db.models.Room;
import net.wvffle.android.pb.schedule.api.db.models.Schedule;
import net.wvffle.android.pb.schedule.api.db.models.Speciality;
import net.wvffle.android.pb.schedule.api.db.models.Subject;
import net.wvffle.android.pb.schedule.api.db.models.Teacher;
import net.wvffle.android.pb.schedule.api.db.models.Title;

public enum SyncedCollectionEntryType {
    ROOM(Room.class),
    DEGREE(Degree.class),
    SCHEDULE(Schedule.class),
    SPECIALITY(Speciality.class),
    SUBJECT(Subject.class),
    TEACHER(Teacher.class),
    TITLE(Title.class);

    private final Class<? extends SyncedCollectionEntry> clazz;
    SyncedCollectionEntryType(Class<? extends SyncedCollectionEntry> clazz) {
        this.clazz = clazz;
    }

    public Class<? extends SyncedCollectionEntry> getSyncedCollectionEntryClass () {
        return clazz;
    }
}
