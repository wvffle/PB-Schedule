package net.wvffle.android.pb.schedule.utils;

import android.graphics.RectF;

public class TouchEventUtils {
    public static boolean contains(float left, float top, float right, float bottom, float x, float y) {
        return left < right && top < bottom  // check for empty first
                && x >= left && x < right && y >= top && y < bottom;
    }

    public static boolean contains(RectF rect, float x, float y) {
        return contains(rect.left, rect.top, rect.right, rect.bottom, x, y);
    }
}