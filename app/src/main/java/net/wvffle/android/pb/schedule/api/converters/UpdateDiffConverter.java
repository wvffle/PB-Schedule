package net.wvffle.android.pb.schedule.api.converters;


import net.wvffle.android.pb.schedule.api.update.UpdateDiff;

import io.objectbox.converter.PropertyConverter;

public class UpdateDiffConverter implements PropertyConverter<UpdateDiff, String> {

    /**
     * Deserialize a string into UpdateDiff
     * @param databaseValue serialized string
     * @return UpdateDiff object
     */
    @Override
    public UpdateDiff convertToEntityProperty(String databaseValue) {
        return UpdateDiff.deserialize(databaseValue);
    }

    /**
     * Serialize UpdateDiff to a string
     * @param entityProperty UpdateDiff object
     * @return serialized string
     */
    @Override
    public String convertToDatabaseValue(UpdateDiff entityProperty) {
        return entityProperty.serialize();
    }
}
