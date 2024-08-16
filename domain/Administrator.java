package domain;

import dto.GebruikerDTO;
import exceptions.RegistreerGebruikerException;
import jakarta.persistence.Entity;

@Entity
public class Administrator extends Gebruiker {

	private static final long serialVersionUID = 1L;

	protected Administrator() {
		super();
	}

	public Administrator(GebruikerDTO gebruikerDTO) throws RegistreerGebruikerException {
		super(gebruikerDTO);
		this.isWachtwoordVeranderd = true;
	}
}
