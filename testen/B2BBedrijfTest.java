package testen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.security.SecureRandom;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import domain.Adres;
import domain.B2BBedrijf;
import domain.B2BBestelling;
import domain.Bestelling;
import dto.B2BBedrijfDTO;
import exceptions.RegistreerException;
import utils.BedrijfSectorEnum;

/**
 *
 */
@ExtendWith(MockitoExtension.class)
public class B2BBedrijfTest {

	private static final SecureRandom R = new SecureRandom();

	// UTILITIES & HELPMETHODES
	private Adres adres;

	private B2BBedrijf maakGeldigBedrijf() {
		return new B2BBedrijf("Apple Inc.", "Apple.Inc@gmail.com", "+32 483745075", adres, "http://www.web.be",
				BedrijfSectorEnum.IT);
	}

	private B2BBedrijfDTO maakGeldigBedrijfDTO() {
		B2BBedrijfDTO dto = new B2BBedrijfDTO();
		int seed = R.nextInt(0, 10000000);
		dto.naam = "BedrijfDTO" + seed;
		dto.email = String.format("test%d@email.com", seed);
		dto.telefoonnummer = String.format("+324%08d", R.nextInt(0, 99999999));
		dto.websiteURL = String.format("https://www.testurl.test%d.com/test123/123test", seed);
		dto.sector = BedrijfSectorEnum.values()[R.nextInt(0, BedrijfSectorEnum.values().length)];
		dto.isActief = R.nextBoolean();
		return dto;
	}

	@BeforeEach
	void beforeEach() {
		adres = Mockito.mock(Adres.class);
	}

	@ParameterizedTest
	@ValueSource(strings = { "Proximus", "Apple Inc.", "Tech Data", "Orange Belguim", "SWIFT", "Avnet Europe" })
	public void nieuwBedrijfMetGeldigeNaam(String naam) {
		Assertions.assertDoesNotThrow(() -> new B2BBedrijf(naam, "geldigemail@gmail.com", "+32 463 49 30 44", adres,
				"http://www.web.be", BedrijfSectorEnum.IT));
	}

