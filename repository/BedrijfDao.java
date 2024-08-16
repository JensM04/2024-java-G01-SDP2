package repository;

import java.util.List;

import domain.B2BBedrijf;
import domain.Leverancier;

public interface BedrijfDao extends GenericDao<B2BBedrijf> {
	public List<B2BBedrijf> findByBestelling(Leverancier leverancier);
}
