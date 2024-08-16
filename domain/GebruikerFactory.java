package domain;

import dto.GebruikerDTO;
import exceptions.RegistreerGebruikerException;
import utils.GebruikerRolEnum;

public class GebruikerFactory {

	public static Gebruiker maakGebruiker(GebruikerDTO gebruiker, Bedrijf bedrijf) throws RegistreerGebruikerException {
		if(gebruiker.rol.equals(GebruikerRolEnum.LEVERANCIER))
			return new Leverancier(gebruiker, (B2BBedrijf) bedrijf);
		if(gebruiker.rol.equals(GebruikerRolEnum.KLANT))
			return new Klant(gebruiker, (B2BBedrijf) bedrijf);
		return null;
	}

	public static Klant maakKlant(String gebruikersnaam, String wachtwoord, String email, B2BBedrijf bedrijf) throws RegistreerGebruikerException {
		GebruikerDTO klant = new GebruikerDTO();
		klant.naam = gebruikersnaam;
		klant.wachtwoord = wachtwoord;
		klant.email = email;
		klant.rol = GebruikerRolEnum.KLANT;
		return new Klant(klant, bedrijf);
	}

	public static Leverancier maakLeverancier(String gebruikersnaam, String wachtwoord, String email, B2BBedrijf bedrijf) throws RegistreerGebruikerException {
		GebruikerDTO leverancier = new GebruikerDTO();
		leverancier.naam = gebruikersnaam;
		leverancier.wachtwoord = wachtwoord;
		leverancier.rol = GebruikerRolEnum.LEVERANCIER;
		leverancier.email = email;
		return new Leverancier(leverancier, bedrijf);
	}

	public static Administrator maakAdministrator(String gebruikersnaam, String wachtwoord, String email) throws RegistreerGebruikerException {
		GebruikerDTO admin = new GebruikerDTO();
		admin.naam = gebruikersnaam;
		admin.wachtwoord = wachtwoord;
		admin.email = email;
		admin.rol = GebruikerRolEnum.ADMINISTRATOR;
		return new Administrator(admin);
	}

}
