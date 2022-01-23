package net.wvffle.android.pb.schedule.views;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import net.wvffle.android.pb.schedule.MainActivity;
import net.wvffle.android.pb.schedule.ObjectBox;
import net.wvffle.android.pb.schedule.R;
import net.wvffle.android.pb.schedule.api.calendar.EmptyDayDecorator;
import net.wvffle.android.pb.schedule.api.enums.ClassType;
import net.wvffle.android.pb.schedule.api.setup.GroupPair;
import net.wvffle.android.pb.schedule.api.setup.SetupData;
import net.wvffle.android.pb.schedule.databinding.FragmentHomeViewBinding;
import net.wvffle.android.pb.schedule.models.Schedule;
import net.wvffle.android.pb.schedule.models.Schedule_;
import net.wvffle.android.pb.schedule.util.GenericGroupedRecyclerViewAdapter;
import net.wvffle.android.pb.schedule.viewmodels.HomeViewModel;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDateTime;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HomeView extends BaseViewWithVM<FragmentHomeViewBinding, HomeViewModel> {
    @Override
    void setup(FragmentHomeViewBinding binding, HomeViewModel vm) {
        MainActivity.getInstance()
                .getSupportActionBar()
                .show();

        MainActivity.getInstance()
                .getSupportActionBar()
                .setTitle(R.string.home);

        MainActivity.getInstance()
                .getDrawer()
                .setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED);

        binding.calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_NONE);

        CalendarDay today = CalendarDay.today();
        binding.calendarView.setSelectedDate(today);
        binding.calendarView.state()
                .edit()
                .setFirstDayOfWeek(DayOfWeek.MONDAY)
                .setCalendarDisplayMode(CalendarMode.WEEKS)
                .setMinimumDate(today)
                .commit();

        SetupData setupData = MainActivity.getInstance().getSetupData();
        List<GroupPair> groups = setupData.getGroups();
        long[] scheduleIds = setupData.getSelectedSchedules().stream().mapToLong(l -> l).toArray();
        List<Schedule> classes = ObjectBox.getScheduleBox()
                .query()
                .in(Schedule_.id, scheduleIds)
                .build()
                .find()
                .stream()
                .filter(schedule -> {
                    if (schedule.getType() == ClassType.W) {
                        return true;
                    }

                    for (GroupPair group : groups) {
                        if (group.getGroupNumber() == schedule.getGroup() && group.getType() == schedule.getType()) {
                            return true;
                        }
                    }

                    return false;
                })
                .sorted(
                        Comparator.comparing(Schedule::getDay)
                                .thenComparing(Schedule::getHour)
                )
                .collect(Collectors.toList());

        vm.setClasses(classes);
        binding.calendarView.addDecorators(
                new EmptyDayDecorator(classes)
        );

        vm.setDateTime(LocalDateTime.now());
        binding.calendarView.setOnMonthChangedListener((view, calendarDay) -> vm.setDateTime(calendarDay.getDate().atStartOfDay()));

        GenericGroupedRecyclerViewAdapter<Schedule> adapter = new GenericGroupedRecyclerViewAdapter<>(vm, R.layout.adapter_item_home_group, R.layout.adapter_item_home_item);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setAdapter(adapter);

        vm.getUpcomingClasses().observe(this, adapter::setData);
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