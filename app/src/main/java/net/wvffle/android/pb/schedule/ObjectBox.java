package net.wvffle.android.pb.schedule;

import android.content.Context;
import android.util.Log;

import net.wvffle.android.pb.schedule.models.MyObjectBox;

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

    public static BoxStore get() {
        return boxStore;
    }
}
