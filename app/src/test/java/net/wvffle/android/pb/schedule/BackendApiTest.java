package net.wvffle.android.pb.schedule;

import static org.junit.Assert.assertEquals;

import net.wvffle.android.pb.schedule.api.BackendApi;
import net.wvffle.android.pb.schedule.models.Room;

import org.junit.Test;

public class BackendApiTest {
    @Test
    public void testRoom() {
        String hash = "00d1c9c8a3668121c9fc4d89b848b17e73a61073";
        Room room = BackendApi.getRoom(hash);

        assertEquals(hash, room.getHash());
        assertEquals("209", room.getName());
    }
}