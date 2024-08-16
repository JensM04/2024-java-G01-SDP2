package domain;

import java.util.function.Predicate;

import dto.AdresDTO;
import exceptions.RegistreerException;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import utils.IntegerPropertyConverter;
import utils.StringPropertyConverter;

@Embeddable
public class Adres implements AdresInterface {

	@Convert(converter = StringPropertyConverter.class)
	private SimpleStringProperty straat = new SimpleStringProperty();
	@Convert(converter = IntegerPropertyConverter.class)
	private SimpleIntegerProperty huisnummer = new SimpleIntegerProperty();
	@Convert(converter = StringPropertyConverter.class)
	private SimpleStringProperty bus = new SimpleStringProperty();
	@Convert(converter = IntegerPropertyConverter.class)
	private SimpleIntegerProperty postcode = new SimpleIntegerProperty();
	@Convert(converter = StringPropertyConverter.class)
	private SimpleStringProperty gemeente = new SimpleStringProperty();
	@Transient
	private SimpleStringProperty adresString = new SimpleStringProperty();

	protected Adres() {
	}

	public Adres(String straat, int huisnummer, String bus, int postcode, String gemeente) {
		setStraat(straat);
		setHuisnummer(huisnummer);
		setBus(bus);
		setPostcode(postcode);
		setGemeente(gemeente);
	}

	public Adres(AdresDTO adres) throws RegistreerException {
		try {
			setStraat(adres.straat);

		} catch (IllegalArgumentException ex) {
			adres.hasError = true;
			adres.straatError = ex.getMessage();
		}
		try {
			setHuisnummer(adres.huisnummer);

		} catch (IllegalArgumentException ex) {
			adres.hasError = true;
			adres.huisnummerError = ex.getMessage();
		}
		try {
			setBus(adres.bus);

		} catch (IllegalArgumentException ex) {
			adres.hasError = true;
			adres.busError = ex.getMessage();
		}
		try {
			setPostcode(adres.postcode);

		} catch (IllegalArgumentException ex) {
			adres.hasError = true;
			adres.postcodeError = ex.getMessage();
		}
		try {
			setGemeente(adres.gemeente);

		} catch (IllegalArgumentException ex) {
			adres.hasError = true;
			adres.gemeenteError = ex.getMessage();
		}

		if(adres.hasError) throw new RegistreerException();
	}


	private void setAdres() {
		String adresString = String.format("%s %s%s, %s %s", this.straat, this.huisnummer, this.bus, this.postcode,
				this.gemeente);
		this.adresString = new SimpleStringProperty(adresString);
	}

	private void setStraat(String straat) {
		if (straat == null || straat.isBlank()) {
			throw new IllegalArgumentException("Geef een straat op!");
		}
		if(!straat.matches("^[a-zA-Z]{2,60}(?:-|\\s[a-zA-Z]{2,60})?$")) {
			throw new IllegalArgumentException("Geef een geldige straat op");
		}
		this.straat.set(straat);
//		this.createStringProperty();
	}

	private void setHuisnummer(int huisnummer) {
		if (huisnummer <= 0 || huisnummer > 9999) {
			throw new IllegalArgumentException("Geef een geldig huisnummer op!");
		}
		this.huisnummer.set(huisnummer);
//		this.createStringProperty();
	}

	private void setBus(String bus) {
		if (bus == null || bus.isBlank() || bus.isEmpty()) {
			throw new IllegalArgumentException("Geef een bus op!");
		}
		if (!bus.matches("\\d{0,3}(\\d|[a-z]|[A-Z])?")) {
			throw new IllegalArgumentException("Geef een geldige bus op!");
		}
		this.bus.set(bus);
//		this.createStringProperty();
	}

	private void setPostcode(int postcode) {
		if (postcode < 1000 || postcode > 9999) {
			throw new IllegalArgumentException("Geef een geldige postcode in!");
		}
		this.postcode.set(postcode);
//		this.createStringProperty();
	}

	private void setGemeente(String gemeente) {
		if (gemeente == null || gemeente.isBlank()) {
			throw new IllegalArgumentException("Geef een gemeente op!");
		}
		if (!gemeente.matches("^[a-zA-Z]{2,26}(?:-[a-zA-Z]{2,26})?$")) {
			throw new IllegalArgumentException("Geef een geldige gemeente op!");
		}
		this.gemeente.set(gemeente);
//		this.createStringProperty();
	}

	@Override
	public String getStraat() {
		return this.straat.getValue();
	}

	@Override
	public int getHuisnummer() {
		return this.huisnummer.getValue();
	}

	@Override
	public String getBus() {
		return this.bus.getValue();
	}

	@Override
	public int getPostcode() {
		return this.postcode.getValue();
	}

	@Override
	public String getGemeente() {
		return this.gemeente.getValue();
	}

	@Override
	public SimpleStringProperty straatProperty() {
		return this.straat;
	}

	@Override
	public SimpleIntegerProperty huisnummerProperty() {
		return this.huisnummer;
	}

	@Override
	public SimpleStringProperty busProperty() {
		return this.bus;
	}

	@Override
	public SimpleIntegerProperty postcodeProperty() {
		return this.postcode;
	}

	@Override
	public SimpleStringProperty gemeenteProperty() {
		return this.gemeente;
	}

	@Override
	public String getAdresString() {
		createStringProperty();
		return this.adresString.getValue();
	}

	@Override
	public SimpleStringProperty adresStringProperty() {
		return this.adresString;
	}

	@Override
	public void createStringProperty() {
		String busString = "";
		if (this.getBus() != null && !this.getBus().isEmpty()) {
			busString = " " + this.getBus();
		}
		this.adresString.set(
				String.format("%s %d%s, %d %s", getStraat(), getHuisnummer(), busString, getPostcode(), getGemeente()));
	}

	@Override
	public boolean isInSearch(String query) {
		String queryLowCase = query.toLowerCase();
		if (this.getAdresString().toLowerCase().contains(queryLowCase))
			return true;
		return false;
	}

	@Override
	public boolean isInFilter(AdresDTO filter) {
		if (!(filter.bus == null || this.getBus().toLowerCase().contains(filter.bus.toLowerCase())) || !(filter.gemeente == null || this.getGemeente().toLowerCase().contains(filter.gemeente.toLowerCase())) || !(filter.huisnummer == null
				|| Integer.toString(this.getHuisnummer()).contains(filter.huisnummer.toString())) || !(filter.postcode == null || Integer.toString(this.getPostcode()).contains(filter.postcode.toString())))
			return false;
		if (!(filter.straat == null || this.getStraat().toLowerCase().contains(filter.straat.toLowerCase())))
			return false;
		return true;
	}
}
