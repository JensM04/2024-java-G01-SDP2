package domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import utils.BetaalStatusEnum;
import utils.BigDecimalConverter;
import utils.BooleanPropertyConverter;
import utils.LocalDateTimeConverter;

@Entity
@NamedQueries({
		@NamedQuery(name = "Betaling.findByBestelling", query = "SELECT b FROM Betaling b WHERE b.huidigeBestelling = :bestelling"),
		@NamedQuery(name = "Betaling.findByBedrijf", query = "SELECT b FROM Betaling b WHERE b.huidigBedrijf = :bedrijf") })
public class Betaling {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int betalingId;

	@ManyToOne
	@JoinColumn(name = "BestellingID")
	B2BBestelling huidigeBestelling;

	@ManyToOne
	@JoinColumn(name = "KlantID")
	B2BBedrijf huidigBedrijf;

	@Convert(converter = BigDecimalConverter.class)
	private SimpleObjectProperty<BigDecimal> teBetalen = new SimpleObjectProperty<BigDecimal>();

	@Convert(converter = BigDecimalConverter.class)
	private SimpleObjectProperty<BigDecimal> betaaldeBedrag = new SimpleObjectProperty<BigDecimal>();

	@Convert(converter = LocalDateTimeConverter.class)
	private SimpleObjectProperty<LocalDateTime> betaalDatum = new SimpleObjectProperty<LocalDateTime>();

	@Convert(converter = BooleanPropertyConverter.class)
	private SimpleBooleanProperty isVerwerkt = new SimpleBooleanProperty();

	@Convert(converter = BooleanPropertyConverter.class)
	private SimpleBooleanProperty isGoedgekeurd = new SimpleBooleanProperty();

	public Betaling(B2BBestelling bestelling, BigDecimal betaaldeBedrag) {
		this.huidigeBestelling = bestelling;
		this.huidigBedrijf = (B2BBedrijf) this.huidigeBestelling.getKlant();
		this.teBetalen.set(bestelling.getTotaalBestellingbedragExclBTW());
		this.betaaldeBedrag.set(betaaldeBedrag);
		this.betaalDatum.set(LocalDateTime.now());
		this.isVerwerkt.set(false);
		this.isGoedgekeurd.set(false);
	}

	protected Betaling() {

	}

	public ObjectProperty<UUID> bestellingUuidProperty() {
		return this.huidigeBestelling.uuidProperty();
	}

	public SimpleObjectProperty<BigDecimal> teBetalenProperty() {
		return this.teBetalen;
	}

	public SimpleObjectProperty<BigDecimal> betaaldeBedragProperty() {
		return this.betaaldeBedrag;
	}

	public SimpleBooleanProperty isVerwerktProperty() {
		return this.isVerwerkt;
	}

	public SimpleBooleanProperty isGoedgekeurdProperty() {
		return this.isGoedgekeurd;
	}

	public BigDecimal getTeBetalen() {
		return this.teBetalen.get();
	}

	public BigDecimal getBetaaldeBedrag() {
		return this.betaaldeBedrag.get();
	}

	public boolean getIsVerwerkt() {
		return this.isVerwerkt.get();
	}

	public boolean getIsGoedgekeurd() {
		return this.isGoedgekeurd.get();
	}

	public void verwerk() {
		if (this.getIsVerwerkt() == true) {
			throw new IllegalArgumentException("Deze betaling is reeds verwerkt");
		} else if (this.getTeBetalen().compareTo(this.getBetaaldeBedrag()) == 0) {
			this.huidigeBestelling.wijzigBetalingsStatus(BetaalStatusEnum.BETAALD);
			this.isGoedgekeurd.set(true);
		} else if (this.getTeBetalen().compareTo(this.getBetaaldeBedrag()) == 1) {
			this.huidigeBestelling.wijzigBetalingsStatus(BetaalStatusEnum.FACTUUR_VERZONDEN);
			this.isGoedgekeurd.set(false);
		}
		this.isVerwerkt.set(true);
	}
}
