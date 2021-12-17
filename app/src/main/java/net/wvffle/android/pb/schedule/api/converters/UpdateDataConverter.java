package net.wvffle.android.pb.schedule.api.converters;

import net.wvffle.android.pb.schedule.api.update.UpdateData;
import net.wvffle.android.pb.schedule.util.Serializer;

import java.io.IOException;

import io.objectbox.converter.PropertyConverter;

public class UpdateDataConverter implements PropertyConverter<UpdateData, String> {

    @Override
    public UpdateData convertToEntityProperty(String databaseValue) {
        return UpdateData.deserialize(databaseValue);
    }

    @Override
    public String convertToDatabaseValue(UpdateData entityProperty) {
        return entityProperty.serialize();
    }
}
