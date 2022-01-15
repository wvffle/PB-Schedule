package net.wvffle.android.pb.schedule.api.calendar;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import net.wvffle.android.pb.schedule.models.Schedule;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EmptyDayDecorator implements DayViewDecorator {
    Set<Integer> nonEmptyDays = new HashSet<>();

    public EmptyDayDecorator(List<Schedule> classes) {
        for (Schedule clazz : classes) {
            nonEmptyDays.add(clazz.getDay());
        }
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return !nonEmptyDays.contains(day.getDate().getDayOfWeek().getValue());
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setDaysDisabled(true);
    }
}
