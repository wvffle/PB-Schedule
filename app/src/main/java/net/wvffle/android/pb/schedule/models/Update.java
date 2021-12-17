package net.wvffle.android.pb.schedule.models;

import com.google.gson.JsonObject;

import net.wvffle.android.pb.schedule.api.model.Model;
import net.wvffle.android.pb.schedule.api.update.UpdateData;
import net.wvffle.android.pb.schedule.api.update.UpdateDiff;
import net.wvffle.android.pb.schedule.api.converters.UpdateDataConverter;
import net.wvffle.android.pb.schedule.api.converters.UpdateDiffConverter;

import org.joda.time.format.ISODateTimeFormat;

import java.text.ParseException;
import java.util.Date;

import io.objectbox.annotation.ConflictStrategy;
import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;
import io.objectbox.annotation.Unique;

@Entity
public class Update implements Model {
    @Id(assignable = true)
    public long id;

    @Index
    @Unique(onConflict = ConflictStrategy.REPLACE)
    private final String hash;
    private final Date date;

    @Convert(converter = UpdateDataConverter.class, dbType = String.class)
    private final UpdateData data;

    @Convert(converter = UpdateDiffConverter.class, dbType = String.class)
    private final UpdateDiff diff;

    public Update(long id, String hash, Date date, UpdateData data, UpdateDiff diff) {
        this.id = id;
        this.hash = hash;
        this.date = date;
        this.data = data;
        this.diff = diff;
    }

    /**
     * Create a new Update from JSON
     * @param update JsonObject containing update data
     * @return new update model
     */
    public static Update fromJson(JsonObject update) throws ParseException {
        UpdateData data = new UpdateData(update.getAsJsonObject("data"));
        UpdateDiff diff = new UpdateDiff(update.getAsJsonObject("diff"), data);
        return new Update(
                update.get("id").getAsLong(),
                update.get("hash").getAsString(),
                ISODateTimeFormat.dateTime()
                        .parseDateTime(update.get("date").getAsString())
                        .toDate(),
                data,
                diff
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
     * @return Date of the update
     */
    public Date getDate () {
        return date;
    }

    /**
     * Return data of the update
     * @return UpdateData of the update
     */
    public UpdateData getData () {
        return data;
    }

    /**
     * Return diff of the update
     * @return UpdateDiff of the update
     */
    public UpdateDiff getDiff () {
        return diff;
    }
}
