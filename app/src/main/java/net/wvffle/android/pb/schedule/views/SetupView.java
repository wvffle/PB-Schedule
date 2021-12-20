package net.wvffle.android.pb.schedule.views;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.Fragment;

import net.wvffle.android.pb.schedule.R;
import net.wvffle.android.pb.schedule.databinding.FragmentSetupViewBinding;
import net.wvffle.android.pb.schedule.viewmodels.SetupViewModel;
import net.wvffle.android.pb.schedule.views.setup.FirstSetupStep;
import net.wvffle.android.pb.schedule.views.setup.SetupAdapter;

import java.util.ArrayList;

public class SetupView extends BaseViewWithVM<FragmentSetupViewBinding, SetupViewModel> {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setup_view;
    }

    @Override
    Class<SetupViewModel> getViewModelClass() {
        return SetupViewModel.class;
    }

    @Override
    void setup(FragmentSetupViewBinding binding, SetupViewModel viewModel) {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new FirstSetupStep(viewModel));
        fragments.add(new FirstSetupStep(viewModel));

        binding.viewPager.setAdapter(new SetupAdapter(
                this,
                fragments,
                viewModel,
                requireActivity().getSupportFragmentManager(),
                getLifecycle()
        ));

        SharedPreferences pref = requireActivity().getSharedPreferences("setup", Context.MODE_PRIVATE);
        viewModel.getMaxSteps().observe(this, binding.viewPager::setCurrentItem);

        SharedPreferences.Editor editor = pref.edit();
//        binding.button.setOnClickListener(v -> {
//
//            // TODO [#37]: Write step data to editor
//            if (binding.viewPager.getCurrentItem() == fragments.size() - 1) {
//                editor.putBoolean("setup-done", true);
//                editor.apply();
//                navigate(R.id.action_setupView_to_homeView);
//                return;
//            }
//
//            binding.viewPager.setCurrentItem(binding.viewPager.getCurrentItem() + 1);
//        });
    }
}