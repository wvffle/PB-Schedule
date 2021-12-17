package net.wvffle.android.pb.schedule.api.update;

import com.google.gson.JsonObject;

import org.joda.time.format.ISODateTimeFormat;

import java.util.Date;

/**
 * A container for update entries (without data and diff)
 */
public class UpdateEntry {
    public final long id;

    private final String hash;
    private final Date date;

    private UpdateEntry(long id, String hash, Date date) {
        this.id = id;
        this.hash = hash;
        this.date = date;
    }

    /**
     * Create a new UpdateEntry from JSON
     * @param json JsonObject containing update data
     * @return new update entry
     */
    public static UpdateEntry fromJson (JsonObject json) {
        return new UpdateEntry(
                json.get("id").getAsLong(),
                json.get("hash").getAsString(),
                ISODateTimeFormat.dateTime()
                        .parseDateTime(json.get("date").getAsString())
                        .toDate()
        );
    }

    /**
     * Return hash of the update
     * @return String hash
     */
    public String getHash() {
        return hash;
    }

    /**
     * Return date of the update
     * @return Date object
     */
    public Date getDate() {
        return date;
    }
}
