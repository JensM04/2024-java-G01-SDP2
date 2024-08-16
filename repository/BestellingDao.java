package repository;

import java.util.List;

import domain.B2BBestelling;
import domain.Bedrijf;
import domain.Leverancier;

public interface BestellingDao extends GenericDao<B2BBestelling>{
	public List<B2BBestelling> findAllLeverancier(Leverancier leverancier);

	public List<B2BBestelling> findAllBedrijf(Bedrijf bedrijf);

	public List<B2BBestelling> findAll(Bedrijf bedrijf);
}
