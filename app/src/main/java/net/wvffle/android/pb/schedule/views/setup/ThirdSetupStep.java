package net.wvffle.android.pb.schedule.views.setup;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.recyclerview.widget.LinearLayoutManager;

import net.wvffle.android.pb.schedule.ObjectBox;
import net.wvffle.android.pb.schedule.R;
import net.wvffle.android.pb.schedule.api.enums.ClassType;
import net.wvffle.android.pb.schedule.api.setup.GroupPair;
import net.wvffle.android.pb.schedule.databinding.FragmentThirdSetupStepViewBinding;
import net.wvffle.android.pb.schedule.models.Degree;
import net.wvffle.android.pb.schedule.models.Schedule_;
import net.wvffle.android.pb.schedule.util.GenericRecyclerViewAdapter;
import net.wvffle.android.pb.schedule.util.Serializer;
import net.wvffle.android.pb.schedule.viewmodels.SetupViewModel;
import net.wvffle.android.pb.schedule.views.BaseView;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import io.sentry.Sentry;

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
            List<GroupPair> groups = viewModel.getGroups().getValue();
            assert groups != null;

            Integer semester = viewModel.getSemester().getValue();
            assert semester != null;

            Degree degree = viewModel.getDegree().getValue();
            assert degree != null;

            List<Long> selectedIds = ObjectBox.getScheduleBox()
                    .query(
                            Schedule_.semester.equal(semester)
                                    .and(Schedule_.degreeId.equal(degree.id))
                    )
                    .build()
                    .find()
                    .stream()
                    .filter(schedule -> {
                        if (schedule.getType() == ClassType.W) {
                            return true;
                        }

                        for (GroupPair groupPair : groups) {
                            if (groupPair.isSelected() && schedule.getGroup() == groupPair.getGroupNumber() && schedule.getType() == groupPair.getType()) {
                                return true;
                            }
                        }

                        return false;
                    })
                    .map(schedule -> schedule.id)
                    .collect(Collectors.toList());

            viewModel.setSelectedIds(selectedIds);

            SharedPreferences pref = requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
            try {
                pref.edit()
                        .putBoolean("setup-done", true)
                        .putString("setup-data", Serializer.getInstance().toString(viewModel.buildSetupData()))
                        .apply();
            } catch (IOException e) {
                e.printStackTrace();
                Sentry.captureException(e);
            }

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
        });
    }
}