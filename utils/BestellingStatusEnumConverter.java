package utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

@Converter(autoApply = true)
public class BestellingStatusEnumConverter
		implements AttributeConverter<ObjectProperty<BestellingStatusEnum>, Integer> {

	@Override
	public Integer convertToDatabaseColumn(ObjectProperty<BestellingStatusEnum> attribute) {
		if (attribute != null && attribute.getValue() != null) {
			BestellingStatusEnum status = attribute.getValue();
			switch (status) {
			case GEPLAATST -> {
				return 0;
			}
			case VERWERKT -> {
				return 1;
			}
			case VERZONDEN -> {
				return 2;
			}
			case UIT_VOOR_LEVERING -> {
				return 3;
			}
			case GELEVERD -> {
				return 4;
			}
			case VOLTOOID -> {
				return 5;
			}
			default -> throw new IllegalArgumentException("Unexpected value: " + status);
			}
		}
		return null;
	}

	@Override
	public ObjectProperty<BestellingStatusEnum> convertToEntityAttribute(Integer dbData) {
		if (dbData != null) {
			BestellingStatusEnum status = switch (dbData) {
			case 0 -> BestellingStatusEnum.GEPLAATST;

			case 1 -> BestellingStatusEnum.VERWERKT;

			case 2 -> BestellingStatusEnum.VERZONDEN;

			case 3 -> BestellingStatusEnum.UIT_VOOR_LEVERING;

			case 4 -> BestellingStatusEnum.GELEVERD;

			case 5 -> BestellingStatusEnum.VOLTOOID;

			default -> BestellingStatusEnum.GEPLAATST;
			};
			return new SimpleObjectProperty<>(status);
		}
		return null;

	}
}
