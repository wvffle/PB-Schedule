package net.wvffle.android.pb.schedule.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.wvffle.android.pb.schedule.R;
import net.wvffle.android.pb.schedule.api.update.UpdateEntry;
import net.wvffle.android.pb.schedule.databinding.UpdateListViewBinding;
import net.wvffle.android.pb.schedule.util.GenericRecyclerViewAdapter;
import net.wvffle.android.pb.schedule.viewmodels.UpdateListViewModel;

public class UpdateListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UpdateListViewBinding binding = DataBindingUtil.setContentView(this, R.layout.update_list_view);
        UpdateListViewModel viewModel = new ViewModelProvider(this).get(UpdateListViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        RecyclerView recyclerView = binding.recyclerView;
        GenericRecyclerViewAdapter<UpdateEntry> adapter = new GenericRecyclerViewAdapter<>(viewModel, R.layout.adapter_item_update_entry);
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