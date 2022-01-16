package net.wvffle.android.pb.schedule.utils;

import java.util.Arrays;

public class ObjectUtils {
    public static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }

    public static int hash(Object... values) {
        return Arrays.hashCode(values);
    }
}
