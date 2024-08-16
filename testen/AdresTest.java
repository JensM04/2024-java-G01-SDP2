package testen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import domain.Adres;

class AdresTest {

	// Belgische postcode moet positief zijn en tussen 1000-9999 liggen
	@ParameterizedTest
	@ValueSource(ints = { 1000, 2000, 3000, 4000, 5000, 6000, 7000, 1000 })
	public void nieuwAdresMetGeldigePostcode(int postcode) {
		Assertions.assertDoesNotThrow(() -> new Adres("Geldigestraat", 12, "A", postcode, "Gent"));
	}

	// Belgische postcode moet positief zijn en tussen 1000-9999 liggen
	@ParameterizedTest
	@ValueSource(ints = { -2000, -100, -5000, 0, 100, 999, 10000, 15000 })
	public void nieuwAdresMetOngeldigePostcode(int postcode) {
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> new Adres("Geldigestraat", 12, "A", postcode, "Gent"));
	}

	// Een straat in België moet minstens 2 tekens lang zijn, en kan niet langer
	// zijn dan 60 tekens
	@ParameterizedTest
	@ValueSource(strings = { "Geldigestraat", "Zavelstraat", "Reepstraat", "Stationstraat", "Schoolstraat",
			"Grote Markt" })
	public void nieuwAdresMetGeldigeStraat(String straat) {
		Assertions.assertDoesNotThrow(() -> new Adres(straat, 12, "A", 9000, "Gent"));
	}

	// Een straat in België moet minstens 2 tekens lang zijn, max 60 tekens lang en
	// geen cijfers bevatten
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { " ", "D", "Straat1", "TeLangeStraatnaamDieNietAanDeVoorwaardenVoldoet12345", "Straat2",
			"67980", "Str44t" })
	public void nieuwAdresMetOngeldigeStraat(String straat) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Adres(straat, 12, "A", 9000, "Gent"));
	}

	// Een geldige busnummer kan max lengte 4 hebben, en mag max 1 letter bevatten
	@ParameterizedTest
	@ValueSource(strings = { "123", "1A", "999", "A", "123A" })
	public void testGeldigeBusNummers(String busNummer) {
		Assertions.assertDoesNotThrow(() -> new Adres("Geldigestraat", 12, busNummer, 9000, "Gent"));
	}

	// Een geldige busnummer kan max lengte 4 hebben, en mag max 1 letter bevatten
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { "A3A13", "ABSQ2", "1B2C3", "12345", "ABCD2" })
	public void testOngeldigeBusNummers(String busNummer) {
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> new Adres("Geldigestraat", 12, busNummer, 9000, "Gent"));
	}

	// Een geldige huisnummer kan niet negatief, 0 of groter dan 9999 zijn
	@ParameterizedTest
	@ValueSource(ints = { 1, 12, 24, 287, 35, 300, 943 })
	public void testGeldigeHuisnummers(int huisNummer) {
		Assertions.assertDoesNotThrow(() -> new Adres("Geldigestraat", huisNummer, "A", 9000, "Gent"));
	}

	// Een geldige huisnummer kan niet negatief, 0 of groter dan 9999 zijn
	@ParameterizedTest
	@ValueSource(ints = { -1, -12, 0, -3, -35, -300, -943 })
	public void testOngeldigeHuisnummers(int huisNummer) {
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> new Adres("Geldigestraat", huisNummer, "A", 9000, "Gent"));
	}

	// Een geldige gemeente in België heeft min 2 tekens, max 26 tekens, en geen
	// cijfers
	@ParameterizedTest
	@ValueSource(strings = { "Gent", "Brugge", "Sint-Niklaas", "Lokeren", "Antwerpen", "Brussel" })
	public void testGeldigeGemeentes(String gemeente) {
		Assertions.assertDoesNotThrow(() -> new Adres("Geldigestraat", 12, "A", 9000, gemeente));
	}

	// Een geldige gemeente in België heeft min 2 tekens, max 26 tekens, en geen
	// cijfers
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { "G3nt", "Brugg3", "S", " ", "DitIsEenTeLangeNaamVoorEenGemeenteEnIsDusOngeldig",
			"9Brus0sel0" })
	public void testOngeldigeGemeentes(String gemeente) {
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> new Adres("Geldigestraat", 12, "A", 9000, gemeente));
	}

}
