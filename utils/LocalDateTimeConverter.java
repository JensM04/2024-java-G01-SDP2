package utils;

import java.time.LocalDateTime;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<ObjectProperty<LocalDateTime>, LocalDateTime> {

    @Override
    public LocalDateTime convertToDatabaseColumn(ObjectProperty<LocalDateTime> arg0) {
        return arg0.getValue();
    }

    @Override
    public ObjectProperty<LocalDateTime> convertToEntityAttribute(LocalDateTime arg0) {
        return new SimpleObjectProperty<>(arg0);
    }
}