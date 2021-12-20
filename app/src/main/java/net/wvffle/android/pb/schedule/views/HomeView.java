package net.wvffle.android.pb.schedule.views;

import net.wvffle.android.pb.schedule.R;
import net.wvffle.android.pb.schedule.databinding.FragmentHomeViewBinding;
import net.wvffle.android.pb.schedule.viewmodels.HomeViewModel;

public class HomeView extends BaseViewWithVM<FragmentHomeViewBinding, HomeViewModel> {
    @Override
    void setup(FragmentHomeViewBinding binding, HomeViewModel vm) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_view;
    }

    @Override
    Class<HomeViewModel> getViewModelClass() {
        return HomeViewModel.class;
    }
}