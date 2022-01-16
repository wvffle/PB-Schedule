package net.wvffle.android.pb.schedule.utils;

import android.content.Context;
import android.util.TypedValue;

import androidx.annotation.DimenRes;

public class ResourceUtils {

    public static float getFloatDimension(Context context, @DimenRes int id) {
        TypedValue value = new TypedValue();
        context.getResources().getValue(id, value, true);
        return value.getFloat();
    }
}
