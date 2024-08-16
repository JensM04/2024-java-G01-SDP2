package repository;

import java.util.List;

import domain.B2BBedrijf;
import domain.Product;

public interface ProductDao extends GenericDao<Product>{
	public List<Product> getProductenByBedrijf(B2BBedrijf bedrijf);
	public void addProduct(Product product);
}
