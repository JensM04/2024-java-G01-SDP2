package domain;

public class AccountInfoMail {

	public AccountInfoMail(Bedrijf bedrijf) {
		if (bedrijf == null) {
			throw new IllegalArgumentException("Er moet een bedrijf meegegeven worden!");
		}
		stuurAccountInfo(bedrijf);
	}

	private void stuurAccountInfo(Bedrijf bedrijf) {
		String levUser = String.format("%s_Leverancier", bedrijf.getNaam().replace(" ", "_"));
		String levWw = String.format("%sPortaalWachtwoord", bedrijf.getNaam().replace(" ", "_"));
		String klantUser = String.format("%s_Klant", bedrijf.getNaam().replace(" ", "_"));
		String klantWw = String.format("%sWebWachtwoord", bedrijf.getNaam().replace(" ", "_"));

		String subject = "Accountinfo B2B Portaal";

		String content = String.format("<h3>Welkom %s</h3>\r\n"
				+ "    <p>Bedankt voor het gebruiken van ons platform!</p>\r\n"
				+ "    <p>Om in te loggen heeft u volgende gegevens nodig:</p>\r\n"
				+ "    <table style=\"border: 1px solid black; border-collapse: collapse\">\r\n"
				+ "      <thead style=\"border: 1px solid black; border-collapse: collapse\">\r\n"
				+ "        <tr style=\"border: 1px solid black; border-collapse: collapse\">\r\n"
				+ "          <th style=\"border: 1px solid black; border-collapse: collapse\"></th>\r\n"
				+ "          <th style=\"border: 1px solid black; border-collapse: collapse\">\r\n"
				+ "            Gebruikersnaam\r\n" + "          </th>\r\n"
				+ "          <th style=\"border: 1px solid black; border-collapse: collapse\">\r\n"
				+ "            Wachtwoord\r\n" + "          </th>\r\n" + "        </tr>\r\n" + "      </thead>\r\n"
				+ "      <tbody style=\"border: 1px solid black; border-collapse: collapse\">\r\n"
				+ "        <tr style=\"border: 1px solid black; border-collapse: collapse\">\r\n"
				+ "          <td style=\"border: 1px solid black; border-collapse: collapse\">\r\n"
				+ "            Leverancieraccount - Desktop applicatie\r\n" + "          </td>\r\n"
				+ "          <td style=\"border: 1px solid black; border-collapse: collapse\">\r\n"
				+ "            %s\r\n" + "          </td>\r\n"
				+ "          <td style=\"border: 1px solid black; border-collapse: collapse\">\r\n"
				+ "            %s\r\n" + "          </td>\r\n" + "        </tr>\r\n" + "        <tr>\r\n"
				+ "          <td style=\"border: 1px solid black; border-collapse: collapse\">\r\n"
				+ "            Klantaccount - Web applicatie\r\n" + "          </td>\r\n"
				+ "          <td style=\"border: 1px solid black; border-collapse: collapse\">\r\n"
				+ "            %s\r\n" + "          </td>\r\n"
				+ "          <td style=\"border: 1px solid black; border-collapse: collapse\">\r\n"
				+ "            %s\r\n" + "          </td>\r\n" + "        </tr>\r\n" + "      </tbody>\r\n"
				+ "    </table>", bedrijf.getNaam(), levUser, levWw, klantUser, klantWw);

		String receiver = bedrijf.getEmail();

		MailSender.stuurEmail(content, subject, receiver);

	}
}
