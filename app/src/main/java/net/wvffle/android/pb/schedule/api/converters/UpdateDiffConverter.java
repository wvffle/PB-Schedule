package net.wvffle.android.pb.schedule.api.converters;


import net.wvffle.android.pb.schedule.api.update.UpdateDiff;

import io.objectbox.converter.PropertyConverter;

public class UpdateDiffConverter implements PropertyConverter<UpdateDiff, String> {

    @Override
    public UpdateDiff convertToEntityProperty(String databaseValue) {
        return UpdateDiff.deserialize(databaseValue);
    }

    @Override
    public String convertToDatabaseValue(UpdateDiff entityProperty) {
        return entityProperty.serialize();
    }
}
