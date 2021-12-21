package net.wvffle.android.pb.schedule.api.converters;

import net.wvffle.android.pb.schedule.api.enums.ClassType;

import io.objectbox.converter.PropertyConverter;

public class ClassTypeConverter implements PropertyConverter<ClassType, String> {
    /**
     * Deserialize a string into ClassType
     * @param databaseValue serialized string
     * @return ClassType enum entry
     */
    @Override
    public ClassType convertToEntityProperty(String databaseValue) {
        return ClassType.valueOfName(databaseValue);
    }

    /**
     * Serialize ClassType to a string
     * @param entityProperty ClassType enum entry
     * @return serialized string
     */
    @Override
    public String convertToDatabaseValue(ClassType entityProperty) {
        return entityProperty.name();
    }
}
