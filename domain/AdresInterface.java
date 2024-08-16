package domain;

import dto.AdresDTO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public interface AdresInterface extends Searchable, Filterable<AdresDTO> {

	String getStraat();
	SimpleStringProperty straatProperty();

	int getHuisnummer();
	SimpleIntegerProperty huisnummerProperty();

	String getBus();
	SimpleStringProperty busProperty();

	int getPostcode();
	SimpleIntegerProperty postcodeProperty();

	String getGemeente();
	SimpleStringProperty gemeenteProperty();

	void createStringProperty();
	SimpleStringProperty adresStringProperty();
	String getAdresString();

}
