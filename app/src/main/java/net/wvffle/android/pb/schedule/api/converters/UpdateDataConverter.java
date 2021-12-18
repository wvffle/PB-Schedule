package net.wvffle.android.pb.schedule.api.converters;

import net.wvffle.android.pb.schedule.api.update.UpdateData;

import io.objectbox.converter.PropertyConverter;

public class UpdateDataConverter implements PropertyConverter<UpdateData, String> {

    /**
     * Deserialize a string into UpdateData
     * @param databaseValue serialized string
     * @return UpdateData object
     */
    @Override
    public UpdateData convertToEntityProperty(String databaseValue) {
        return UpdateData.deserialize(databaseValue);
    }

    /**
     * Serialize UpdateData to a string
     * @param entityProperty UpdateData object
     * @return serialized string
     */
    @Override
    public String convertToDatabaseValue(UpdateData entityProperty) {
        return entityProperty.serialize();
    }
}
