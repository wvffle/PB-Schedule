package net.wvffle.android.pb.schedule;

import android.app.Application;

import net.wvffle.android.pb.schedule.api.db.ObjectBox;

public class ScheduleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ObjectBox.init(this);
    }
}
