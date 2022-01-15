package net.wvffle.android.pb.schedule.views;

import net.wvffle.android.pb.schedule.R;
import net.wvffle.android.pb.schedule.databinding.FragmentDayViewBinding;

public class DayView extends BaseView<FragmentDayViewBinding> {
    @Override
    public void setup(FragmentDayViewBinding binding) {
//        binding.timeLine.addEvent();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_day_view;
    }
}