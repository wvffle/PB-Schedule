package net.wvffle.android.pb.schedule.services;

import android.app.Notification;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import net.wvffle.android.pb.schedule.MainActivity;
import net.wvffle.android.pb.schedule.ObjectBox;
import net.wvffle.android.pb.schedule.R;
import net.wvffle.android.pb.schedule.api.DatabaseSyncService;
import net.wvffle.android.pb.schedule.api.setup.SetupData;
import net.wvffle.android.pb.schedule.models.Update;
import net.wvffle.android.pb.schedule.models.Update_;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ScheduleUpdateService extends FirebaseMessagingService {
    private final String CHANNEL_ID = "waff-pb";

    @Override
    public void onNewToken(@NonNull String s) {
        Log.d("TOKEN", s);
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Map<String, String> data = remoteMessage.getData();
        String type = data.getOrDefault("type", "unknown");
        Log.d("NOTIFICATION", type);

        if (Objects.requireNonNull(type).equals("update")) {
            Update lastUpdate = DatabaseSyncService.getLastUpdate();

            SetupData setupData = MainActivity.getSetupData(this);
            assert setupData != null;

            DatabaseSyncService.sync((syncedUpdate, isNew) -> {
                List<Update> newUpdates = ObjectBox.getUpdateBox()
                        .query()
                        .greater(Update_.id, lastUpdate.id)
                        .build()
                        .find();

                boolean showNotification = newUpdates.stream().anyMatch(
                        update -> update.getDiff()
                                .getRemovedModels()
                                .values()
                                .stream()
                                .anyMatch(models -> models.stream().anyMatch(
                                        model -> setupData.getSelectedSchedules()
                                                .contains(model.getId())
                                ))
                );

                if (!showNotification) {
                    return;
                }

                Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_schedule_logo)
                        .setContentTitle(getString(R.string.notification_update_title))
                        .setContentText(getString(R.string.notification_update_text))
                        // TODO [$61ed68affeab4e067ff05efd]: Go to diff on notification tap
                        // .setContentIntent()
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .build();

                NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
                manager.notify(Math.toIntExact(syncedUpdate.getId()), notification);
            });

            return;
        }

        super.onMessageReceived(remoteMessage);
    }
}
