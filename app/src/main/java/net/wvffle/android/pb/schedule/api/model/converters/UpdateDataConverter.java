package net.wvffle.android.pb.schedule.api.model.converters;

import net.wvffle.android.pb.schedule.api.model.UpdateData;
import net.wvffle.android.pb.schedule.util.Serializer;

import java.io.IOException;

import io.objectbox.converter.PropertyConverter;

public class UpdateDataConverter implements PropertyConverter<UpdateData, String> {

    @Override
    public UpdateData convertToEntityProperty(String databaseValue) {
        try {
            return (UpdateData) Serializer.getInstance().fromString(databaseValue);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String convertToDatabaseValue(UpdateData entityProperty) {
        try {
            return Serializer.getInstance().toString(entityProperty);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
