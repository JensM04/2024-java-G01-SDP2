package utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

@Converter(autoApply = true)
public class IntegerPropertyConverter implements AttributeConverter<IntegerProperty, Integer> {

    @Override
    public Integer convertToDatabaseColumn(IntegerProperty attribute) {
        return attribute != null ? attribute.get() : null;
    }

    @Override
    public IntegerProperty convertToEntityAttribute(Integer dbData) {
        return new SimpleIntegerProperty(dbData);
    }
}