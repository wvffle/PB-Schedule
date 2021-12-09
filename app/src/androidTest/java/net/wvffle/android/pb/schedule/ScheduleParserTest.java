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
        assertEquals("WA-23C-1", ((Room) entries.get(0)).getName());
        assertEquals(1, ((Room) entries.get(0)).getId());
    }
}