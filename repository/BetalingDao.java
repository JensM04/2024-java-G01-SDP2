package repository;

import java.util.List;

import domain.B2BBestelling;
import domain.Bedrijf;
import domain.Betaling;

public interface BetalingDao extends GenericDao<Betaling>{
	
	public List<Betaling> findByBestelling(B2BBestelling huidigeBestelling);

	public List<Betaling> findByBedrijf(Bedrijf b2bBedrijf);

}
