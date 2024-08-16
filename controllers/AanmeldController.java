	package controllers;

import domain.Encryption;
import domain.Gebruiker;
import domain.Leverancier;
import exceptions.AanmeldException;
import jakarta.persistence.EntityNotFoundException;
import repository.GebruikerDao;
import repository.GebruikerDaoJpa;
import utils.ControllerSoortEnum;

public class AanmeldController {
	private GebruikerDao gebruikerRepo;

	public AanmeldController() {
		gebruikerRepo = new GebruikerDaoJpa();
	}

	public AanmeldController(GebruikerDao mock) {
		gebruikerRepo = mock;
	}

	public ControllerSoortEnum getControllerSoort() {
		return ControllerSoortEnum.AANMELDCONTROLLER;
	}

	public Controller meldAan(String gebruikersnaam, String wachtwoord) throws AanmeldException  {
		if (gebruikersnaam == null || gebruikersnaam.isBlank() || gebruikersnaam.isEmpty()) {
			throw new AanmeldException("Je moet een geldige gebruikersnaam opgeven");
		}
		if (wachtwoord == null || wachtwoord.isBlank() || wachtwoord.isEmpty()) {
			throw new AanmeldException("Je moet een geldig wachtwoord opgeven");
		}

		try {
			Leverancier geb = gebruikerRepo.zoekLeverancier(gebruikersnaam);

			if (!Encryption.verifyUserPassword(wachtwoord, geb.getWachtwoord(), geb.getSalt())) {
				throw new AanmeldException();
			}

			return new LeverancierController(geb);


		} catch(EntityNotFoundException ex) {

			try {
				Gebruiker geb = gebruikerRepo.zoekAdmin(gebruikersnaam);

				if (!Encryption.verifyUserPassword(wachtwoord, geb.getWachtwoord(), geb.getSalt())) {
					throw new AanmeldException();
				}

				return new AdminController();
			} catch(EntityNotFoundException exception) {
				throw new AanmeldException("Er bestaat geen gebruiker met gebruikersnaam: " + gebruikersnaam);
			}

		}

	}
}