	// geldige naam: minstens 2 tekens, max 28 tekens, geen opeenvolgende speciale
	// tekens, niet meer dan 3 opeenvolgende cijfers
	@ParameterizedTest
	@ValueSource(strings = { "DezeNaamIsVeelTeLangEnIsDusGeenGeldigeBedrijfsNaam", "+ù^$^'é", "129201", " " })
	public void nieuwBedrijfMetOngeldigeNaam(String naam) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new B2BBedrijf(naam, "geldigemail@gmail.com",
				"+32 463 49 30 44", adres, "http://www.web.be", BedrijfSectorEnum.IT));
	}

	@ParameterizedTest
	@ValueSource(strings = { "geldigeEmail@gmail.com", "proximus@hotmail.com", "appleInc2@outlook.be",
			"my.mail@gmail.be" })
	public void nieuwBedrijfMetGeldigeEmail(String email) {
		Assertions.assertDoesNotThrow(
				() -> new B2BBedrijf("Proximus", email, "+32 463 49 30 44", adres, "http://www.web.be", BedrijfSectorEnum.IT));
	}

	@ParameterizedTest
	@ValueSource(strings = { "mail.com", "@proximus@gmail.#@", "12.#@34qs,@.com.gmail", " ", "@gmail.com", " " })
	public void nieuwBedrijfMetOngeldigeEmail(String email) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new B2BBedrijf("Apple Inc.", email,
				"+32 463 49 30 44", adres, "http://www.web.be", BedrijfSectorEnum.IT));
	}

	// moet beginnen met +32 4..., moet min 12 tekens hebben: +32483745075, geen
	// letters, geen speciale tekens enkel +
	@ParameterizedTest
	@ValueSource(strings = { "+32 463 49 30 44", "+32432345678", "+32456091256", "+32466889910" })
	public void nieuwBedrijfMetGeldigTelefoonnummer(String telnr) {
		Assertions.assertDoesNotThrow(() -> new B2BBedrijf("Apple Inc.", "geldigemail@gmail.com", telnr, adres,
				"http://www.web.be", BedrijfSectorEnum.IT));
	}

	@ParameterizedTest
	@ValueSource(strings = { " ", "ddl0904048", "+32 54245242", "+32 456 02 34 5 4", " ", "-235çà98" })
	public void nieuwBedrijfMetOngeldigTelefoonnummer(String telnr) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new B2BBedrijf("Apple Inc",
				"geldigemail@gmail.com", telnr, adres, "http://www.web.be", BedrijfSectorEnum.IT));
	}

	@Test
	public void testGettersGeldigBedrijf() {
		B2BBedrijf b = new B2BBedrijf("Apple Inc.", "Apple.Inc@gmail.com", "+32 483745075", adres, "http://www.web.be",
				BedrijfSectorEnum.IT);
		Assertions.assertEquals("Apple Inc.", b.getNaam());
		Assertions.assertEquals("Apple.Inc@gmail.com", b.getEmail());
		Assertions.assertEquals("+32 483745075", b.getTelefoonnummer());
	}

	// TODO willen we dat de eerste state false is? of dat een bedrijf direct als
	// actief staat?
	@Test
	public void testSetActief() {
		B2BBedrijf b = new B2BBedrijf("Apple Inc.", "Apple.Inc@gmail.com", "+32 483745075", adres, "http://www.web.be",
				BedrijfSectorEnum.IT);
//		Assertions.assertEquals(false, b.getIsActief());
		b.setIsActief(true);
		Assertions.assertEquals(true, b.getIsActief());
	}

	/**
	 * Indien een bedrijf actief is, verwachten we bij het aanroepen van de methode
	 * <code>wijzigIsActief()</code> dat dit wordt gewijzigd naar nonactief
	 * <p>
	 * Indien een bedrijf non-actief is, verwachten we dus bij het aanroepen van
	 * diezelfde methode dat het omgekeerde gebeurd.
	 *
	 * @author kevinw
	 * @param boolString
	 */
	@ParameterizedTest
	@ValueSource(strings = { "true", "false" })
	public void testWijzigIsActief_WijzigNaarOmgekeerdeBoolean_GeefOmgekeerdeBoolean(String boolString) {
		boolean initialBoolean = Boolean.getBoolean(boolString);
		B2BBedrijf b = this.maakGeldigBedrijf();
		b.setIsActief(initialBoolean);
		b.wijzigIsActief();

		assertEquals(!initialBoolean, b.getIsActief());
	}

	/**
	 * Je moet inkomende bestellingen kunnen toevoegen en die terugkrijgen, de lijst
	 * moet unmodifiable zijn
	 */
	@Test
	public void testVoegInkomendeBestellingToe_BestellingWordtToegevoegd_GeeftUnmodifiableList() {
		B2BBedrijf bedrijf = maakGeldigBedrijf();
		for (int i = 0; i < 100; i++) {
			bedrijf.addInkomendeBestelling(Mockito.mock(B2BBestelling.class));
		}
		B2BBestelling mockBestelling = Mockito.mock(B2BBestelling.class);

		List<B2BBestelling> bestellingen = bedrijf.getInkomendeBestellingen();
		// test size 100
		assertEquals(bestellingen.size(), 100, "Size bestellingen moet 100 zijn");
		// test unmodifiable
		assertThrows(UnsupportedOperationException.class, () -> bestellingen.add(mockBestelling));
		assertThrows(UnsupportedOperationException.class, () -> bestellingen.remove(0));
	}

	/**
	 * Je moet een inkomende bestelling kunnen krijgen, te zoeken op id, indien die
	 * er niet is moet je null krijgen
	 */
	@Test
	public void testgetInkomendeBestellingById_BestellingBestaat_GeeftBestelling() {
		B2BBedrijf bedrijf = maakGeldigBedrijf();
		for (int i = 0; i < 3; i++) {
			B2BBestelling mockBestelling = Mockito.mock(B2BBestelling.class);
			Mockito.when(mockBestelling.getUuid())
					.thenReturn(UUID.fromString(String.format("%dfc03087-d265-11e7-b8c6-83e29cd24f4c", i)));
			bedrijf.addInkomendeBestelling(mockBestelling);
		}
		for (int i = 0; i < 3; i++) {
			assertEquals(
					UUID.fromString(String.format("%dfc03087-d265-11e7-b8c6-83e29cd24f4c", i)), bedrijf
							.getInkomendeBestellingByUuid(
									UUID.fromString(String.format("%dfc03087-d265-11e7-b8c6-83e29cd24f4c", i)))
							.getUuid());
		}

	}

	/**
	 * Je moet een uitgaande bestelling kunnen toevoegen en die kunnen opvragen, je
	 * krijgt dan een unmodifiable list
	 */
	@Test
	public void testVoegUitgaandeBestellingToe_BestellingWordtToegevoegd_GeeftUnmodifiableList() {
		B2BBedrijf bedrijf = maakGeldigBedrijf();
		for (int i = 0; i < 100; i++) {
			bedrijf.addUitgaandeBestelling(Mockito.mock(B2BBestelling.class));
		}
		B2BBestelling mockBestelling = Mockito.mock(B2BBestelling.class);

		List<Bestelling> bestellingen = bedrijf.getUitgaandeBestellingen();
		// test size 100
		assertEquals(bestellingen.size(), 100, "Size bestellingen moet 100 zijn");
		// test unmodifiable
		assertThrows(UnsupportedOperationException.class, () -> bestellingen.add(mockBestelling));
		assertThrows(UnsupportedOperationException.class, () -> bestellingen.remove(0));
	}

	// TESTEN: URL TESTEN
	// test url is null of empty -> mag
	@ParameterizedTest
	@NullAndEmptySource
	public void testUrlInConstructor_NullEnEmpty_UrlMagNullOfEmptyZijn(String url) throws RegistreerException {
		// arrange
		Adres adres = Mockito.mock(Adres.class);
		B2BBedrijfDTO dto = maakGeldigBedrijfDTO();
		dto.websiteURL = url;
		B2BBedrijf bedrijf;

		// act
		bedrijf = new B2BBedrijf(dto, adres);

		// assert
		assertEquals(B2BBedrijf.class, bedrijf.getClass());
	}

	// test url is ongeldig
	@ParameterizedTest
	@ValueSource(strings = { "foute url", "foute_url", "www.fout.be", "www.fout.nl/test123", "htt://www.bol.com" })
	public void testUrlInConstructor_OngeldigFormaat_ThrowsRegistreerException(String testUrl) {
		// arrange
		Adres adres = Mockito.mock(Adres.class);
		B2BBedrijfDTO dto = maakGeldigBedrijfDTO();
		dto.websiteURL = testUrl;

		// act
		// assert
		assertThrows(RegistreerException.class, () -> new B2BBedrijf(dto, adres));
	}

	// test url is geldig
	@ParameterizedTest
	@ValueSource(strings = { "http://example.com", "https://www.example.org", "ftp://ftp.example.net",
			"http://192.168.1.1", "http://[2001:0db8:85a3:0000:0000:8a2e:0370:7334]",
			"http://example.com/path/to/resource", "http://example.com/path?query=1&param=2",
			"http://example.com/path#fragment", "http://subdomain.example.com", "http://example.com:8080",
			"http://user:password@example.com", "http://example.com/~username" })
	public void testUrlInConstructor_GeldigFormaat_BedrijfGemaaktEnUrlKomtOvereen(String testUrl) throws RegistreerException {
		// arrange
		Adres adres = Mockito.mock(Adres.class);
		B2BBedrijfDTO dto = maakGeldigBedrijfDTO();
		dto.websiteURL = testUrl;
		B2BBedrijf bedrijf;

		// act
		bedrijf = new B2BBedrijf(dto, adres);
		
		// assert
		assertEquals(testUrl, bedrijf.getWebsiteURL());
	}

	// TESTEN: SECTOR TESTEN
	// test sector is null
	@Test
	void testSectorInConstrucotr_Null_RegistreerException() {
		// arrange
		Adres adres = Mockito.mock(Adres.class);
		B2BBedrijfDTO dto = maakGeldigBedrijfDTO();
		dto.sector = null;

		// act
		// assert
		assertThrows(RegistreerException.class, () -> new B2BBedrijf(dto, adres));
	}

	// test sector is geldig
	@Test
	void testSectorInConstrucotr_SectorIsGeldig_maaktBedrijf() throws RegistreerException {
		Adres adres = Mockito.mock(Adres.class);
		for(BedrijfSectorEnum enumSector : BedrijfSectorEnum.values()) {
			B2BBedrijfDTO dto = maakGeldigBedrijfDTO();
			dto.sector = enumSector;			
			B2BBedrijf bedrijf = new B2BBedrijf(dto, adres);
			assertEquals(bedrijf.getSector(), enumSector);
		}
	}

	// TESTEN: IS_ACTIEF TESTEN
	// test actief is null (Boolean in dto)
	@Test
	void testIsActiefInConstructor_Null_maaktBedrijfDatActiefIs() throws RegistreerException {
		// arrange
		Adres adres = Mockito.mock(Adres.class);
		B2BBedrijfDTO dto = maakGeldigBedrijfDTO();
		dto.isActief = null;
		B2BBedrijf bedrijf;

		// act
		bedrijf = new B2BBedrijf(dto, adres);
		
		// assert
		assertEquals(bedrijf.getIsActief(), Boolean.TRUE);
	}
		
	// test actief is geldig -> true en false
	@ParameterizedTest
	@ValueSource(ints = {0, 1})
	void testIsActiefInConstructor_TrueEnFalse_maaktBedrijfMetCorrecteIsActiefState(int val) throws RegistreerException {
		// arrange
		Adres adres = Mockito.mock(Adres.class);
		B2BBedrijfDTO dto = maakGeldigBedrijfDTO();
		dto.isActief = val != 0;
		B2BBedrijf bedrijf;

		// act
		bedrijf = new B2BBedrijf(dto, adres);
		
		// assert
		assertEquals(bedrijf.getIsActief(), val != 0);
	}
}
