package net.wvffle.android.pb.schedule.util;

public class GroupedItem<T> {
    private String groupName = null;
    private T item = null;

    private GroupedItem() {
    }

    public static <T> GroupedItem<T> createGroup(String name) {
        GroupedItem<T> groupedItem = new GroupedItem<>();
        groupedItem.groupName = name;
        return groupedItem;
    }

    public static <T> GroupedItem<T> createItem(T item) {
        GroupedItem<T> groupedItem = new GroupedItem<>();
        groupedItem.item = item;
        return groupedItem;
    }

    public boolean isGroup() {
        return groupName != null;
    }

    public String getGroup() {
        return groupName;
    }

    public T getItem() {
        return item;
    }
}
