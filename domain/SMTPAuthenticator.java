package domain;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SMTPAuthenticator extends Authenticator{
	private static final String SMTP_AUTH_EMAIL = "spd2.g01@gmail.com";
	private static final String SMTP_AUTH_PASSWORD = "mxjz mqvs cbds auqw";

	@Override
	public PasswordAuthentication getPasswordAuthentication() {
		String username = SMTP_AUTH_EMAIL;
		String password = SMTP_AUTH_PASSWORD;

		return new PasswordAuthentication(username, password);
	}
}
