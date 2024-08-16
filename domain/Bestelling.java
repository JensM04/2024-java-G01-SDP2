package domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import utils.BestellingStatusEnum;
import utils.BetaalStatusEnum;

public interface Bestelling extends /* Filterable<BestellingFilterDTO>, */ Searchable, UUIDIdentifiable {
	LocalDateTime getBestellingDatum();

	BestellingStatusEnum getBestellingStatus();

	void stuurBetalingsHerinnering();

	Adres getLeveradres();

	Bedrijf getKlant();

	LocalDateTime getDatumLaatsteBetalingsHerinnering();

	List<ProductInBestellingInterface> getProductenInBestelling();

	BigDecimal getTotaalBestellingbedragMetBTW();

	BigDecimal getTotaalBestellingbedragExclBTW();

	ObjectProperty<UUID> uuidProperty();

	StringProperty klantNaamProperty();

	ObjectProperty<LocalDateTime> datumProperty();

	ObjectProperty<BigDecimal> totaalBedragProperty();

	ObjectProperty<BestellingStatusEnum> statusProperty();

	ObjectProperty<BetaalStatusEnum> betaalStatusProperty();

	BetaalStatusEnum getBetaalStatus();
	
	UUID getUuid();

	ObservableList<Betaling> getBetalingen();

	Bedrijf getLeverancier();

}
