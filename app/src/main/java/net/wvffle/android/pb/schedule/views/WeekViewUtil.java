package net.wvffle.android.pb.schedule.views;


import org.threeten.bp.DayOfWeek;

public class WeekViewUtil {


    public static int dayOfWeekToCalendarDay(int dayOfWeek) {
        return dayOfWeek % 7 + 1;
    }


    public static int calendarDayToDayOfWeek(int calendarDay) {
        return calendarDay == 1 ? 7 : calendarDay - 1;
    }


    public static int daysBetween(DayOfWeek dateOne, org.threeten.bp.DayOfWeek dateTwo) {
        int daysInBetween = 0;
        while (dateOne != dateTwo) {
            daysInBetween++;
            dateOne = dateOne.plus(1);
        }
        return daysInBetween;
    }

    public static int getPassedMinutesInDay(int hour, int minute) {
        return hour * 60 + minute;
    }


    public static int getPassedMinutesInDay(DayTime date) {
        return getPassedMinutesInDay(date.getHour(), date.getMinute());
    }
}

