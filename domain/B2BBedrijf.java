package domain;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import dto.B2BBedrijfDTO;
import exceptions.RegistreerException;
import exceptions.RegistreerGebruikerException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.BedrijfSectorEnum;
import utils.BedrijfSectorEnumConverter;
import utils.BetaalStatusEnum;
import utils.BooleanPropertyConverter;
import utils.StringPropertyConverter;
import utils.UUIDValueConverter;

@Entity
@NamedQueries({ @NamedQuery(name = "B2BBedrijf.findAll", query = "SELECT b FROM B2BBedrijf b"),
		@NamedQuery(name = "B2BBedrijf.findByName", query = "SELECT b FROM B2BBedrijf b WHERE b.naam = :naam"),
		@NamedQuery(name = "B2BBedrijf.findByBestelling", query = "SELECT DISTINCT b FROM B2BBedrijf b JOIN B2BBestelling bs ON b.bedrijfId = bs.klant.bedrijfId WHERE bs.leverancier = :leverancier") })
public class B2BBedrijf implements Bedrijf, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bedrijfId;

	@Convert(converter = UUIDValueConverter.class)
	private UUID uuidValue;

	@Convert(converter = StringPropertyConverter.class)
	private StringProperty naam = new SimpleStringProperty();

	@Convert(converter = StringPropertyConverter.class)
	private StringProperty email = new SimpleStringProperty();

	@Convert(converter = StringPropertyConverter.class)
	private StringProperty telefoonnummer = new SimpleStringProperty();

	@Convert(converter = BooleanPropertyConverter.class)
	private BooleanProperty isActief = new SimpleBooleanProperty();

	@Transient
	private StringProperty kortAdresProperty = new SimpleStringProperty();

	@Transient
	private IntegerProperty aantalOpenstaandeBestellingenUitgaand = new SimpleIntegerProperty();

	@Transient
	private IntegerProperty aantalOpenstaandeBestellingenInkomend = new SimpleIntegerProperty();

	@Convert(converter = BedrijfSectorEnumConverter.class)
	private ObjectProperty<BedrijfSectorEnum> sector = new SimpleObjectProperty<>();

	private String BTWNr;
	
	@Embedded
	private Adres adres;

	@OneToMany(mappedBy = "leverancier", cascade = CascadeType.PERSIST)
	private List<B2BBestelling> inkomendeBestellingen = new ArrayList<>();
	@OneToMany(mappedBy = "klant", cascade = CascadeType.PERSIST)
	private List<B2BBestelling> uitgaandeBestellingen = new ArrayList<>();

	private String websiteURL;

	@OneToMany(mappedBy = "bedrijf", cascade = CascadeType.PERSIST)
	private List<Leverancier> leverancierAccounts = new ArrayList<>();

	@OneToMany(mappedBy = "bedrijf", cascade = CascadeType.PERSIST)
	private List<Klant> klantAccounts = new ArrayList<>();

	@OneToMany(mappedBy = "huidigBedrijf", cascade = CascadeType.PERSIST)
	private List<Betaling> betalingen = FXCollections.observableArrayList();

	protected B2BBedrijf() {
	}

	public B2BBedrijf(B2BBedrijfDTO bedrijfDTO, Adres adres) throws RegistreerException {
		this.uuidValue = UUID.randomUUID();
		try {
			setNaam(bedrijfDTO.naam);
		} catch (IllegalArgumentException ex) {
			bedrijfDTO.hasError = true;
			bedrijfDTO.naamError = ex.getMessage();
		}
		try {
			setAdres(adres);
		} catch (IllegalArgumentException ex) {
			bedrijfDTO.hasError = true;
			bedrijfDTO.adresError = ex.getMessage();
		}
		try {
			setEmail(bedrijfDTO.email);
		} catch (IllegalArgumentException ex) {
			bedrijfDTO.hasError = true;
			bedrijfDTO.emailError = ex.getMessage();
		}
		try {
			setTelefoonnummer(bedrijfDTO.telefoonnummer);
		} catch (IllegalArgumentException ex) {
			bedrijfDTO.hasError = true;
			bedrijfDTO.telefoonnummerError = ex.getMessage();
		}
		try {
			setWebsiteURL(bedrijfDTO.websiteURL);
		} catch (IllegalArgumentException ex) {
			bedrijfDTO.hasError = true;
			bedrijfDTO.websiteUrlError = ex.getMessage();
		}
		try {
			setSector(bedrijfDTO.sector);
		} catch (IllegalArgumentException ex) {
			bedrijfDTO.hasError = true;
			bedrijfDTO.sectorError = ex.getMessage();
		}
		if (bedrijfDTO.isActief != null) {
			setIsActief(bedrijfDTO.isActief);
		} else {
			setIsActief(true);
		}
		setBtwNr(bedrijfDTO.btw);
		
		if (bedrijfDTO.hasError)
			throw new RegistreerException();
	}

	public B2BBedrijf(String naam, String email, String telefoonnummer, Adres adres, String websiteURL,
			BedrijfSectorEnum sector, String Btw) {
		this.uuidValue = UUID.randomUUID();
		setNaam(naam);
		setEmail(email);
		setTelefoonnummer(telefoonnummer);
		setAdres(adres);
		setWebsiteURL(websiteURL);
		setSector(sector);
		setIsActief(true);
	}

	@Override
	public IntegerProperty aantalOpenstaandeBestellingenInkomendProperty() {
		int aantal = (int) this.getInkomendeBestellingen().stream()
				.filter(b -> b.getBetaalStatus() != BetaalStatusEnum.BETAALD).count();
		this.setAantalOpenstaandeBestellingenInkomend(aantal);
		return this.aantalOpenstaandeBestellingenInkomend;
	}

	@Override
	public IntegerProperty aantalOpenstaandeBestellingenUitgaandProperty(UUID id) {
		int aantal = (int) this.getUitgaandeBestellingen().stream()
				.filter(b -> b.getLeverancier().getUuid() == id && b.getBetaalStatus() != BetaalStatusEnum.BETAALD)
				.count();
		this.setAantalOpenstaandeBestellingenUitgaand(aantal);
		return this.aantalOpenstaandeBestellingenUitgaand;
	}

	public void addInkomendeBestelling(B2BBestelling bestelling) {
		this.inkomendeBestellingen.add(bestelling);
	}

	public void addKlantAccount(Klant klant) {
		this.klantAccounts.add(klant);
	}

	public void addLeverancierAccount(Leverancier leverancier) {
		this.leverancierAccounts.add(leverancier);
	}

	public void addUitgaandeBestelling(B2BBestelling bestelling) {
		this.uitgaandeBestellingen.add(bestelling);
	}

	@Override
	public StringProperty adresProperty() {
		return this.adres.adresStringProperty();
	}

	@Override
	public StringProperty emailProperty() {
		return this.email;
	}

	@Override
	public int getAantalKlanten() {
		return 0;
	}

	@Override
	public AdresInterface getAdres() {
		return this.adres;
	}

	@Override
	public StringProperty getAdresProperty() {
		System.out.print(this.adresProperty());
		System.out.println(this.adres.adresStringProperty());
		return this.adres.adresStringProperty();
	}

	public List<Bedrijf> getAlleKlanten() {
		List<Bedrijf> klanten = this.getInkomendeBestellingen().stream().map(el -> el.getKlant()).distinct().toList();
		return klanten;
	}

	@Override
	public String getEmail() {
		return email.get();
	}

	public long getId() {
		return this.bedrijfId;
	}

	@Override
	public ObservableList<Klant> getKlantAccounts() {
		return FXCollections.observableArrayList(klantAccounts);
	}

	@Override
	public ObservableList<Leverancier> getLeverancierAccounts() {
		return FXCollections.observableArrayList(leverancierAccounts);
	}

	public B2BBestelling getInkomendeBestellingByUuid(UUID bestellingUuid) {
		B2BBestelling bestelling = this.inkomendeBestellingen.stream().filter(el -> el.getUuid().equals(bestellingUuid))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Geen inkomende bestelling met dit bestellingId"));
		return bestelling;
	}

	public List<B2BBestelling> getInkomendeBestellingen() {
		return Collections.unmodifiableList(inkomendeBestellingen);
	}

	public boolean getIsActief() {
		return this.isActief.getValue();
	}

	@Override
	public StringProperty getKortAdres() {
		String str = String.format("%s %d, %s", adres.getStraat(), adres.getHuisnummer(), adres.getGemeente());
		setKortAdresPropery(str);
		return kortAdresProperty;
	}

	@Override
	public String getNaam() {
		return naam.get();
	}

	@Override
	public BedrijfSectorEnum getSector() {
		return sectorProperty().get();
	}

	@Override
	public String getTelefoonnummer() {
		return telefoonnummer.get();
	}

	@Override
	public List<Bestelling> getUitgaandeBestellingen() {
		return Collections.unmodifiableList(uitgaandeBestellingen);
	}

	@Override
	public String getWebsiteURL() {
		return this.websiteURL;
	}

	@Override
	public BooleanProperty isActiefProperty() {
		return this.isActief;
	}

	@Override
	public boolean isInFilter(B2BBedrijfDTO filter) {

//		if (filter.search != null) {
//			return filter.isSearchContained(this);
//		}
//		if (filter.naam != null) {
//			return filter.isNaamContained(this);
//		}
//		if (filter.kortAdres != null) {
//			return filter.isAdresContained(this);
//		}
//		if (filter.aantalOpenstaandeBestellingen != null) {
//			return filter.isAantalOpenstaandeBestellingenContained(this);
//		}
//		if (filter.sector != null) {
//			return filter.isSectorContained(this);
//		}

		return true;

	}

	@Override
	public boolean isInSearch(String query) {
		String lowerCaseQuery = query.toLowerCase();
		if (this.getNaam().toLowerCase().contains(lowerCaseQuery)
				|| (this.getIsActief() && "TRUE".toLowerCase().contains(lowerCaseQuery)))
			return true;
		if (!this.getIsActief() && "FALSE".toLowerCase().contains(lowerCaseQuery))
			return true;
		if (this.getEmail().toLowerCase().contains(lowerCaseQuery))
			return true;
		if (this.getTelefoonnummer().toLowerCase().contains(lowerCaseQuery))
			return true;
		if (this.getAdres().isInSearch(lowerCaseQuery))
			return true;

		return false;
	}

	@Override
	public StringProperty kortAdresProperty() {
		return kortAdresProperty;
	}

	private String convertEmail(String email, String rol) {
		String[] delen = email.split("@");
		delen[0] = delen[0] + rol;
		return String.format("%s@%s", delen[0], delen[1]);
	}

	public void maakAccounts() throws RegistreerGebruikerException {
		try {
			klantAccounts.add(GebruikerFactory.maakKlant(this.getNaam().replace(" ", "_") + "_Klant",
					this.getNaam().replace(" ", "_") + "WebWachtwoord", convertEmail(this.getEmail(), "Klant"), this));
			leverancierAccounts.add(GebruikerFactory.maakLeverancier(this.getNaam().replace(" ", "_") + "_Leverancier",
					this.getNaam().replace(" ", "_") + "PortaalWachtwoord", convertEmail(this.getEmail(), "Leverancier"), this));
		} catch (RegistreerGebruikerException rge) {
			throw rge;
		}
	}

	@Override
	public StringProperty naamProperty() {
		return this.naam;
	}

	@Override
	public ObjectProperty<BedrijfSectorEnum> sectorProperty() {
		return this.sector;
	}

	private void setAantalOpenstaandeBestellingenUitgaand(int aantal) {
		this.aantalOpenstaandeBestellingenUitgaand.set(aantal);
	}

	private void setAantalOpenstaandeBestellingenInkomend(int aantal) {
		this.aantalOpenstaandeBestellingenInkomend.set(aantal);
	}

	private void setAdres(Adres adres) {
		if (adres == null)
			throw new IllegalArgumentException("Adres mag niet leeg zijn");
		this.adres = adres;
	}

	private void setEmail(String email) {
		if (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
			throw new IllegalArgumentException("Ongeldig e-mailadres.");
		}
		this.email.set(email);
	}

	public void setIsActief(boolean isActief) {
		this.isActief.set(isActief);
	}

	private void setKortAdresPropery(String adres) {
		kortAdresProperty.set(adres);
	}

	public void setNaam(String naam) {
		if (naam.length() < 2 || naam.length() > 40) {
			throw new IllegalArgumentException(
					"Een geldige naam moet minstens 2 tekens bevatten en mag maximaal 40 tekens hebben.");
		}
		if (!naam.matches(".*[a-zA-Z].*")) {
			throw new IllegalArgumentException("Een geldige naam moet minstens 1 letter bevatten.");
		}
		if (naam.matches(".*[^a-zA-Z0-9]{3,}.*")) {
			throw new IllegalArgumentException(
					"Er mogen niet meer dan 2 opeenvolgende speciale tekens in de naam zijn.");
		}
		this.naam.set(naam);
	}

	public void setSector(BedrijfSectorEnum sector) {
		if (sector == null)
			throw new IllegalArgumentException("Er moet een sector worden meegegeven");
		this.sector.set(sector);
	}

	private void setTelefoonnummer(String telnr) {
		if (!telnr.startsWith("+32 4") && !telnr.startsWith("+324")) {
			throw new IllegalArgumentException("Het telefoonnummer moet beginnen met '+32 4...'");
		}
		if (telnr.length() < 12 || telnr.length() > 16) {
			throw new IllegalArgumentException("Het telefoonnummer moet minimaal 12 tekens bevatten.");
		}
		if (!telnr.matches("\\+\\d+([\\s\\-]?\\d+)+")) {
			throw new IllegalArgumentException("Het telefoonnummer mag alleen cijfers en spaties bevatten.");
		}
		this.telefoonnummer.set(telnr);
	}

	private void setWebsiteURL(String urlString) {
		if (urlString == null || urlString.isBlank()) {
			this.websiteURL = "";
			return;
		}

		try {
			URL url = new URL(urlString);
		} catch (Exception e) {
			throw new IllegalArgumentException(urlString + " is an invalid URL");
		}

		this.websiteURL = urlString;

	}

	@Override
	public StringProperty telefoonnummerProperty() {
		return this.telefoonnummer;
	}

	public void wijzigIsActief() {
		this.setIsActief(!this.getIsActief());
	}

	public void wijzigSector(BedrijfSectorEnum sector) {
		this.setSector(sector);
	}

	@Override
	public UUID getUuid() {
		return this.uuidValue;
	}
	
	public void setBtwNr(String btw) {
		this.BTWNr = btw;
	}

	@Override
	public String getBtw() {
		return this.BTWNr;
	}

}
