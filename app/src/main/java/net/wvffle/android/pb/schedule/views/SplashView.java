package net.wvffle.android.pb.schedule.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

import net.wvffle.android.pb.schedule.MainActivity;
import net.wvffle.android.pb.schedule.R;
import net.wvffle.android.pb.schedule.api.DatabaseSyncService;
import net.wvffle.android.pb.schedule.databinding.FragmentSplashViewBinding;

public class SplashView extends BaseView<FragmentSplashViewBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_splash_view;
    }

    @Override
    protected void setup(FragmentSplashViewBinding binding) {
        SharedPreferences pref = requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        long then = System.currentTimeMillis();
        Handler handler = new Handler();

        if (MainActivity.getInstance().getSetupData() == null) {
            pref.edit().putBoolean("setup-done", false).apply();
        }

        // NOTE: Ensure we have the latest info in the database.
        DatabaseSyncService.sync((update, isNew) -> {
            long delta = System.currentTimeMillis() - then;

            boolean setupDone = pref.getBoolean("setup-done", false);
            if (update == null && !setupDone) {
                // TODO [$61f900b87a23e60664e43a70]: Add 'Try again later view'
                //       Maybe register an FCM channel listener and push a message when the server is available
                return;
            }

            handler.postDelayed(() -> navigate(setupDone
                    ? R.id.action_splashView_to_homeView
                    : R.id.action_splashView_to_setupView
            ), Math.max(1, 3000 - delta));
        });
    }
}