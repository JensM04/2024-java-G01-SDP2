package repository;

import java.util.List;

import domain.B2BBestelling;
import domain.Bedrijf;
import domain.Betaling;

public class BetalingDaoJpa  extends GenericDaoJpa<Betaling> implements BetalingDao{

	public BetalingDaoJpa() {
		super(Betaling.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Betaling> findByBestelling(B2BBestelling huidigeBestelling) {
		// TODO Auto-generated method stub
		return em.createNamedQuery("Betaling.findByBestelling", Betaling.class).setParameter("bestelling", huidigeBestelling).getResultList();
	}

	@Override
	public List<Betaling> findByBedrijf(Bedrijf b2bBedrijf) {
		// TODO Auto-generated method stub
		return em.createNamedQuery("Betaling.findByBedrijf", Betaling.class).setParameter("bedrijf", b2bBedrijf).getResultList();
	}

}
