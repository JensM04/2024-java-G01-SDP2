package repository;

import domain.Administrator;
import domain.Gebruiker;
import domain.Leverancier;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import utils.GebruikerRolEnum;

public class GebruikerDaoJpa extends GenericDaoJpa<Gebruiker> implements GebruikerDao {

	public GebruikerDaoJpa() {
		super(Gebruiker.class);
	}

	// Geen named query, want dit geeft errors bij het runnen (heeft iets te maken met inheritance)
	@Override
	public Leverancier zoekLeverancier(String gebruikersnaam) {
		try {
			return em.createQuery("SELECT l FROM Gebruiker l WHERE l.gebruikersnaam = :gebruikersnaam AND l.rol = :rol", Leverancier.class).setParameter("gebruikersnaam", gebruikersnaam).setParameter("rol", GebruikerRolEnum.LEVERANCIER).getSingleResult();
		} catch (NoResultException ex) {
			throw new EntityNotFoundException();
		}
	}



	@Override
	public Administrator zoekAdmin(String gebruikersnaam) {
		try {
			return em.createQuery("SELECT l FROM Gebruiker l WHERE l.gebruikersnaam = :gebruikersnaam AND l.rol = :rol", Administrator.class).setParameter("gebruikersnaam", gebruikersnaam).setParameter("rol", GebruikerRolEnum.ADMINISTRATOR).getSingleResult();
		} catch (NoResultException ex) {
			throw new EntityNotFoundException();
		}
	}

	@Override
	public Leverancier zoekLeverancierVanBedrijf(int id) {
		try {
			return null; //em.createQuery("SELECT l FROM Leverancier l WHERE l.bedrijf.bedrijfId = :id", Leverancier.class).setParameter("id", id).getSingleResult();
		} catch (NoResultException ex) {
			throw new EntityNotFoundException();
		}
	}

}