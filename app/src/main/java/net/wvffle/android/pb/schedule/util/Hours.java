package net.wvffle.android.pb.schedule.util;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;

public class Hours {
    public static String idToHour(int hourId) {
        switch (hourId) {
            case 1:
                return "08:30";
            case 2:
                return "09:15";
            case 3:
                return "10:15";
            case 4:
                return "11:45";
            case 5:
                return "12:00";
            case 6:
                return "12:45";
            case 7:
                return "14:00";
            case 8:
                return "14:45";
            case 9:
                return "16:00";
            case 10:
                return "16:45";
            case 11:
                return "17:40";
            case 12:
                return "18:25";
            case 13:
                return "19:20";
            case 14:
                return "20:05";
        }

        return "00:00";
    }

    public static LocalDateTime idToTodaysLocalDateTame(int hourId) {
        String[] timeParts = idToHour(hourId).split(":");
        LocalTime time = LocalTime.of(Integer.parseInt(timeParts[0]), Integer.parseInt(timeParts[1]));
        return LocalDateTime.of(
                LocalDate.now(),
                time
        );
    }
}
