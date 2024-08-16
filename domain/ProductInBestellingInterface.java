package domain;

import java.math.BigDecimal;

public interface ProductInBestellingInterface {

	String getNaam();

	int getAantal();

	boolean getIsInStock();

	BigDecimal getEenheidsprijs();

	BigDecimal getTotalePrijs();
}
