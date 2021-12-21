package net.wvffle.android.pb.schedule.views.setup;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import net.wvffle.android.pb.schedule.viewmodels.SetupViewModel;
import net.wvffle.android.pb.schedule.views.SetupView;

import java.util.List;

public class SetupAdapter extends FragmentStateAdapter {
    private final List<Fragment> fragments;
    private Integer maxSteps = 1;

    @SuppressLint("NotifyDataSetChanged")
    public SetupAdapter(SetupView setupView, List<Fragment> fragments, SetupViewModel viewModel, @NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        this.fragments = fragments;

        viewModel.getMaxSteps().observe(setupView, integer -> {
            maxSteps = integer;
            notifyDataSetChanged();
        });
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return Math.min(maxSteps, fragments.size());
    }
}
