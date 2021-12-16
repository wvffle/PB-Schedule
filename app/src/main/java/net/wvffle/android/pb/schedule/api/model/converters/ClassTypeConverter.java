package net.wvffle.android.pb.schedule.api.model.converters;

import net.wvffle.android.pb.schedule.enums.ClassType;

import io.objectbox.converter.PropertyConverter;

public class ClassTypeConverter implements PropertyConverter<ClassType, String> {
    @Override
    public ClassType convertToEntityProperty(String databaseValue) {
        return ClassType.valueOfName(databaseValue);
    }

    @Override
    public String convertToDatabaseValue(ClassType entityProperty) {
        return entityProperty.name();
    }
}
