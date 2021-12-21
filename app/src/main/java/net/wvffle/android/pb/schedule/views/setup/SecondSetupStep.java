package net.wvffle.android.pb.schedule.views.setup;

import static net.wvffle.android.pb.schedule.util.IntegersUtil.getIntFromEnd;

import androidx.recyclerview.widget.LinearLayoutManager;

import net.wvffle.android.pb.schedule.ObjectBox;
import net.wvffle.android.pb.schedule.R;
import net.wvffle.android.pb.schedule.databinding.FragmentSecondSetupStepViewBinding;
import net.wvffle.android.pb.schedule.models.Schedule;
import net.wvffle.android.pb.schedule.models.Schedule_;
import net.wvffle.android.pb.schedule.util.GenericRecyclerViewAdapter;
import net.wvffle.android.pb.schedule.viewmodels.SetupViewModel;
import net.wvffle.android.pb.schedule.views.BaseView;

import java.util.List;
import java.util.stream.Collectors;

public class SecondSetupStep extends BaseView<FragmentSecondSetupStepViewBinding> {
    private final SetupViewModel viewModel;

    public SecondSetupStep(SetupViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_second_setup_step_view;
    }

    @Override
    protected void setup(FragmentSecondSetupStepViewBinding binding) {
        binding.setViewModel(viewModel);

        GenericRecyclerViewAdapter<String> adapter = new GenericRecyclerViewAdapter<>(viewModel, R.layout.adapter_item_semester);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.recyclerView.setAdapter(adapter);

        viewModel.getDegree().observe(this, degree -> {
            if (degree == null) return;

            List<String> semesters = ObjectBox.getScheduleBox()
                    .query()
                    .equal(Schedule_.degreeId, degree.id)
                    .build()
                    .find()
                    .stream()
                    .map(Schedule::getSemester)
                    .distinct()
                    .sorted()
                    // TODO [$61c1d4cc217ade069c0b63ee]: Get string from string id
                    .map(semester -> "Semester " + semester)
                    .collect(Collectors.toList());

            viewModel.setSemesters(semesters);
            adapter.setData(semesters);
            adapter.setOnItemClickListener((view, position) -> {
                viewModel.setSemester(getIntFromEnd(semesters.get(position)));
                viewModel.setMaxStep(3);
            });
        });
    }
}