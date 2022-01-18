package net.wvffle.android.pb.schedule.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mancj.materialsearchbar.MaterialSearchBar;

import net.wvffle.android.pb.schedule.MainActivity;
import net.wvffle.android.pb.schedule.ObjectBox;
import net.wvffle.android.pb.schedule.R;
import net.wvffle.android.pb.schedule.api.setup.SetupData;
import net.wvffle.android.pb.schedule.api.setup.SetupDataBuilder;
import net.wvffle.android.pb.schedule.databinding.AdapterItemExtraSubjectBinding;
import net.wvffle.android.pb.schedule.databinding.FragmentExtraSubjectsViewBinding;
import net.wvffle.android.pb.schedule.models.Schedule;
import net.wvffle.android.pb.schedule.models.Schedule_;
import net.wvffle.android.pb.schedule.util.GenericRecyclerViewAdapter;
import net.wvffle.android.pb.schedule.util.GenericSuggestionAdapter;
import net.wvffle.android.pb.schedule.util.Serializer;
import net.wvffle.android.pb.schedule.viewmodels.ExtraSubjectsViewModel;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import io.sentry.Sentry;

public class ExtraSubjectsView extends BaseViewWithVM<FragmentExtraSubjectsViewBinding, ExtraSubjectsViewModel> implements MaterialSearchBar.OnSearchActionListener {
    private MaterialSearchBar searchBar;

    @Override
    void setup(FragmentExtraSubjectsViewBinding binding, ExtraSubjectsViewModel vm) {
        MainActivity.getInstance()
                .getSupportActionBar()
                .setTitle(R.string.extra_subjects);

        SetupData setupData = MainActivity.getInstance().getSetupData();

        Map<Long, Boolean> initiallySelected = new HashMap<>();
        for (Long id : setupData.getSelectedSchedules()) {
            initiallySelected.put(id, true);
        }

        vm.setSelected(initiallySelected);

        List<Schedule> classes = ObjectBox.getScheduleBox()
                .query(
                        Schedule_.degreeId.equal(setupData.getDegree().id)
                                .and(Schedule_.subjectId.notEqual(0))
                )
                .build()
                .find();

        vm.setClasses(classes);
        vm.setFilter("");

        GenericRecyclerViewAdapter<Schedule> itemsAdapter = new GenericRecyclerViewAdapter<Schedule>(vm, R.layout.adapter_item_extra_subject) {
            @Override
            public void onBindViewHolder(@NonNull GenericViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);

                AdapterItemExtraSubjectBinding binding = (AdapterItemExtraSubjectBinding) holder.getBinding();
                binding.ripple.setOnClickListener(view -> binding.checkBox.setChecked(!binding.checkBox.isChecked()));
            }
        };
        itemsAdapter.setData(vm.getFilteredClasses().getValue());
        vm.getFilteredClasses().observe(this, itemsAdapter::setData);

        SharedPreferences pref = requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        SetupDataBuilder setupDataBuilder = SetupDataBuilder.from(MainActivity.getInstance().getSetupData());
        vm.getSelected().observe(this, selected -> {
            List<Long> selectedIds = selected.entrySet()
                    .stream()
                    .filter(Map.Entry::getValue)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            setupDataBuilder.setSelectedSchedules(selectedIds);
            try {
                pref.edit()
                        .putString("setup-data", Serializer.getInstance().toString(setupDataBuilder.build()))
                        .apply();
            } catch (IOException e) {
                e.printStackTrace();
                Sentry.captureException(e);
            }
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setAdapter(itemsAdapter);

        searchBar = binding.searchBar;
        binding.searchBar.setOnSearchActionListener(this);
        searchBar.setCardViewElevation(10);
        searchBar.setElevation(10);

        GenericSuggestionAdapter<Schedule> adapter = new GenericSuggestionAdapter<Schedule>(getLayoutInflater(), BR.schedule, R.layout.suggestion_item_subject) {
            @Override
            public Predicate<Schedule> getPredicate(String term) {
                Log.d("Predicate", term);
                return schedule -> schedule.getSubject().getName().toLowerCase().contains(term);
            }

            @Override
            public int getSingleViewHeight() {
                return 30;
            }
        };

        // TODO [$61e6da68500d31062a77961d]: Add clickable suggestions
//        searchBar.setCustomSuggestionAdapter(adapter);
        adapter.setSuggestions(classes);

        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                vm.setFilter(s.toString());
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_extra_subjects_view;
    }

    @Override
    Class<ExtraSubjectsViewModel> getViewModelClass() {
        return ExtraSubjectsViewModel.class;
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {

    }

    @Override
    public void onButtonClicked(int buttonCode) {
    }
}