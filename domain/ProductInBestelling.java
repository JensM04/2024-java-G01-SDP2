package domain;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ProductInBestelling implements ProductInBestellingInterface, Serializable {

	private static final long serialVersionUID = -5357571513880838346L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@ManyToOne
    @JoinColumn(name = "PRODUCTID")
    private Product product;
	private int aantal;

	@ManyToOne
    private B2BBestelling b2bBestelling;

	public ProductInBestelling(Product product, int aantal, B2BBestelling b2bBestelling) {
		setAantal(aantal);
		setProduct(product);
		setB2bBestelling(b2bBestelling);
	}

	public ProductInBestelling(Product product, int aantal) {
		setAantal(aantal);
		setProduct(product);
	}

	protected ProductInBestelling() {

	}

	private void setAantal(int aantal) {
		if(aantal <= 0) {
			throw new IllegalArgumentException("Aantal producten moet positief en verschillend van 0 zijn!");
		}
		this.aantal = aantal;
	}

	private void setProduct(Product product) {
		if(product == null) {
			throw new IllegalArgumentException("Product kan niet leeg zijn!");
		}
		this.product = product;
	}

	public void setB2bBestelling(B2BBestelling b2b) {
		this.b2bBestelling = b2b;
	}

	@Override
	public BigDecimal getTotalePrijs() {
		return product.getEenheidsprijs().multiply(BigDecimal.valueOf(aantal));
	}

	@Override
	public String getNaam() {
		return product.getNaam();
	}

	@Override
	public int getAantal() {
		return aantal;
	}

	@Override
	public boolean getIsInStock() {
		return product.isInStock();
	}

	@Override
	public BigDecimal getEenheidsprijs() {
		return product.getEenheidsprijs();
	}

	public void plaatsBesteldAantalInVoorraad() {
		this.product.setAantalInStock(this.product.getAantalInStock() + this.aantal);
	}

	public void haalBesteldAantalUitVoorraad() {
		this.product.setAantalInStock(this.product.getAantalInStock() - this.aantal);
	}
}
