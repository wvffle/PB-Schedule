package net.wvffle.android.pb.schedule.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

import net.wvffle.android.pb.schedule.R;
import net.wvffle.android.pb.schedule.databinding.FragmentSplashViewBinding;

public class SplashView extends BaseView<FragmentSplashViewBinding> {
    @Override
    int getLayoutId() {
        return R.layout.fragment_splash_view;
    }

    @Override
    void setup(FragmentSplashViewBinding binding) {
        SharedPreferences pref = requireActivity().getSharedPreferences("setup", Context.MODE_PRIVATE);
        new Handler().postDelayed(() -> {
            navigate(
                    pref.getBoolean("setup-done", false)
                            ? R.id.action_splashView_to_homeView
                            : R.id.action_splashView_to_setupView
            );
        }, 5000);
    }
}