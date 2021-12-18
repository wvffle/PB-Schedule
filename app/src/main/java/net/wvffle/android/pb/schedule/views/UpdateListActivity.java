package net.wvffle.android.pb.schedule.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import net.wvffle.android.pb.schedule.BR;
import net.wvffle.android.pb.schedule.R;
import net.wvffle.android.pb.schedule.api.model.Model;
import net.wvffle.android.pb.schedule.api.update.UpdateEntry;
import net.wvffle.android.pb.schedule.databinding.ActivityUpdateListBinding;
import net.wvffle.android.pb.schedule.util.GenericRecyclerViewAdapter;
import net.wvffle.android.pb.schedule.viewmodels.UpdateListViewModel;

import java.util.ArrayList;
import java.util.List;

public class UpdateListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityUpdateListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_update_list);
        UpdateListViewModel viewModel = new ViewModelProvider(this).get(UpdateListViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        RecyclerView recyclerView = binding.recyclerView;
        GenericRecyclerViewAdapter<UpdateEntry> adapter = new GenericRecyclerViewAdapter<>(viewModel, R.layout.item_update_entry);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);

        binding.swipeRefresh.setOnRefreshListener(() -> {
            viewModel.loadUpdates(this);
        });

        viewModel.getUpdates().observe(this, adapter::setData);
        viewModel.isLoading().observe(this, binding.swipeRefresh::setRefreshing);

        viewModel.loadUpdates(this);
    }

}