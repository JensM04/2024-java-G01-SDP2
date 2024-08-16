package dto;

import utils.GebruikerRolEnum;

public class GebruikerDTO {

	public String naam;
	public String wachtwoord;
	public GebruikerRolEnum rol;
	public String email;

	public boolean hasError = false;
	public String naamError;
	public String emailError;
	public String wachtwoordError;
	public String rolError;
}
