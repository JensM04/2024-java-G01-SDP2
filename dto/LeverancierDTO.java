package dto;

import java.util.List;

import domain.B2BBestelling;

public class LeverancierDTO {

	public List<B2BBestelling> bestellingen;
	public B2BBestelling huidigeBestelling;
	public int id;

	public String naam;
	public String wachtwoord;
}
