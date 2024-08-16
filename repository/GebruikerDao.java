package repository;

import domain.Administrator;
import domain.Gebruiker;
import domain.Leverancier;

// TODO -> naar gebruiker? kan geen 2 apparte objecten in 1 dao
public interface GebruikerDao extends GenericDao<Gebruiker> {
	public Leverancier zoekLeverancier(String gebruikersnaam);
	public Administrator zoekAdmin(String gebruikersnaam);
	public Leverancier zoekLeverancierVanBedrijf(int id);
}
