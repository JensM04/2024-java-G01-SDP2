package repository;

import java.util.List;

import domain.B2BBestelling;
import domain.Bedrijf;
import domain.Leverancier;

public class BestellingDaoJpa extends GenericDaoJpa<B2BBestelling> implements BestellingDao{

	public BestellingDaoJpa() {
		super(B2BBestelling.class);
	}

	@Override
	public List<B2BBestelling> findAllLeverancier(Leverancier leverancier){
		return em.createNamedQuery("B2BBestelling.findAll", B2BBestelling.class).setParameter("leverancier", leverancier).getResultList();
	}

	@Override
	public B2BBestelling get(Long id) {
		return em.createNamedQuery("B2BBestelling.findById", B2BBestelling.class).setParameter("bId", id).getSingleResult();
	}


	@Override
	public List<B2BBestelling> findAllBedrijf(Bedrijf bedrijf) {
		return em.createNamedQuery("B2BBestelling.findByBedrijf", B2BBestelling.class).setParameter("bedrijf", bedrijf).getResultList();
	}

	@Override
	public List<B2BBestelling> findAll(Bedrijf bedrijf) {
		return em.createNamedQuery("B2BBestelling.findEvery", B2BBestelling.class).getResultList();
	}
}
