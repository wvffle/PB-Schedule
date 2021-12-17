package net.wvffle.android.pb.schedule.api.converters;


import net.wvffle.android.pb.schedule.api.update.UpdateDiff;
import net.wvffle.android.pb.schedule.util.Serializer;

import java.io.IOException;

import io.objectbox.converter.PropertyConverter;

public class UpdateDiffConverter implements PropertyConverter<UpdateDiff, String> {

    @Override
    public UpdateDiff convertToEntityProperty(String databaseValue) {
        try {
            return (UpdateDiff) Serializer.getInstance().fromString(databaseValue);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            // TODO: Add Sentry
        }

        return null;
    }

    @Override
    public String convertToDatabaseValue(UpdateDiff entityProperty) {
        try {
            return Serializer.getInstance().toString(entityProperty);
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: Add Sentry
        }

        return null;
    }
}
