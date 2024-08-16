package domain;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String naam;
	private int aantalInStock;
	private BigDecimal eenheidsprijs;

	public Product(String naam, BigDecimal eenheidsprijs, int aantalInStock) {
		setNaam(naam);
		setEenheidsprijs(eenheidsprijs);
		setAantalInStock(aantalInStock);
	}

	protected Product() {

	}

	public String getNaam() {
		return naam;
	}

	public BigDecimal getEenheidsprijs() {
		return eenheidsprijs;
	}

	public void setNaam(String naam) {
		if (naam == null || naam.trim().isEmpty() || naam.length() < 2 || naam.length() > 25) {
            throw new IllegalArgumentException("De productnaam moet minimaal 2, en maximaal 25 tekens hebben!");
        }
        this.naam = naam;
	}

	public void setEenheidsprijs(BigDecimal eenheidsprijs) {
		if (eenheidsprijs == null || eenheidsprijs.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("De eenheidsprijs kan niet negatief of 0 zijn!");
		}
		this.eenheidsprijs = eenheidsprijs;
	}

	public void setAantalInStock(Integer aantalInStock) {
	    if (aantalInStock == null || aantalInStock < 0) {
	        throw new IllegalArgumentException("Aantal in stock kan niet negatief zijn!");
	    }
	    this.aantalInStock = aantalInStock;
	}

	public boolean isInStock() {
		return getAantalInStock() > 0;
	}

	public void wijzigProduct(String naam, BigDecimal eenheidsprijs, int aantalInStock) {
		this.setNaam(naam);
		this.setEenheidsprijs(eenheidsprijs);
		this.setAantalInStock(aantalInStock);
	}

	public int getAantalInStock() {
		return aantalInStock;
	}

}
