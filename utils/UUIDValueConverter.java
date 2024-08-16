
package utils;

import java.util.UUID;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class UUIDValueConverter implements AttributeConverter<UUID, String> {
	@Override
	public String convertToDatabaseColumn(UUID arg0) {
		return arg0 != null ? arg0.toString() : null;
	}

    @Override
    public UUID convertToEntityAttribute(String dbData) {
        if (dbData != null) {
            return UUID.fromString(dbData);
        }
        return null;
    }

}