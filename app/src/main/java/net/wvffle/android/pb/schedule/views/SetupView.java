package net.wvffle.android.pb.schedule.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import net.wvffle.android.pb.schedule.R;
import net.wvffle.android.pb.schedule.databinding.FragmentSetupViewBinding;
import net.wvffle.android.pb.schedule.viewmodels.SetupViewModel;
import net.wvffle.android.pb.schedule.views.setup.FirstSetupScreen;
import net.wvffle.android.pb.schedule.views.setup.SetupAdapter;

import java.util.ArrayList;

public class SetupView extends BaseViewWithVM<FragmentSetupViewBinding, SetupViewModel> {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentSetupViewBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setup_view, container, false);
        SetupViewModel viewModel = new ViewModelProvider(this).get(SetupViewModel.class);
        setup(binding, viewModel);

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new FirstSetupScreen());
        fragments.add(new FirstSetupScreen());

        binding.viewPager.setAdapter(new SetupAdapter(
                fragments,
                requireActivity().getSupportFragmentManager(),
                getLifecycle()
        ));

        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                viewModel.setButtonName(
                        binding.viewPager.getCurrentItem() == fragments.size() - 1
                                // TODO: Migrate to string ids
                                ? "Finish"
                                : "Next"
                );

                super.onPageSelected(position);
            }
        });

        SharedPreferences pref = requireActivity().getSharedPreferences("setup", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        binding.button.setOnClickListener(v -> {

            // TODO: Write step data to editor
            if (binding.viewPager.getCurrentItem() == fragments.size() - 1) {
                editor.putBoolean("setup-done", true);
                editor.apply();
                navigate(R.id.action_setupView_to_homeView);
                return;
            }

            binding.viewPager.setCurrentItem(binding.viewPager.getCurrentItem() + 1);
        });

        return binding.getRoot();
    }
}