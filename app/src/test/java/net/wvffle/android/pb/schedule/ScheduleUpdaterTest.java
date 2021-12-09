package net.wvffle.android.pb.schedule;

import static org.junit.Assert.assertNotEquals;

import net.wvffle.android.pb.schedule.api.ScheduleParser;
import net.wvffle.android.pb.schedule.api.ScheduleUpdater;

import org.junit.Test;

public class ScheduleUpdaterTest {
    ScheduleUpdater updater = new ScheduleUpdater();


    @Test
    public void canFetchXMLData () {
        String xml = updater.getScheduleData();
        assertNotEquals(null, xml);
        assertNotEquals("", xml);
    }
}