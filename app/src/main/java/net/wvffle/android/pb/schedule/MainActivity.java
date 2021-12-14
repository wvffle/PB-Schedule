package net.wvffle.android.pb.schedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.ListenableWorker;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.util.Log;

import net.wvffle.android.pb.schedule.api.ApiWorker;
import net.wvffle.android.pb.schedule.api.BackendApi;
import net.wvffle.android.pb.schedule.api.db.ObjectBox;
import net.wvffle.android.pb.schedule.api.db.models.Room;

import io.objectbox.Box;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WorkManager.getInstance(this).enqueue(ApiWorker.create(() -> {
            Room room = BackendApi.getRoom("00d1c9c8a3668121c9fc4d89b848b17e73a61073");
            Log.d("Backend", String.valueOf(room.id));
            Log.d("Backend", room.getHash());
            Log.d("Backend", room.getName());

            Box<Room> box = ObjectBox.get().boxFor(Room.class);
            box.put(room);

            Log.d("ObjectBox", String.valueOf(room.id));
            Log.d("ObjectBox", room.getHash());
            Log.d("ObjectBox", room.getName());

            return ListenableWorker.Result.success();
        }));
    }
}