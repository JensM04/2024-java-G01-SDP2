package domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Date;
import java.util.Deque;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import repository.BestellingDao;
import repository.BetalingDao;
import repository.BetalingDaoJpa;
import utils.BestellingStatusEnum;
import utils.BestellingStatusEnumConverter;
import utils.BetaalStatusEnum;
import utils.BigDecimalConverter;
import utils.LocalDateTimeConverter;
import utils.UUIDValueConverter;

@Entity
@Access(AccessType.FIELD)
@Table(name = "Bestelling")
@NamedQueries({
		@NamedQuery(name = "B2BBestelling.findEvery", query = "SELECT b FROM B2BBestelling b WHERE b.leverancier = :leverancier"),
		@NamedQuery(name = "B2BBestelling.findAll", query = "SELECT b FROM B2BBestelling b WHERE b.leverancier = :leverancier"),
		@NamedQuery(name = "B2BBestelling.findById", query = "SELECT b FROM B2BBestelling b WHERE b.bestellingId = :bId"),
		@NamedQuery(name = "B2BBestelling.findByBedrijf", query = "SELECT b FROM B2BBestelling b WHERE b.klant = :bedrijf") })
public class B2BBestelling implements Bestelling, Serializable {

	private static final long serialVersionUID = 3210660632897765495L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bestellingId;

	@ManyToOne
	@JoinColumn(name = "leverancierid")
	private B2BBedrijf leverancier;

	@ManyToOne
	@JoinColumn(name = "klantid")
	private B2BBedrijf klant;
	
	private Adres leveradres;

	@OneToMany(mappedBy = "huidigeBestelling", cascade = CascadeType.PERSIST)
	private List<Betaling> betalingen = FXCollections.observableArrayList();

	@OneToMany(mappedBy = "b2bBestelling", cascade = CascadeType.PERSIST)
	private List<ProductInBestelling> producten;

	@Transient
	private Deque<BetalingsHerinnering> betalingsHerinneringen;
	
	@Convert(converter = LocalDateTimeConverter.class)
	private ObjectProperty<LocalDateTime> bestellingDatum = new SimpleObjectProperty<>();
	
	@Convert(converter = BigDecimalConverter.class)
	private ObjectProperty<BigDecimal> bedrag = new SimpleObjectProperty<>();

	@Convert(converter = UUIDValueConverter.class)
	private UUID uuidValue = UUID.randomUUID();

	@Convert(converter = BestellingStatusEnumConverter.class)
	private ObjectProperty<BestellingStatusEnum> bestellingStatus = new SimpleObjectProperty<>();
	
	@Transient
	private ObjectProperty<BetaalStatusEnum> betaalStatus = new SimpleObjectProperty<>();
	
	@Transient
	private final static BigDecimal VERZENDKOST = new BigDecimal(12.50);
	@Transient
	private static final int aantalDagenOmTeBetalen = 30;
	@Transient
	private static final int autoHerinneringBijXDagen = 3;
	@Transient
	private BestellingDao bestellingRepo;
	@Transient
	private BetalingDao betalingRepo;

	protected B2BBestelling() {
	}

	public B2BBestelling(B2BBedrijf klant, Adres leveradres, List<ProductInBestelling> producten,
			B2BBedrijf leverancier) {
		this.uuidValue = UUID.randomUUID();
		this.setKlant(klant);
		this.setLeveradres(leveradres);
		this.setProducten(producten);
		this.setLeverancier(leverancier);
		this.betalingsHerinneringen = new ArrayDeque<>();
		this.setBestellingDatum(LocalDateTime.now());
		this.setBedrag(BigDecimal
				.valueOf(producten.stream().mapToDouble(product -> product.getTotalePrijs().doubleValue()).sum()));
		this.setBestellingStatus(BestellingStatusEnum.GEPLAATST);
		this.setBetaalStatus(BetaalStatusEnum.ONVERWERKT);
	}

	private void setBestellingStatus(BestellingStatusEnum status) {
		if(status == null) throw new IllegalArgumentException("Status mag niet leeg zijn");
		this.bestellingStatus.set(status);
	}

	private boolean zijnGoederenInMagazijnBijStatus(BestellingStatusEnum status) {
		return switch(status) {
		case GELEVERD -> false;
		case GEPLAATST -> true;
		case VERWERKT -> true;
		case VERZONDEN -> false;
		case VOLTOOID -> false;
		case ONTVANGEN -> true;
		default -> false;
		};
	}

	private void setBestellingDatum(LocalDateTime datum) {
		this.bestellingDatum.set(datum);
	}

	private void setProducten(List<ProductInBestelling> producten) {
		if (producten == null) {
			throw new IllegalArgumentException("Producten mogen niet null zijn");
		}
		this.producten = producten;

	}

	private void setLeveradres(Adres leveradres) {
		if (leveradres == null)
			throw new IllegalArgumentException("Leveradres mag niet null zijn");
		this.leveradres = leveradres;
	}

	private void setKlant(B2BBedrijf klant) {
		if (klant == null) {
			throw new IllegalArgumentException("Klant mag niet null zijn");
		}
		this.klant = klant;
		klant.addUitgaandeBestelling(this);
	}

	private void setLeverancier(B2BBedrijf leverancier) {
		if (leverancier == null) {
			throw new IllegalArgumentException("Leverancier mag niet null zijn");
		}
		this.leverancier = leverancier;
		leverancier.addInkomendeBestelling(this);
	}

	private void setBedrag(BigDecimal value) {

		this.bedrag.set(value);
	}


	public void setBetaalStatus(BetaalStatusEnum value) {
		this.betaalStatus.set(value);
	}

