package domain;

import java.io.Serializable;
import java.util.List;

import dto.B2BBedrijfDTO;
import dto.GebruikerDTO;
import exceptions.RegistreerGebruikerException;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import javafx.beans.property.SimpleStringProperty;

@Entity
public class Klant extends Gebruiker implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinTable
    private B2BBedrijf bedrijf;

	protected Klant() {
	}
	
	public Klant(GebruikerDTO gebruiker, B2BBedrijf bedrijf) throws RegistreerGebruikerException {
		super(gebruiker);
		if(bedrijf == null)
			throw new IllegalArgumentException("Bedrijf mag niet leeg zijn");
		setBedrijf(bedrijf);
	}

	private void setBedrijf(B2BBedrijf bedrijf) {
		this.bedrijf = bedrijf;
		this.bedrijf.addKlantAccount(this);
	}
}
