package net.wvffle.android.pb.schedule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import net.wvffle.android.pb.schedule.api.BackendApi;
import net.wvffle.android.pb.schedule.api.update.UpdateData;
import net.wvffle.android.pb.schedule.api.update.UpdateDiff;
import net.wvffle.android.pb.schedule.api.update.UpdateEntry;
import net.wvffle.android.pb.schedule.models.Update;

import org.junit.Test;

import java.util.List;

public class UpdateModelTest {

    @Test
    public void canFetchUpdates () {
        List<UpdateEntry> updates = BackendApi.getUpdates();
        assertTrue(updates.size() > 0);
    }

    @Test
    public void canFetchSingleUpdate () {
        List<UpdateEntry> updates = BackendApi.getUpdates();
        Update update = BackendApi.getUpdate(updates.get(0).getHash());

        assertNotNull(update);
        assertEquals(updates.get(0).getHash(), update.getHash());
        assertEquals(updates.get(0).id, update.id);

        UpdateData data = update.getData();
        assertNotNull(data);
        assertTrue(data.getRooms().size() > 0);

        UpdateDiff diff = update.getDiff();
        assertNotNull(diff);

        boolean hasAdded = diff.getAdded().size() > 0;
        boolean hasRemoved =  diff.getRemoved().size() > 0;
        assertTrue(hasAdded || hasRemoved);

        if (hasAdded) {
            assertTrue(diff.getAddedModels().size() > 0);
        }

        if (hasRemoved) {
            assertTrue(diff.getRemovedModels().size() > 0);
        }
    }
}
