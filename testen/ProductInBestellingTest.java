package testen;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import domain.Product;
import domain.ProductInBestelling;

@ExtendWith(MockitoExtension.class)
public class ProductInBestellingTest {

	@Mock
	private Product p;

	@InjectMocks
	private ProductInBestelling pb;

	// ProductInBestelling moet een positief aantal hebben
	@ParameterizedTest
	@ValueSource(ints = {1,2,10,100})
	public void ProductInBestellingMetPositiefAantal(int aantal) {
		Assertions.assertDoesNotThrow(() -> new ProductInBestelling(p, aantal));
	}

	// ProductInBestelling moet een positief aantal hebben
	@ParameterizedTest
	@ValueSource(ints = {-100, -5, -1, 0})
	public void ProductInBestellingMetFoutiefAantal(int aantal) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new ProductInBestelling(p, aantal));
	}

	//ProductInBestelling moet een product krijgen
	@Test
	public void ProductInBestellingMetNullAlsProduct() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new ProductInBestelling(null, 3));
	}

	//ProductInBestelling geeft de naam van het product terug
	@Test
	public void productInBestellingGeeftCorrecteNaam() {
		Mockito.when(p.getNaam()).thenReturn("kaas");

		pb = new ProductInBestelling(p, 1);
		Assertions.assertEquals("kaas",pb.getNaam());

		Mockito.verify(p).getNaam();
	}

	//ProductInBestelling geeft de totale prijs van de bestelling, prijs product = 3.5, aantal = 3 -> totaal = 10.5
	@Test
	public void productInBestellingGeeftTotalePrijs() {
		Mockito.when(p.getEenheidsprijs()).thenReturn(new BigDecimal("3.5"));

		pb = new ProductInBestelling(p, 3);
		Assertions.assertEquals(new BigDecimal("10.5"),pb.getTotalePrijs());

		Mockito.verify(p).getEenheidsprijs();
	}

	//test de aantal getter
	@Test
	public void productInBestellingGeeftAantal() {
		pb = new ProductInBestelling(p, 3);
		Assertions.assertEquals(3,pb.getAantal());
	}

	//test een product dat niet in stock is
	@Test
	public void productInBestellingChecktProductNietInStock() {
		Mockito.when(p.isInStock()).thenReturn(false);

		pb = new ProductInBestelling(p, 3);
		Assertions.assertEquals(false, pb.getIsInStock());

		Mockito.verify(p).isInStock();
	}

	//test een product dat wel in stock is
	@Test
	public void productInBestellingChecktProductWelInStock() {
		Mockito.when(p.isInStock()).thenReturn(true);

		pb = new ProductInBestelling(p, 3);
		Assertions.assertEquals(true,pb.getIsInStock());

		Mockito.verify(p).isInStock();
	}

	//ProductInBestelling geeft de eenheidsprijs van het product
	public void productInBestellingGeeftEenheidsprijsVanProduct() {
		Mockito.when(p.getEenheidsprijs()).thenReturn(new BigDecimal("3.5"));

		pb = new ProductInBestelling(p, 3);
		Assertions.assertEquals(new BigDecimal("3.5") ,pb.getEenheidsprijs());

		Mockito.verify(p).getEenheidsprijs();
	}
}
