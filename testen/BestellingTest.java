package testen;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import domain.Adres;
import domain.B2BBedrijf;
import domain.B2BBestelling;
import domain.Leverancier;
import domain.ProductInBestelling;
import domain.ProductInBestellingInterface;
import utils.BestellingStatusEnum;
import utils.BetaalStatusEnum;

@ExtendWith(MockitoExtension.class)
class BestellingTest {
	BetaalStatusEnum b;

	@Test
	void createBestelling_nullAlsKlant_gooitException() {
	    Adres mockAdres = Mockito.mock(Adres.class);
	    B2BBedrijf mockLeverancier = Mockito.mock(B2BBedrijf.class);
	    Assertions.assertThrows(IllegalArgumentException.class,
	            () -> new B2BBestelling(null, mockAdres, new ArrayList<>(), mockLeverancier));
	}

	@Test
	void createBestelling_nullAlsAdres_gooitException() {
		B2BBedrijf mockKlant = Mockito.mock(B2BBedrijf.class);
		B2BBedrijf mockLeverancier = Mockito.mock(B2BBedrijf.class);
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> new B2BBestelling(mockKlant, null, new ArrayList<>(), mockLeverancier));
	}

	@Test
	void createBestelling_nullAlsProducten_gooitException() {
		B2BBedrijf mockKlant = Mockito.mock(B2BBedrijf.class);
		Adres mockLeverAdres = Mockito.mock(Adres.class);
		B2BBedrijf mockLeverancier = Mockito.mock(B2BBedrijf.class);
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> new B2BBestelling(mockKlant, mockLeverAdres, null, mockLeverancier));
	}

	@Test
	void getProducten_geeftUnmoddifiableList() {
		B2BBedrijf mockLeverancier = Mockito.mock(B2BBedrijf.class);
		B2BBedrijf mockKlant = Mockito.mock(B2BBedrijf.class);
		Adres mockLeverAdres = Mockito.mock(Adres.class);
		ProductInBestelling productDummy1 = Mockito.mock(ProductInBestelling.class);
		B2BBestelling bestellingDummy = new B2BBestelling(mockKlant, mockLeverAdres, new ArrayList<>(),mockLeverancier);

		List<ProductInBestellingInterface> productLijst = bestellingDummy.getProductenInBestelling();

		Assertions.assertThrows(UnsupportedOperationException.class, () -> productLijst.add(productDummy1));
	}

	//12.5 euro verzendkosten
	@Test
	void getTotaleBedrag_geefJuisteBedragMetVerzendKosten() {
		B2BBedrijf mockLeverancier = Mockito.mock(B2BBedrijf.class);
		ProductInBestelling productDummy1 = Mockito.mock(ProductInBestelling.class);
		ProductInBestelling productDummy2 = Mockito.mock(ProductInBestelling.class);
		B2BBedrijf mockKlant = Mockito.mock(B2BBedrijf.class);
		Adres mockLeverAdres = Mockito.mock(Adres.class);

		Mockito.when(productDummy1.getTotalePrijs()).thenReturn(new BigDecimal(2039.01));
		Mockito.when(productDummy2.getTotalePrijs()).thenReturn(new BigDecimal(700.00));
		
		B2BBestelling bestellingDummy = new B2BBestelling(mockKlant, mockLeverAdres, new ArrayList<>(Arrays.asList(productDummy1, productDummy2)),mockLeverancier);
		
		Assertions.assertEquals(Math.round(2751.51 * 100), Math.round(bestellingDummy.getTotaalBestellingbedragExclBTW().doubleValue() * 100));
	}

	@Test
	void testGetAdres_geeftAdresStringTerug_NietNull() {
		B2BBedrijf mockLeverancier = Mockito.mock(B2BBedrijf.class);
		Adres mockLeverAdres = Mockito.mock(Adres.class);
		B2BBedrijf mockKlant = Mockito.mock(B2BBedrijf.class);

		Mockito.when(mockLeverAdres.toString()).thenReturn("Sint-Jozefstraat 1, 9880 Aalter");

		B2BBestelling bestelling = new B2BBestelling(mockKlant, mockLeverAdres, new ArrayList<>(),mockLeverancier);

		Adres adres = bestelling.getLeveradres();
		
		Assertions.assertNotNull(adres);
		Assertions.assertEquals("Sint-Jozefstraat 1, 9880 Aalter",adres.toString());
		 
	}

	@ParameterizedTest
	@EnumSource(BestellingStatusEnum.class)
	void testWijzigOrderStatus_geeftJuisteOrderStatus(BestellingStatusEnum orderStatus) {
		B2BBedrijf mockLeverancier = Mockito.mock(B2BBedrijf.class);
		Adres mockLeverAdres = Mockito.mock(Adres.class);
		B2BBedrijf mockKlant = Mockito.mock(B2BBedrijf.class);
		
		B2BBestelling bestelling = new B2BBestelling(mockKlant, mockLeverAdres, new ArrayList<>(),mockLeverancier);
		
		bestelling.wijzigBestellingStatus(orderStatus);

		Assertions.assertEquals(orderStatus, bestelling.getBestellingStatus());
	}

	@Test
	void testWijzigOrderStatus_nullAlsOrderStatus_gooitException() {
		B2BBedrijf mockLeverancier = Mockito.mock(B2BBedrijf.class);
		Adres mockLeverAdres = Mockito.mock(Adres.class);
		B2BBedrijf mockKlant = Mockito.mock(B2BBedrijf.class);
		
		B2BBestelling bestelling = new B2BBestelling(mockKlant, mockLeverAdres, new ArrayList<>(),mockLeverancier);
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> bestelling.wijzigBestellingStatus(null));
	}
	
}
