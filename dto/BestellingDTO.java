package dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import domain.Adres;
import domain.Bedrijf;
import domain.ProductInBestelling;
import javafx.beans.property.ObjectProperty;
import utils.BestellingStatusEnum;
import utils.BetaalStatusEnum;

public class BestellingDTO {

	public UUID uuid;

	public static Integer id;

	public String klantNaam;
	public Bedrijf klant;
	public Adres leveradres;
	public BestellingStatusEnum bestellingStatus;
	public BetaalStatusEnum betaalStatus;
	public List<ProductInBestelling> producten;
	public ObjectProperty<LocalDateTime> bestellingDatum;

	public String uuidString;
}
