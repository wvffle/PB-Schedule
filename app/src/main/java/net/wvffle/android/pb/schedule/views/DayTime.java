package net.wvffle.android.pb.schedule.views;


import androidx.annotation.NonNull;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class DayTime implements Comparable<DayTime> {

    private LocalTime time;
    private DayOfWeek day;

    public DayTime(DayTime dayTime) {
        this.time = dayTime.time;
        this.day = dayTime.day;
    }

    public DayTime(DayOfWeek day, LocalTime time) {
        this.time = time;
        this.day = day;
    }


    public DayTime(DayOfWeek day, int hour, int minute) {
        this(day, LocalTime.of(hour, minute));
    }


    public DayTime(int day, int hour, int minute) {
        this(DayOfWeek.of(day), hour, minute);
    }


    public DayTime() {
    }

    public DayTime(LocalDateTime localDateTime) {
        this.day = localDateTime.getDayOfWeek();
        this.time = localDateTime.getTime(); //nwm
    }

    public DayOfWeek getDay() {
        return day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }


    public int getDayValue() {
        return day.getValue();
    }


    public int getHour() {
        return this.time.getHour();
    }


    public int getMinute() {
        return this.time.getMinute();
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setDay(int day) {
        this.day = DayOfWeek.of(day);
    }


    public void addDays(int days) {
        this.day = this.day.plus(days);
    }


    public void addHours(int hours) {
        this.time = this.time.plusHours(hours);
    }


    public void addMinutes(int minutes) {
        this.time = this.time.plusMinutes(minutes);
    }

    @Override
    public int compareTo(@NonNull DayTime dayTime) {
        if (this.day == dayTime.day) {
            return this.time.compareTo(dayTime.time);
        } else {
            return this.day.compareTo(dayTime.day);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DayTime dayTime = (DayTime) o;
        return Objects.equals(time, dayTime.time) &&
                day == dayTime.day;
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, day);
    }


    public boolean isAfter(DayTime otherDayTime) {
        return this.compareTo(otherDayTime) > 0;
    }


    public boolean isBefore(DayTime otherDayTime) {
        return this.compareTo(otherDayTime) < 0;
    }


    public boolean isSame(DayTime otherDayTime) {
        return this.compareTo(otherDayTime) == 0;
    }


    public void setMinimumTime() {
        this.time = LocalTime.MIN;
    }

    public void setTime(int hour, int minute) {
        this.time = LocalTime.of(hour, minute);
    }

    public void subtractMinutes(int minutes) {
        this.time = this.time.minusMinutes(minutes);
    }


    public long toNumericalUnit() {
        return (this.getTime().toNanoOfDay()) + this.getDay().getValue();
    }

    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("K:ha");
        return "DayTime{" +
//                "time=" + time.format(dtf) + nie dziala
                ", day=" + day +
                '}';
    }
}


