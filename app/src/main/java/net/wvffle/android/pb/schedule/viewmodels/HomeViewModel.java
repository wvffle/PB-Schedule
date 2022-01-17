package net.wvffle.android.pb.schedule.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import net.wvffle.android.pb.schedule.models.Schedule;
import net.wvffle.android.pb.schedule.util.GroupedItem;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.FormatStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class HomeViewModel extends ViewModel {
    MutableLiveData<LocalDateTime> dateTime = new MutableLiveData<>();
    MutableLiveData<List<Schedule>> classes = new MutableLiveData<>();
    MutableLiveData<List<GroupedItem<Schedule>>> upcomingClasses = new MutableLiveData<>();

    public LiveData<LocalDateTime> getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        LocalDateTime today = LocalDateTime.now();
        if (dateTime.isBefore(today)) {
            dateTime = today;
        }

        boolean isOddWeek = dateTime.getDayOfYear() / 7 % 2 == 0;

        this.dateTime.setValue(dateTime);
        LocalDateTime finalDateTime = dateTime;
        List<Schedule> upcoming = Objects.requireNonNull(classes.getValue())
                .stream()
                .filter(schedule -> {

                    if (isOddWeek && !schedule.isOddWeeks() || !isOddWeek && !schedule.isEvenWeeks()) {
                        return false;
                    }

                    if (schedule.getDay() > finalDateTime.getDayOfWeek().getValue()) {
                        return true;
                    }

                    if (schedule.getDay() == finalDateTime.getDayOfWeek().getValue()) {
                        // TODO [#55]: Check if current `date` is after the hour of `schedule` class
                        //             Also make use of `schedule.getIntervals()` and return false if only 15 minutes are left
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

                LocalDate day = LocalDate.of(dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth())
                        .minusDays(dateTime.getDayOfWeek().getValue())
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
