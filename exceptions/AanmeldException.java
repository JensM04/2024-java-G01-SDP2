package exceptions;

public class AanmeldException extends Exception {

	private static final long serialVersionUID = 1L;

	public AanmeldException() {
		super("De combinatie van gebruikersnaam en wachtwoord is niet correct");
	}

	public AanmeldException(String message) {
		super(message);
	}



}
