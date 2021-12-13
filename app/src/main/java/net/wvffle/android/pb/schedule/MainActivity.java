package net.wvffle.android.pb.schedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.ListenableWorker;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.util.Log;

import net.wvffle.android.pb.schedule.api.ApiWorker;
import net.wvffle.android.pb.schedule.api.BackendApi;
import net.wvffle.android.pb.schedule.api.syncedcollectionentry.entries.Room;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WorkManager.getInstance(this).enqueue(ApiWorker.create(() -> {
            Room room = BackendApi.getRoom("00d1c9c8a3668121c9fc4d89b848b17e73a61073");
            Log.d("Backend", room.getHash());
            Log.d("Backend", room.getName());
            return ListenableWorker.Result.success();
        }));
    }
}