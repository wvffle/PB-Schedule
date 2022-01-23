package net.wvffle.android.pb.schedule.api;

import android.util.Log;

import androidx.work.ListenableWorker;
import androidx.work.WorkManager;

import net.wvffle.android.pb.schedule.MainActivity;
import net.wvffle.android.pb.schedule.ObjectBox;
import net.wvffle.android.pb.schedule.api.update.UpdateData;
import net.wvffle.android.pb.schedule.models.Update;
import net.wvffle.android.pb.schedule.models.Update_;

import java.util.Objects;

import io.objectbox.query.QueryBuilder;

public class DatabaseSyncService {
    private static final String TAG = "DatabaseSyncService";

    /**
     * Returns last {@link Update} from the local database
     *
     * @return last {@link Update}
     */
    public static Update getLastUpdate() {
        return ObjectBox.getUpdateBox()
                .query()
                .order(Update_.id, QueryBuilder.DESCENDING)
                .build()
                .findFirst();
    }

    /**
     * Synchronizes database
     *
     * @param then {@link SyncResult} called after synchronization is done
     */
    public static void sync(SyncResult then) {
        // TODO: Handle update failure
        WorkManager.getInstance(MainActivity.getInstance()).enqueue(Worker.create(() -> {
            Update lastUpdate = getLastUpdate();

            String newHash = Objects.requireNonNull(BackendApi.getService().getUpdates().execute().body())
                    .get(0).getHash();

            if (lastUpdate == null || !lastUpdate.getHash().equals(newHash)) {
                Log.i(TAG, "Found new update, syncing.");
                Update update = Objects.requireNonNull(BackendApi.getService().getUpdate(newHash).execute().body());
                UpdateData data = update.getData();
                ObjectBox.getRoomBox().put(data.getRooms());
                ObjectBox.getDegreeBox().put(data.getDegrees());
                ObjectBox.getSpecialityBox().put(data.getSpecialities());
                ObjectBox.getSubjectBox().put(data.getSubjects());
                ObjectBox.getTitleBox().put(data.getTitles());
                ObjectBox.getTeacherBox().put(data.getTeachers());
                ObjectBox.getScheduleBox().put(data.getSchedules());
                ObjectBox.getUpdateBox().put(update);
                then.execute(update, true);
            } else {
                Log.i(TAG, "No new updates.");
                then.execute(lastUpdate, false);
            }

            return ListenableWorker.Result.success();
        }));
    }

    public interface SyncResult {
        void execute(Update update, boolean isNew);
    }
}
