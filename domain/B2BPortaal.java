package domain;

import java.util.List;

import exceptions.RegistreerGebruikerException;
import jakarta.persistence.EntityManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import repository.BedrijfDao;
import repository.BedrijfDaoJpa;
import repository.GebruikerDao;
import repository.GebruikerDaoJpa;

public class B2BPortaal {

	private EntityManager entityManager;
	private BedrijfDao bedrijfDao;
	private ObservableList<B2BBedrijf> bedrijven = FXCollections.observableArrayList();
	private GebruikerDao gebruikerDao;
	private List<Gebruiker> gebruikers;

	public B2BPortaal(BedrijfDao bedrijfDao, GebruikerDao gebruikerDao) {
		this.bedrijfDao = bedrijfDao;
		this.bedrijven.addAll(bedrijfDao.findAll());
		this.gebruikerDao = gebruikerDao;
		this.gebruikers = this.gebruikerDao.findAll();
	}

	public B2BPortaal() {
		this(new BedrijfDaoJpa(), new GebruikerDaoJpa());
	}

	public ObservableList<? extends Bedrijf> getAlleBedrijven() {
		return FXCollections.unmodifiableObservableList(this.bedrijven);
	}

	public void addBedrijf(B2BBedrijf bedrijf) {
		this.bedrijven.add(bedrijf);
		try {
			bedrijf.maakAccounts();
			this.bedrijfDao.insert(bedrijf);
		}catch(RegistreerGebruikerException rge) {
			System.err.print(rge.getMessage());
		}
		AccountInfoMail infoMail = new AccountInfoMail(bedrijf);
	}

	public B2BBedrijf getBedrijfById(long id) {
		return bedrijven.stream().filter(el -> el.getId() == id).findFirst().orElse(null);
	}

	public void deleteBedrijfById(long id) {
		B2BBedrijf bedrijf = this.bedrijfDao.get(id);
		this.bedrijfDao.delete(bedrijf);

	}

	public void addGebruiker(Gebruiker gebruiker) {
		this.gebruikerDao.insert(gebruiker);
	}
	
	public void wijzigWachtwoord(Gebruiker gebruiker, String nieuwWachtwoord, String bevestigingWachtwoord) {
		if (!nieuwWachtwoord.equals(bevestigingWachtwoord)) {
			throw new IllegalArgumentException("De wachtwoorden komen niet overeen");
		}
		gebruiker.wijzigWachtwoord(nieuwWachtwoord);
	}
}
