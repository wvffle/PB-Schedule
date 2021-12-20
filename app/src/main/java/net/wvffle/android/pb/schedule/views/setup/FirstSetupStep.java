package net.wvffle.android.pb.schedule.views.setup;

import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;

import net.wvffle.android.pb.schedule.R;
import net.wvffle.android.pb.schedule.databinding.FragmentFirstSetupStepViewBinding;
import net.wvffle.android.pb.schedule.models.Degree;
import net.wvffle.android.pb.schedule.util.GenericRecyclerViewAdapter;
import net.wvffle.android.pb.schedule.viewmodels.SetupViewModel;
import net.wvffle.android.pb.schedule.views.BaseView;

import java.util.List;

public class FirstSetupStep extends BaseView<FragmentFirstSetupStepViewBinding> {
    private final SetupViewModel viewModel;

    public FirstSetupStep(SetupViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_first_setup_step_view;
    }

    @Override
    protected void setup(FragmentFirstSetupStepViewBinding binding) {
        binding.setViewModel(viewModel);

        GenericRecyclerViewAdapter<Degree> adapter = new GenericRecyclerViewAdapter<>(viewModel, R.layout.adapter_item_degree);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.recyclerView.setAdapter(adapter);

        viewModel.getUpdate().observe(this, update -> {
            List<Degree> degrees = update.getData().getDegrees();
            viewModel.setDegrees(degrees);
            adapter.setData(degrees);

            adapter.setOnItemClickListener((view, position) -> {
                Log.d("Adapter", "Click " + degrees.get(position).getName());
            });
        });
    }
}