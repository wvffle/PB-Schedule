package net.wvffle.android.pb.schedule.api.enums;

import android.content.Context;

import androidx.annotation.ColorInt;

import net.wvffle.android.pb.schedule.MainActivity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public enum ClassType implements Serializable {
    WF("pe", 0xffaaff00),
    S("seminar", 0xff00ffaa),
    J("language", 0xff00aaff),
    P("project", 0xff00aaff),
    L("laboratories", 0xffff0066),
    CW("exercises", 0xffffee00),
    PS("special", 0xff00aaff),
    W("lecture", 0xff0077ff),
    UNKNOWN("unknown", 0xff000000);

    private final String fullName;
    private final int color;
    private static final Map<String, ClassType> BY_NAME = new HashMap<>();

    static {
        for (ClassType type : values()) {
            BY_NAME.put(type.fullName, type);
            BY_NAME.put(type.getFullName(), type);
            BY_NAME.put(type.name(), type);
        }
    }

    ClassType(String fullName, @ColorInt int color) {
        this.fullName = fullName;
        this.color = color;
    }

    /**
     * Return a {@link ClassType} for a given name
     *
     * @param name String name
     * @return {@link ClassType} enum value
     */
    public static ClassType valueOfName(String name) {
        // NOTE: support for exercises
        name = name.replace('Ć', 'c');
        ClassType classType = BY_NAME.getOrDefault(name, valueOf(name.toUpperCase()));
        return classType == null ? UNKNOWN : classType;
    }

    /**
     * Return type name
     *
     * @return String name
     */
    public String getFullName() {
        Context context = MainActivity.getInstance();

        // NOTE: Support for tests
        if (context == null) {
            return fullName;
        }

        return context.getString(context.getResources().getIdentifier("class_type__" + fullName, "string", context.getPackageName()));
    }

    /**
     * Return color string
     *
     * @return Color as int
     */
    public int getColor() {
        return color;
    }
}