	@Override
	public Bedrijf getKlant() {
		return this.klant;
	}

	@Override
	public Adres getLeveradres() {
		return this.leveradres;
	}
	
	@Override
	public Bedrijf getLeverancier() {
		return this.leverancier;
	}

	@Override
	public BigDecimal getTotaalBestellingbedragMetBTW() {
	    BigDecimal bedrag = BigDecimal.valueOf(producten.stream().mapToDouble(product -> product.getTotalePrijs().doubleValue()).sum()).add(VERZENDKOST);
	    BigDecimal BTW = bedrag.multiply(BigDecimal.valueOf(0.21));
	    BigDecimal totaal = bedrag.add(BTW);
	    return totaal;
	}

	@Override
	public BigDecimal getTotaalBestellingbedragExclBTW() {
	    BigDecimal bedrag = BigDecimal.valueOf(producten.stream().mapToDouble(product -> product.getTotalePrijs().doubleValue()).sum());
	    BigDecimal totaal = bedrag.add(VERZENDKOST);
	    return totaal;
	}


	@Override
	public LocalDateTime getDatumLaatsteBetalingsHerinnering() {
		if (this.betalingsHerinneringen == null) {
			this.betalingsHerinneringen = new ArrayDeque<BetalingsHerinnering>();
		}
		return betalingsHerinneringen.peekLast() == null ? null : betalingsHerinneringen.peekLast().getDatumGestuurd();
	}

	@Override
	public LocalDateTime getBestellingDatum() {
		return this.bestellingDatum.get();
	}

	@Override
	@Access(AccessType.PROPERTY)
	public BestellingStatusEnum getBestellingStatus() {
		return this.bestellingStatus.get();
	}

	@Access(AccessType.PROPERTY)
	@Override
	public BetaalStatusEnum getBetaalStatus() {
		return this.betaalStatus.get();
	}

	@Override
	public List<ProductInBestellingInterface> getProductenInBestelling() {
		return Collections.unmodifiableList(this.producten);
	}

	@Override
	public void stuurBetalingsHerinnering() {
		if (this.betalingsHerinneringen == null) {
			this.betalingsHerinneringen = new ArrayDeque<BetalingsHerinnering>();
		}
		this.betalingsHerinneringen.addLast(new BetalingsHerinnering(this));
	}

	public void wijzigBestelling(BestellingStatusEnum bestellingStatus, BetaalStatusEnum betaalStatus) {
		this.wijzigBestellingStatus(bestellingStatus);
		this.wijzigBetalingsStatus(betaalStatus);
		System.out.printf("Het id van de originele bestelling is: %s", this.getUuid());
	}

	public void wijzigBetalingsStatus(BetaalStatusEnum betaalStatus) {
		setBetaalStatus(betaalStatus);
	}

	public void wijzigBestellingStatus(BestellingStatusEnum status) {
		if (status == null) {
			throw new IllegalArgumentException("Bestellingstatus mag niet null zijn");
		}

		BestellingStatusEnum old = this.getBestellingStatus();
		if(old != null) {
			if(zijnGoederenInMagazijnBijStatus(old) && !zijnGoederenInMagazijnBijStatus(status)) {
				this.producten.forEach(p -> p.haalBesteldAantalUitVoorraad());
			}
			if(zijnGoederenInMagazijnBijStatus(status) && !zijnGoederenInMagazijnBijStatus(old)) {
				this.producten.forEach(p -> p.plaatsBesteldAantalInVoorraad());
			}
		} else {
			if(!zijnGoederenInMagazijnBijStatus(status)) {
				this.producten.forEach(p -> p.haalBesteldAantalUitVoorraad());
			}
		}

		setBestellingStatus(status);
	}

//	@Override
//	public boolean isInFilter(BestellingFilterDTO filter) {
//		if (filter.search != null) {
//			return filter.isSearchContained(this);
//		}
//		if (filter.id != null) {
//			return filter.isIdContained(this);
//		}
//		if (filter.bestelDatum != null) {
//			return filter.isBestelDatumContained(this);
//		}
//		if (filter.klantNaam != null) {
//			return filter.isKlantNaamContained(this);
//		}
//		if (filter.betaalStatus != null) {
//			return filter.isBetaalStatusContained(this);
//		}
//		return true;
//	}

	@Override
	public boolean isInSearch(String query) {
		return false;
	}

	@Override
	public ObjectProperty<UUID> uuidProperty() {
		return new SimpleObjectProperty<UUID>(this.getUuid());
	}

	@Override
	public ObjectProperty<LocalDateTime> datumProperty() {
		return this.bestellingDatum;
	}

	@Override
	public ObjectProperty<BigDecimal> totaalBedragProperty() {
		return this.totaalBedragProperty();
	}

	@Override
	public ObjectProperty<BetaalStatusEnum> betaalStatusProperty() {
		return this.betaalStatus;
	}

	@Override
	public ObjectProperty<BestellingStatusEnum> statusProperty() {
		return this.bestellingStatus;
	}

	@Override
	public StringProperty klantNaamProperty() {
		return this.klant.naamProperty();
	}

	public boolean isAutomatischeBetalingsherinnering() {
		return ChronoUnit.DAYS.between(LocalDateTime.now(), getBestellingDatum().plusDays(aantalDagenOmTeBetalen)) == autoHerinneringBijXDagen;
	}

	@Override
	public UUID getUuid() {
		return this.uuidValue;
	}
	
	@Override
	public ObservableList<Betaling> getBetalingen(){
		if (this.betalingRepo == null) {
			this.betalingRepo = new BetalingDaoJpa();
		}
		return FXCollections.observableArrayList(this.betalingRepo.findByBestelling(this));
	}
}