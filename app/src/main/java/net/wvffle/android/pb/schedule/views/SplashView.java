package net.wvffle.android.pb.schedule.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import net.wvffle.android.pb.schedule.R;
import net.wvffle.android.pb.schedule.databinding.FragmentSplashViewBinding;

public class SplashView extends BaseView<FragmentSplashViewBinding> {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentSplashViewBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash_view, container, false);
        setup(binding);

        SharedPreferences pref = requireActivity().getSharedPreferences("setup", Context.MODE_PRIVATE);
        new Handler().postDelayed(() -> {
            navigate(
                    pref.getBoolean("setup-done", false)
                            ? R.id.action_splashView_to_homeView
                            : R.id.action_splashView_to_setupView
            );
        }, 5000);

        return binding.getRoot();
    }
}