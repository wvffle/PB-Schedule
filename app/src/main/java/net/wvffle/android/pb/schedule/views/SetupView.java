package net.wvffle.android.pb.schedule.views;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import net.wvffle.android.pb.schedule.MainActivity;
import net.wvffle.android.pb.schedule.R;
import net.wvffle.android.pb.schedule.databinding.FragmentSetupViewBinding;
import net.wvffle.android.pb.schedule.viewmodels.SetupViewModel;
import net.wvffle.android.pb.schedule.views.setup.FirstSetupStep;
import net.wvffle.android.pb.schedule.views.setup.SecondSetupStep;
import net.wvffle.android.pb.schedule.views.setup.SetupAdapter;
import net.wvffle.android.pb.schedule.views.setup.ThirdSetupStep;

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
        MainActivity.getInstance()
                .getSupportActionBar()
                .hide();

        MainActivity.getInstance()
                .getDrawer()
                .setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new FirstSetupStep(viewModel));
        fragments.add(new SecondSetupStep(viewModel));
        fragments.add(new ThirdSetupStep(viewModel));

        binding.viewPager.setAdapter(new SetupAdapter(
                this,
                fragments,
                viewModel,
                requireActivity().getSupportFragmentManager(),
                getLifecycle()
        ));

        viewModel.getMaxSteps().observe(this, binding.viewPager::setCurrentItem);
    }
}