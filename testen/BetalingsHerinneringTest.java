package testen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import domain.B2BBedrijf;
import domain.B2BBestelling;
import domain.Bedrijf;
import domain.BetalingsHerinnering;
import utils.BestellingStatusEnum;

@ExtendWith(MockitoExtension.class)
public class BetalingsHerinneringTest {


	private B2BBestelling bestelling;
	
	@BeforeEach
	void beforeEach() {
		bestelling = Mockito.mock(B2BBestelling.class);
	}

	@Test
	void testConstructor_NullWaarde_ThrowsException() {
		assertThrows(
				IllegalArgumentException.class, 
				() -> new BetalingsHerinnering(null));
	}

	@Test
	void testConstructor_GeldigeWaardes_MaaktObject() {
		Bedrijf klant = Mockito.mock(B2BBedrijf.class);
		
		Mockito.when(bestelling.getBestellingDatum()).thenReturn(LocalDateTime.now());
		Mockito.when(bestelling.getUuid()).thenReturn(UUID.randomUUID());
		Mockito.when(bestelling.getTotaalBestellingbedragExclBTW()).thenReturn(new BigDecimal(2));
		Mockito.when(bestelling.getBestellingStatus()).thenReturn(BestellingStatusEnum.VERZONDEN);
		Mockito.when(bestelling.getKlant()).thenReturn(klant);
		Mockito.when(klant.getEmail()).thenReturn("email");
		
		BetalingsHerinnering betalingsHerinnering =  new BetalingsHerinnering(bestelling);

		assertNotNull(betalingsHerinnering);
		assertEquals(BetalingsHerinnering.class, betalingsHerinnering.getClass());
		
		Mockito.verify(bestelling, times(2)).getBestellingDatum();
		Mockito.verify(bestelling, times(2)).getUuid();
		Mockito.verify(bestelling).getTotaalBestellingbedragExclBTW();
		Mockito.verify(bestelling).getBestellingStatus();
		Mockito.verify(bestelling).getKlant();
		Mockito.verify(klant).getEmail();
	}

}
