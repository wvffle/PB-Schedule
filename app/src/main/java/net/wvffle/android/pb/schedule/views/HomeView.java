package net.wvffle.android.pb.schedule.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import net.wvffle.android.pb.schedule.R;
import net.wvffle.android.pb.schedule.databinding.FragmentHomeViewBinding;
import net.wvffle.android.pb.schedule.viewmodels.HomeViewModel;

public class HomeView extends BaseViewWithVM<FragmentHomeViewBinding, HomeViewModel> {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentHomeViewBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_view, container, false);
        HomeViewModel viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        setup(binding, viewModel);

        return binding.getRoot();
    }
}