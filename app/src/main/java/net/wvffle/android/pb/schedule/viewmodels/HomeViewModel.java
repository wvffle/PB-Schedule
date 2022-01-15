package net.wvffle.android.pb.schedule.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import net.wvffle.android.pb.schedule.models.Schedule;
import net.wvffle.android.pb.schedule.util.GroupedItem;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.FormatStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class HomeViewModel extends ViewModel {
    MutableLiveData<LocalDate> date = new MutableLiveData<>();
    MutableLiveData<List<Schedule>> classes = new MutableLiveData<>();
    MutableLiveData<List<GroupedItem<Schedule>>> upcomingClasses = new MutableLiveData<>();

    public LiveData<LocalDate> getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        LocalDate today = CalendarDay.today().getDate();
        if (date.isBefore(today)) {
            date = today;
        }

        this.date.setValue(date);
        Log.d("date", date.toString());
        LocalDate finalDate = date;
        List<Schedule> upcoming = Objects.requireNonNull(classes.getValue())
                .stream()
                .filter(schedule -> {
                    if (schedule.getDay() > finalDate.getDayOfWeek().getValue()) {
                        return true;
                    }

                    // TODO [#54]: Check if classes are in even and odd weeks

                    if (schedule.getDay() == finalDate.getDayOfWeek().getValue()) {
                        // TODO [#55]: Check if current `date` is after the hour of `schedule` class
                        if (false) {
                            return false;
                        }
                    }

                    return false;
                })
                .collect(Collectors.toList());

        List<GroupedItem<Schedule>> items = new ArrayList<>();

        int lastDay = -1;
        for (Schedule clazz : upcoming) {
            if (lastDay != clazz.getDay()) {
                lastDay = clazz.getDay();

                LocalDate day = LocalDate.from(date)
                        .minusDays(date.getDayOfWeek().getValue())
                        .plusDays(clazz.getDay());

                items.add(GroupedItem.createGroup(day.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))));
            }

            items.add(GroupedItem.createItem(clazz));
        }

        upcomingClasses.setValue(items);
    }

    public LiveData<List<Schedule>> getClasses() {
        return classes;
    }

    public void setClasses(List<Schedule> classes) {
        this.classes.setValue(classes);
    }

    public LiveData<List<GroupedItem<Schedule>>> getUpcomingClasses() {
        return upcomingClasses;
    }
}
