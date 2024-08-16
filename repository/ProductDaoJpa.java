package repository;

import java.util.List;

import domain.B2BBedrijf;
import domain.Product;

public class ProductDaoJpa extends GenericDaoJpa<Product> implements ProductDao {
	public ProductDaoJpa() {
		super(Product.class);
	}

	@Override
	public List<Product> getProductenByBedrijf(B2BBedrijf bedrijf) {
		return em.createNamedQuery("Product.getByBedrijf", Product.class).setParameter("bedrijf", bedrijf).getResultList();
	}

	@Override
	public void addProduct(Product product) {
		em.persist(product);
	}

}
