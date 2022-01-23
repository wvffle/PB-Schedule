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

import java.io.IOException;
import java.util.List;

public class UpdateModelTest {

    @Test
    public void canFetchUpdates () throws IOException {
        List<UpdateEntry> updates = BackendApi.getService().getUpdates().execute().body();
        assertTrue(updates.size() > 0);
    }

    @Test
    public void canFetchSingleUpdate () throws IOException {
        List<UpdateEntry> updates = BackendApi.getService().getUpdates().execute().body();
        assertNotNull(updates);

        String hash = updates.get(0).getHash();
        Update update = BackendApi.getService().getUpdate(hash).execute().body();

        assertNotNull(update);
        assertEquals(hash, update.getHash());
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
