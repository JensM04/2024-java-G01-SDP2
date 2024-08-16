package domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


public class BetalingsHerinnering {

	private LocalDateTime datumGestuurd;
	private static final int aantalDagenOmTeBetalen = 30;

	public LocalDateTime getDatumGestuurd() {
		return this.datumGestuurd;
	}

	public void setDatumGestuurd(LocalDateTime datumGestuurd) {
		this.datumGestuurd = datumGestuurd;
	}


	public BetalingsHerinnering(Bestelling bestelling) {
		if (bestelling == null) {
			throw new IllegalArgumentException("Bestelling mag niet null zijn");
		}

		setDatumGestuurd(LocalDateTime.now());
		stuurBetalingsHerinnering(bestelling);

	}

	private void stuurBetalingsHerinnering(Bestelling bestelling) {
		long days = ChronoUnit.DAYS.between(LocalDateTime.now(), bestelling.getBestellingDatum().plusDays(aantalDagenOmTeBetalen));

		String content = String.format("<h1>U heeft nog %d dagen om bestelling %s te betalen</h1> <br/> Bedrag excl. BTW: %.2f euro <br/> LeveringsStatus: %s <br/> Datum van bestelling: %s",
				days, bestelling.getUuid(), bestelling.getTotaalBestellingbedragExclBTW(), bestelling.getBestellingStatus().toString(), bestelling.getBestellingDatum());

		String subject = "Betalingsherinnering bestelling " + bestelling.getUuid();

		String receiver = bestelling.getKlant().getEmail();

		MailSender.stuurEmail(content, subject, receiver);
	}

}



