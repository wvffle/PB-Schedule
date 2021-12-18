package net.wvffle.android.pb.schedule;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.ListenableWorker;
import androidx.work.WorkManager;

import net.wvffle.android.pb.schedule.api.ApiWorker;
import net.wvffle.android.pb.schedule.api.BackendApi;
import net.wvffle.android.pb.schedule.api.update.UpdateData;
import net.wvffle.android.pb.schedule.models.Update;
import net.wvffle.android.pb.schedule.models.Update_;

import io.objectbox.query.QueryBuilder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WorkManager.getInstance(this).enqueue(ApiWorker.create(() -> {
            Update lastUpdate = ObjectBox.getUpdateBox()
                    .query()
                    .order(Update_.id, QueryBuilder.DESCENDING)
                    .build()
                    .findFirst();

            String newHash = BackendApi.getUpdates().get(0).getHash();
            if (lastUpdate == null || !lastUpdate.getHash().equals(newHash)) {
                Log.i("Updater", "Found new update, fetching.");
                Update update = BackendApi.getUpdate(newHash);
                assert update != null;

                UpdateData data = update.getData();
                ObjectBox.getRoomBox().put(data.getRooms());
                ObjectBox.getDegreeBox().put(data.getDegrees());
                ObjectBox.getSpecialityBox().put(data.getSpecialities());
                ObjectBox.getSubjectBox().put(data.getSubjects());
                ObjectBox.getTitleBox().put(data.getTitles());
                ObjectBox.getTeacherBox().put(data.getTeachers());
                ObjectBox.getScheduleBox().put(data.getSchedules());
                ObjectBox.getUpdateBox().put(update);
            } else {
                Log.i("Updater", "No new updates.");
            }

            return ListenableWorker.Result.success();
        }));
    }
}