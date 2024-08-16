package testen;

import java.math.BigDecimal;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import domain.Product;

public class ProductTest {

	//De naam van een product moet minstens 2 tekens bedragen en maximaal 25
	@ParameterizedTest
	@ValueSource (strings = {"ei", "Brood", "hamburger", "bakpoeder uit Peru123456", "bakpoeder uit Peru1234567"})
	public void nieuwProductMetGeldigeNaam(String naam) {
		Assertions.assertDoesNotThrow(() -> new Product(naam, new BigDecimal(1), 5));
	}

	//De naam van een product moet minstens 2 tekens bedragen en maximaal 25
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource (strings = {"b", "bakpoeder uit Peru1234567b", "bakpoeder uit Portugal123456789"})
	public void nieuwProductMetOnGeldigeNaam(String naam) {
		Assertions.assertThrows(IllegalArgumentException.class,() -> new Product(naam, new BigDecimal(1), 5));
	}

	//Het aantalInStock van een product moet altijd >=0
	@ParameterizedTest
	@ValueSource (ints = {0,1,20,250})
	public void nieuwProductMetGeldigeAantalInStock(int aantal) {
		Assertions.assertDoesNotThrow(() -> new Product("kaas", new BigDecimal(1), aantal));
	}

	//Het aantalInStock van een product moet altijd >=0
	@ParameterizedTest
	@ValueSource (ints = {-1,-20,-250})
	public void nieuwProductMetOngeldigeAantalInStock(Integer aantal) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Product("kaas", new BigDecimal(1), aantal));
	}

	//De eenheidsprijs van een product moet altijd > 0
	@ParameterizedTest
	@ValueSource (doubles = {0.01,1, 200})
	public void nieuwProductMetGeldigeEenheidsprijs(double decimal) {
		Assertions.assertDoesNotThrow(() -> new Product("kaas", new BigDecimal(decimal), 5));
	}

	//De eenheidsprijs van een product moet altijd > 0
	@ParameterizedTest
	@NullSource
	@MethodSource("ongeldigeEenheidsprijsWaarden")
	public void nieuwProductMetOngeldigeEenheidsprijs(BigDecimal decimal) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Product("kaas", decimal, 5));
	}

	//Een product is in stock als de aantalInStock > 0
	@ParameterizedTest
	@ValueSource (ints = {1, 2, 10, 100})
	public void productIsInStock(int aantal) {
		Product p = new Product("kaas", new BigDecimal(1), aantal);
		Assertions.assertTrue(p.isInStock());
	}

	//Een product is niet in stock als de aantalInStock == 0
	@Test
	public void productIsInStock() {
		Product p = new Product("kaas", new BigDecimal(1), 0);
		Assertions.assertFalse(p.isInStock());
	}

	//Voor het testen van Nullsource op een BigDecimal
	private static Stream<BigDecimal> ongeldigeEenheidsprijsWaarden() {
        return Stream.of(
                null,
                BigDecimal.ZERO,
                new BigDecimal("-0.01"),
                new BigDecimal("-1"),
                new BigDecimal("-200")
        );
    }
}
