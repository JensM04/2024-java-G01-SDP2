package utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

@Converter(autoApply = true)
public class BooleanPropertyConverter implements AttributeConverter<BooleanProperty, Boolean> {

	@Override
	public Boolean convertToDatabaseColumn(BooleanProperty arg0) {
		return arg0.getValue();
	}

	@Override
	public BooleanProperty convertToEntityAttribute(Boolean arg0) {
		return new SimpleBooleanProperty(arg0);
	}

}