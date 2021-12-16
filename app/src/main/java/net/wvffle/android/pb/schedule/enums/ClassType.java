package net.wvffle.android.pb.schedule.enums;

import java.util.HashMap;
import java.util.Map;

public enum ClassType {
    WF("pe"),
    S("seminar"),
    J("language"),
    P("project"),
    L("laboratories"),
    CW("exercises"),
    PS("special"),
    W("lecture");

    private final String fullName;
    private static final Map<String, ClassType> BY_NAME = new HashMap<>();

    static {
        for (ClassType type : values()) {
            BY_NAME.put(type.fullName, type);
            BY_NAME.put(type.name(), type);
        }
    }

    ClassType (String fullName) {
        this.fullName = fullName;
    }

    public static ClassType valueOfName (String name) {
        // NOTE: support for exercises
        name = name.replace('Ć', 'c');
        return BY_NAME.getOrDefault(name, valueOf(name.toUpperCase()));
    }
}
