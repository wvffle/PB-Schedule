package net.wvffle.android.pb.schedule.api.enums;

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
    W("lecture"),
    UNKNOWN(null);

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

    /**
     * Return a ClassType for a given name
     *
     * @param name String name
     * @return ClassType enum value
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
        // TODO [$61c1d4cc217ade069c0b63ed]: Get name from strings.xml
        return fullName;
    }
}
