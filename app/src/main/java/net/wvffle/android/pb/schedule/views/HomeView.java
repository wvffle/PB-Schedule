package net.wvffle.android.pb.schedule.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import net.wvffle.android.pb.schedule.MainActivity;
import net.wvffle.android.pb.schedule.ObjectBox;
import net.wvffle.android.pb.schedule.R;
import net.wvffle.android.pb.schedule.api.calendar.EmptyDayDecorator;
import net.wvffle.android.pb.schedule.api.setup.GroupPair;
import net.wvffle.android.pb.schedule.api.setup.SetupData;
import net.wvffle.android.pb.schedule.databinding.FragmentHomeViewBinding;
import net.wvffle.android.pb.schedule.models.Schedule;
import net.wvffle.android.pb.schedule.models.Schedule_;
import net.wvffle.android.pb.schedule.util.GenericGroupedRecyclerViewAdapter;
import net.wvffle.android.pb.schedule.util.GroupedItem;
import net.wvffle.android.pb.schedule.util.Serializer;
import net.wvffle.android.pb.schedule.viewmodels.HomeViewModel;

import org.threeten.bp.DayOfWeek;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import io.sentry.Sentry;

public class HomeView extends BaseViewWithVM<FragmentHomeViewBinding, HomeViewModel> {
    @Override
    void setup(FragmentHomeViewBinding binding, HomeViewModel vm) {
        SharedPreferences pref = requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        SetupData setupData = null;
        try {
            String savedData = pref.getString("setup-data", "");
            setupData = (SetupData) Serializer.getInstance().fromString(savedData);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            Sentry.captureException(e);
        } catch (IncompatibleClassChangeError e) {
            e.printStackTrace();
        }

        if (setupData == null) {
            pref.edit().putBoolean("setup-done", false).apply();
            navigate(R.id.action_homeView_to_setupView);
            return;
        }

        MainActivity.getInstance()
                .getSupportActionBar()
                .show();

//        binding.calendarView.setTopbarVisible(false);
        binding.calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_NONE);

        CalendarDay today = CalendarDay.today();
        binding.calendarView.setSelectedDate(today);
        binding.calendarView.state()
                .edit()
                .setFirstDayOfWeek(DayOfWeek.MONDAY)
                .setCalendarDisplayMode(CalendarMode.WEEKS)
                .setMinimumDate(today)
                .commit();

        List<GroupPair> groups = setupData.getGroups();
        List<Schedule> classes = ObjectBox.getScheduleBox()
                .query(
                        Schedule_.semester.equal(setupData.getSemester())
                                .and(Schedule_.degreeId.equal(setupData.getDegree().id))
                )
                .build()
                .find()
                .stream()
                .filter(schedule -> {
                    for (GroupPair group : groups) {
                        if (group.getGroupNumber() == schedule.getGroup() && group.getType() == schedule.getType()) {
                            return true;
                        }
                    }

                    return false;
                })
                .collect(Collectors.toList());

        Collections.sort(classes, Comparator.comparing(Schedule::getDay).thenComparing(Schedule::getHour));

        vm.setClasses(classes);
        binding.calendarView.addDecorators(
                new EmptyDayDecorator(classes)
        );

        vm.setDate(CalendarDay.today().getDate());
        binding.calendarView.setOnMonthChangedListener((view, calendarDay) -> {
            vm.setDate(calendarDay.getDate());
        });

        GenericGroupedRecyclerViewAdapter<Schedule> adapter = new GenericGroupedRecyclerViewAdapter<>(vm, R.layout.adapter_item_home_group, R.layout.adapter_item_home_item);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setAdapter(adapter);


        vm.getUpcomingClasses().observe(this, upcomingClasses -> {
            adapter.setData(upcomingClasses);
            for (GroupedItem<Schedule> item : upcomingClasses) {
                if (item.isGroup()) {
                    Log.d("group:", item.getGroup());
                } else {
                    Log.d("upcoming", item.getItem().getSubject().getName());
                }
            }
        });


//        navigate(R.id.action_homeView_to_dayView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_view;
    }

    @Override
    Class<HomeViewModel> getViewModelClass() {
        return HomeViewModel.class;
    }
}