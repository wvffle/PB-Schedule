package net.wvffle.android.pb.schedule;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import net.wvffle.android.pb.schedule.api.ScheduleParser;
import net.wvffle.android.pb.schedule.api.ScheduleUpdater;
import net.wvffle.android.pb.schedule.api.db.Room;
import net.wvffle.android.pb.schedule.api.db.ScheduleEntry;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ScheduleParserTest {
    @Test
    public void canParseXMLData () {
        List<ScheduleEntry> entries = new ScheduleParser(new ScheduleUpdater().getScheduleData()).parse();
        assertNotEquals(0, entries.size());
        for (ScheduleEntry entry : entries) {
            if (entry instanceof Room) {
                Room room = (Room) entry;
                assertNotEquals("", room.getName());
                assertTrue(room.getId() > -1);
                assertTrue(room.getUpdatedAt() > -1);
            }
        }
    }
}