package utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class GebruikerRolEnumConverter implements AttributeConverter<GebruikerRolEnum, String> {
	@Override
	public String convertToDatabaseColumn(GebruikerRolEnum attribute) {
		if (attribute != null) {
			return attribute.toString();
		}
		return null;
	}

	@Override
	public GebruikerRolEnum convertToEntityAttribute(String dbData) {
		if (dbData != null) {
			System.out.println(dbData);
			try {
			GebruikerRolEnum enumValue = GebruikerRolEnum.valueOf(dbData.toUpperCase());
			return enumValue;
			} catch (Exception e) {
				System.err.println("Invalid value for GebruikerRolEnum: " + dbData.toUpperCase());
			}
		}
		return null;
	}
}
