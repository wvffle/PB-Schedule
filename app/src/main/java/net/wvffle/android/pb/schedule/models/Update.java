package net.wvffle.android.pb.schedule.models;

import com.google.gson.JsonObject;

import net.wvffle.android.pb.schedule.api.model.Model;
import net.wvffle.android.pb.schedule.api.update.UpdateData;
import net.wvffle.android.pb.schedule.api.update.UpdateDiff;
import net.wvffle.android.pb.schedule.api.converters.UpdateDataConverter;
import net.wvffle.android.pb.schedule.api.converters.UpdateDiffConverter;

import org.joda.time.format.ISODateTimeFormat;

import java.text.DateFormat;
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

    public String getHash() {
        return hash;
    }

    public Date getDate () {
        return date;
    }

    public UpdateData getData () {
        return data;
    }

    public UpdateDiff getDiff () {
        return diff;
    }
}
