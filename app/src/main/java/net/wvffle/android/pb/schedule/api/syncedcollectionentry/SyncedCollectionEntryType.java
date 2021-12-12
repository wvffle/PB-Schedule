package net.wvffle.android.pb.schedule.api.syncedcollectionentry;

import net.wvffle.android.pb.schedule.api.syncedcollectionentry.entries.Degree;
import net.wvffle.android.pb.schedule.api.syncedcollectionentry.entries.Room;
import net.wvffle.android.pb.schedule.api.syncedcollectionentry.entries.Schedule;
import net.wvffle.android.pb.schedule.api.syncedcollectionentry.entries.Speciality;
import net.wvffle.android.pb.schedule.api.syncedcollectionentry.entries.Subject;
import net.wvffle.android.pb.schedule.api.syncedcollectionentry.entries.Teacher;
import net.wvffle.android.pb.schedule.api.syncedcollectionentry.entries.Title;

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
