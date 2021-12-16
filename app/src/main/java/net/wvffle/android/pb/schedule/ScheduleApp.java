package net.wvffle.android.pb.schedule;

import android.app.Application;

public class ScheduleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ObjectBox.init(this);
    }
}
