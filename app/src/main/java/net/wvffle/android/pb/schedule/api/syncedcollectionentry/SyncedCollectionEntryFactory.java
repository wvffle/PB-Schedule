package net.wvffle.android.pb.schedule.api.syncedcollectionentry;

import com.google.gson.JsonObject;

import java.lang.reflect.InvocationTargetException;

public class SyncedCollectionEntryFactory {
    public static SyncedCollectionEntry createCollectionEntry (JsonObject json, SyncedCollectionEntryType type) {
        try {
            Class<? extends SyncedCollectionEntry> clazz = type.getSyncedCollectionEntryClass();
            return clazz.cast(clazz.getDeclaredMethod("fromJson", JsonObject.class).invoke(null, json));
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }
}
