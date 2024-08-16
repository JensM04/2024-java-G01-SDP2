package repository;

import java.util.List;

import domain.B2BBedrijf;
import domain.Leverancier;

public class BedrijfDaoJpa extends GenericDaoJpa<B2BBedrijf> implements BedrijfDao {

	public BedrijfDaoJpa() {
		super(B2BBedrijf.class);
	}

	@Override
	public List<B2BBedrijf> findAll() {
		return em.createNamedQuery("B2BBedrijf.findAll", B2BBedrijf.class).getResultList();
	}

	public List<B2BBedrijf> findByBestelling(Leverancier leverancier){
		return em.createNamedQuery("B2BBedrijf.findByBestelling", B2BBedrijf.class).setParameter("leverancier", leverancier).getResultList();
	}
}
