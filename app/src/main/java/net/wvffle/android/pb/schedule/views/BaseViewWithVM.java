package net.wvffle.android.pb.schedule.views;

import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;

public class BaseViewWithVM<B extends ViewDataBinding, VM> extends BaseView<B> {
    private VM viewModel;

    protected void setup(B binding, VM viewModel) {
        setup(binding);

        this.viewModel = viewModel;
        binding.setVariable(BR.viewModel, viewModel);
    }
}
