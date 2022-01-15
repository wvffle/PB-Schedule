package net.wvffle.android.pb.schedule.api.setup;

import androidx.annotation.NonNull;

import net.wvffle.android.pb.schedule.api.enums.ClassType;
import net.wvffle.android.pb.schedule.util.IntegersUtil;
import net.wvffle.android.pb.schedule.util.StringsUtil;

import java.io.Serializable;
import java.util.Objects;

public class GroupPair implements Comparable<GroupPair>, Serializable {
    public int groupNumber;
    public ClassType type;
    private boolean selected = false;

    public GroupPair(int groupNumber, ClassType type) {
        this.groupNumber = groupNumber;
        this.type = type;
    }

    public static GroupPair fromString(String string) {
        int group = IntegersUtil.getIntFromEnd(string);
        return new GroupPair(
                group,
                ClassType.valueOfName(
                        string.substring(0, string.length() - String.valueOf(group).length()).trim()
                )
        );
    }

    @NonNull
    public String toString() {
        return type.getFullName() + " " + groupNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupPair groupPair = (GroupPair) o;
        return groupNumber == groupPair.groupNumber && type == groupPair.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupNumber, type);
    }

    @Override
    public int compareTo(GroupPair o) {
        return StringsUtil.naturalCompareTo(toString(), o.toString());
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public ClassType getType() {
        return type;
    }
}
