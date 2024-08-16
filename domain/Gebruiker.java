package domain;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import dto.GebruikerDTO;
import exceptions.RegistreerGebruikerException;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import javafx.beans.property.SimpleStringProperty;
import utils.GebruikerRolEnum;
import utils.GebruikerRolEnumConverter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Gebruiker implements Serializable {

	private static final long serialVersionUID = -581613090199867509L;
	
	@Convert(converter = GebruikerRolEnumConverter.class)
	@Enumerated(EnumType.STRING)
	private GebruikerRolEnum rol;
	
	private String email;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	protected boolean isWachtwoordVeranderd;
	private String gebruikersnaam;
	private String wachtwoord_hash;
	private String salt;

	public Gebruiker(String naam, String wachtwoord, GebruikerRolEnum rol, String email)
			throws RegistreerGebruikerException {
		setRol(rol);
		setGebruikersnaam(naam);
		setWachtwoord(wachtwoord);
		setEmail(email);
		this.isWachtwoordVeranderd = false;
	}

	protected Gebruiker() {
	}

	private void setEmail(String email) {
		if (email == null)
			throw new IllegalArgumentException("Email adres werd niet meegegeven");

		String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(email);
		if (!matcher.matches())
			throw new IllegalArgumentException("Ongeldig emailadres");

		this.email = email;
	}

	private void setRol(GebruikerRolEnum rol) {
		if (rol == null)
			throw new IllegalArgumentException("Er werd geen rol meegegeven");
		this.rol = rol;
	}

	public Gebruiker(GebruikerDTO gebruiker) throws RegistreerGebruikerException {
		Deque<String> errors = new ArrayDeque<>();
		try {
			setGebruikersnaam(gebruiker.naam);
		} catch (IllegalArgumentException ex) {
			gebruiker.naamError = ex.getMessage();
			errors.add(ex.getMessage());
			gebruiker.hasError = true;
		}
		try {
			setEmail(gebruiker.email);
		} catch (IllegalArgumentException ex) {
			gebruiker.emailError = ex.getMessage();
			errors.add(ex.getMessage());
			gebruiker.hasError = true;
		}
		try {
			setWachtwoord(gebruiker.wachtwoord);
		} catch (IllegalArgumentException ex) {
			gebruiker.wachtwoordError = ex.getMessage();
			errors.add(ex.getMessage());
			gebruiker.hasError = true;
		}
		try {
			setRol(gebruiker.rol);
		} catch (IllegalArgumentException ex) {
			gebruiker.rolError = ex.getMessage();
			errors.add(ex.getMessage());
			gebruiker.hasError = true;
		}
		if(gebruiker.hasError)
			throw new RegistreerGebruikerException("Er ging iets mis bij het registreren\n" + errors.stream().collect(Collectors.joining("\n")));
	}



	public String getGebruikersnaam() {
		return gebruikersnaam;
	}
	
	public SimpleStringProperty gebruikersnaamProperty() {
		return new SimpleStringProperty(gebruikersnaam);
	}

	public String getWachtwoord() {
		return wachtwoord_hash;
	}
	public SimpleStringProperty wachtwoordProperty() {
		return new SimpleStringProperty(wachtwoord_hash);
	}

	public String getSalt() {
		return salt;
	}

	public String getEmail() {
//		return "";
		return this.email;
	}

	private void setGebruikersnaam(String username) {
		if (username == null || username.isBlank()) {
			throw new IllegalArgumentException("Je moet een geldige gebruikersnaam opgeven");
		}
		this.gebruikersnaam = username;
	}

	private void setSalt(int length) {
		salt = Encryption.getSaltvalue(length);
	}
	
	public void wijzigWachtwoord(String nieuwWachtwoord) {
		this.setWachtwoord(nieuwWachtwoord);
		this.isWachtwoordVeranderd = true;
	}

	private void setWachtwoord(String password) {
		if (password == null || password.isBlank()) {
			throw new IllegalArgumentException("Je moet een geldig wachtwoord opgeven");
		}
		setSalt(30);
		String encrypted_wachtwoord = Encryption.generateSecurePassword(password, salt);

		this.wachtwoord_hash = encrypted_wachtwoord;
	}

	public GebruikerRolEnum getRol() {
		return rol;
	}

	public int getId() {
		return id;
	}
}
