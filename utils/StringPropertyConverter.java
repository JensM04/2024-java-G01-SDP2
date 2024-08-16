package utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Converter(autoApply = true)
public class StringPropertyConverter implements AttributeConverter<StringProperty, String> {

    @Override
    public String convertToDatabaseColumn(StringProperty attribute) {
        return attribute != null ? attribute.get() : null;
    }

    @Override
    public StringProperty convertToEntityAttribute(String dbData) {
        return new SimpleStringProperty(dbData);
    }
}