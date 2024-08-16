package utils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

@Converter(autoApply = true)
public class CustomAttributeConverter implements AttributeConverter<Object, Object> {

	@Override
	public Object convertToDatabaseColumn(Object attribute) {
		if (attribute instanceof BestellingStatusEnum) {
			return ((BestellingStatusEnum) attribute).toString();
		}
		if (attribute instanceof BigDecimal) {
			return ((BigDecimal) attribute).unscaledValue();
		}
		if (attribute instanceof LocalDateTime) {
			return attribute == null ? null : Timestamp.valueOf((LocalDateTime) attribute);
		}
		if (attribute instanceof SimpleBooleanProperty) {
			return ((SimpleBooleanProperty) attribute).getValue();
		}
		if (attribute instanceof SimpleIntegerProperty) {
			return ((SimpleIntegerProperty) attribute).get();
		}
		if (attribute instanceof SimpleStringProperty) {
			return ((SimpleStringProperty) attribute).get();
		}
		return null;
	}

	@Override
	public Object convertToEntityAttribute(Object dbData) {
		if (dbData instanceof String) {
			return BestellingStatusEnum.valueOf((String) dbData);
		}
		if (dbData instanceof java.math.BigInteger) {
			return dbData == null ? null : new BigDecimal((java.math.BigInteger) dbData, 2);
		}
		if (dbData instanceof Timestamp) {
			return dbData == null ? null : ((Timestamp) dbData).toLocalDateTime();
		}
		if (dbData instanceof Boolean) {
			return new SimpleBooleanProperty((Boolean) dbData);
		}
		if (dbData instanceof Integer) {
			return new SimpleIntegerProperty((Integer) dbData);
		}
		if (dbData instanceof String) {
			return new SimpleStringProperty((String) dbData);
		}
		return null;
	}
}
