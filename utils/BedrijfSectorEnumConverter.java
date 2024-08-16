
package utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

@Converter(autoApply = true)
public class BedrijfSectorEnumConverter implements AttributeConverter<ObjectProperty<BedrijfSectorEnum>, String> {

    @Override
    public String convertToDatabaseColumn(ObjectProperty<BedrijfSectorEnum> attribute) {
        if (attribute != null && attribute.getValue() != null) {
            return attribute.getValue().toString();
        }
        return null;
    }

    @Override
    public ObjectProperty<BedrijfSectorEnum> convertToEntityAttribute(String dbData) {
        if (dbData != null) {        	
        	BedrijfSectorEnum enumValue = BedrijfSectorEnum.valueOf(dbData);
            return new SimpleObjectProperty<>(enumValue);
        }
        return null;
    }
}