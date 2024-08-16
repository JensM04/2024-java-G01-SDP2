package testen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import domain.B2BBedrijf;
import domain.B2BBestelling;
import domain.Bestelling;
import domain.Leverancier;
import dto.GebruikerDTO;
import exceptions.RegistreerGebruikerException;
import utils.GebruikerRolEnum;

@ExtendWith(MockitoExtension.class)
class LeverancierTest {
	@Mock
	private B2BBedrijf mockBedrijf;
	private Leverancier leverancier;

	private B2BBestelling createMockBestelling(UUID id) {
		B2BBestelling bestelling = Mockito.mock(B2BBestelling.class);

		Mockito.lenient().when(bestelling.getUuid()).thenReturn(id);

		return bestelling;
	}

	private Leverancier maakGeldigeLeverancier(B2BBedrijf bedrijf) {
		GebruikerDTO gebruikerDTO = new GebruikerDTO();
		gebruikerDTO.naam = "testleverancier";
		gebruikerDTO.email = "test@leverancier.be";
		gebruikerDTO.rol = GebruikerRolEnum.LEVERANCIER;
		gebruikerDTO.wachtwoord = "Ww-1234@";
		try {
			return new Leverancier(gebruikerDTO, bedrijf);
		} catch (RegistreerGebruikerException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Test
	void testGetAlleBestellingen_GeenBestellingen_LegeLijstNietNull() {
		List<B2BBestelling> legeLijst = new ArrayList<>();
		B2BBedrijf bedrijf = Mockito.mock(B2BBedrijf.class);
		Leverancier leverancier = maakGeldigeLeverancier(bedrijf);
		Mockito.when(bedrijf.getInkomendeBestellingen()).thenReturn(legeLijst);

		List<? extends Bestelling> bestellingen = leverancier.getAlleBestellingen();

		assertNotNull(bestellingen);
		assertEquals(0, bestellingen.size());

		Mockito.verify(bedrijf).getInkomendeBestellingen();
	}

	@Test
	void testGetAlleBestellingen_3Bestellingen_UnmodifiableList() {
		leverancier = maakGeldigeLeverancier(mockBedrijf);

		List<B2BBestelling> dummyBestellingen = Arrays.asList(
				this.createMockBestelling(UUID.randomUUID()),
				this.createMockBestelling(UUID.randomUUID()),
				this.createMockBestelling(UUID.randomUUID())
		);

		B2BBestelling mockBestelling = this.createMockBestelling(UUID.randomUUID());

		Mockito.when(mockBedrijf.getInkomendeBestellingen()).thenReturn(Collections.unmodifiableList(dummyBestellingen));

		@SuppressWarnings("unchecked")
		List<Bestelling> bestellingen = (List<Bestelling>) leverancier.getAlleBestellingen();

		assertNotNull(bestellingen);
		assertEquals(3, bestellingen.size());

		assertThrows(UnsupportedOperationException.class, () -> bestellingen.add(mockBestelling));

		Mockito.verify(mockBedrijf).getInkomendeBestellingen();
	}

	@Test
	void testGetBestelling_BestellingBestaatNiet_ThrowsException() {
		UUID id1 = UUID.randomUUID();
		UUID id2 = UUID.randomUUID();
		UUID id3 = UUID.randomUUID();
		List<Bestelling> mockBestellingen = Arrays.asList(this.createMockBestelling(id1), this.createMockBestelling(id2));
		leverancier = maakGeldigeLeverancier(mockBedrijf);
		Mockito.when(mockBedrijf.getInkomendeBestellingByUuid(id3))
			.thenReturn(null);

		assertThrows(IllegalArgumentException.class, () -> leverancier.getBestelling(id3));

		Mockito.verify(mockBedrijf).getInkomendeBestellingByUuid(id3);
	}

	@Test
	void testGetBestelling_BestellingBestaat_GetBestelling() {
		UUID id = UUID.randomUUID();
		B2BBestelling mockBestelling = this.createMockBestelling(id);
		leverancier = maakGeldigeLeverancier(mockBedrijf);
		Mockito.when(mockBedrijf.getInkomendeBestellingByUuid(id))
			.thenReturn(mockBestelling);

		Bestelling resultaat = leverancier.getBestelling(id);

		assertEquals(id, resultaat.getUuid());

		Mockito.verify(mockBedrijf).getInkomendeBestellingByUuid(id);
	}

	// TODO test leverancier constructor -> gebruikerdto null & bedrijf null

	// TODO test setHuidigeBestelling

	// TODO test getHuidigeBestelling

	// TODO test wijzigBestelling

	// TODO test getAlleKlanten

	// TODO test getAlleKlanten

	// TODO test getKlantById

	// TODO test getOpenstaandeBestellingen

	// TODO test getBeschikbareProducten
}
