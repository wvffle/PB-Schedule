package net.wvffle.android.pb.schedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.ListenableWorker;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.util.Log;

import net.wvffle.android.pb.schedule.api.ApiWorker;
import net.wvffle.android.pb.schedule.api.BackendApi;
import net.wvffle.android.pb.schedule.api.update.UpdateData;
import net.wvffle.android.pb.schedule.api.update.UpdateEntry;
import net.wvffle.android.pb.schedule.models.Room;
import net.wvffle.android.pb.schedule.models.Update;

import java.util.List;

import io.objectbox.Box;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WorkManager.getInstance(this).enqueue(ApiWorker.create(() -> {
            Update update = BackendApi.getUpdate(BackendApi.getUpdates().get(0).getHash());
            UpdateData data = update.getData();

            ObjectBox.getRoomBox().put(data.getRooms());
            ObjectBox.getDegreeBox().put(data.getDegrees());
            ObjectBox.getSpecialityBox().put(data.getSpecialities());
            ObjectBox.getSubjectBox().put(data.getSubjects());
            ObjectBox.getTitleBox().put(data.getTitles());
            ObjectBox.getTeacherBox().put(data.getTeachers());
            ObjectBox.getScheduleBox().put(data.getSchedules());
            ObjectBox.getUpdateBox().put(update);

            return ListenableWorker.Result.success();
        }));
    }
}