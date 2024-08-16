package utils;

import java.math.BigDecimal;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

@Converter(autoApply = true)
public class BigDecimalConverter implements AttributeConverter<ObjectProperty<BigDecimal>, BigDecimal> {

    @Override
    public BigDecimal convertToDatabaseColumn(ObjectProperty<BigDecimal> arg0) {
        return arg0.getValue();
    }

    @Override
    public SimpleObjectProperty<BigDecimal> convertToEntityAttribute(BigDecimal arg0) {
        return new SimpleObjectProperty<>(arg0);
    }
}