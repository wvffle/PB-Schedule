package net.wvffle.android.pb.schedule.views.setup;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.recyclerview.widget.LinearLayoutManager;

import net.wvffle.android.pb.schedule.ObjectBox;
import net.wvffle.android.pb.schedule.R;
import net.wvffle.android.pb.schedule.api.setup.GroupPair;
import net.wvffle.android.pb.schedule.databinding.FragmentThirdSetupStepViewBinding;
import net.wvffle.android.pb.schedule.models.Schedule_;
import net.wvffle.android.pb.schedule.util.GenericRecyclerViewAdapter;
import net.wvffle.android.pb.schedule.viewmodels.SetupViewModel;
import net.wvffle.android.pb.schedule.views.BaseView;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ThirdSetupStep extends BaseView<FragmentThirdSetupStepViewBinding> {
    private final SetupViewModel viewModel;

    public ThirdSetupStep(SetupViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_third_setup_step_view;
    }

    @Override
    protected void setup(FragmentThirdSetupStepViewBinding binding) {
        binding.setViewModel(viewModel);

        GenericRecyclerViewAdapter<GroupPair> adapter = new GenericRecyclerViewAdapter<>(viewModel, R.layout.adapter_item_group);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.recyclerView.setAdapter(adapter);

        binding.button.setOnClickListener(v -> {
            SharedPreferences pref = requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
            pref.edit()
                    // TODO: Save SetupData
                    .putBoolean("setup-done", true)
                    .putLong("degreeId", Objects.requireNonNull(viewModel.getDegree().getValue()).id)
                    .putInt("semester", Objects.requireNonNull(viewModel.getSemester().getValue()))
                    .putStringSet(
                            "groups",
                            Objects.requireNonNull(viewModel.getGroups().getValue())
                                    .stream()
                                    .map(GroupPair::toString)
                                    .collect(Collectors.toSet())
                    )
                    .apply();

            navigate(R.id.action_setupView_to_homeView);
        });

        viewModel.getSemester().observe(this, semester -> {
            if (semester == -1) return;

            List<GroupPair> groups = ObjectBox.getScheduleBox()
                    .query()
                    .equal(
                            Schedule_.degreeId,
                            Objects.requireNonNull(viewModel.getDegree().getValue()).id
                    )
                    .and()
                    .equal(Schedule_.semester, semester)
                    .build()
                    .find()
                    .stream()
                    .map(schedule -> new GroupPair(schedule.getGroup(), schedule.getType()))
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList());

            viewModel.setGroups(groups);
            adapter.setData(groups);

            viewModel.getGroups().observe(this, groupPairs -> {
            });
        });
    }
}