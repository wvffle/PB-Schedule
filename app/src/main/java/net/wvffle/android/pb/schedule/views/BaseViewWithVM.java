package net.wvffle.android.pb.schedule.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public abstract class BaseViewWithVM<B extends ViewDataBinding, VM extends ViewModel> extends BaseView<B> {
    private VM viewModel;

    abstract Class<VM> getViewModelClass();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(getViewModelClass());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    void setup(B binding) {
        binding.setVariable(BR.viewModel, viewModel);
        setup(binding, viewModel);
    }

    abstract void setup(B binding, VM vm);
}
