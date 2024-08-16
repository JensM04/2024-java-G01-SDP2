package domain;

import java.util.List;
import java.util.UUID;

import dto.B2BBedrijfDTO;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import utils.BedrijfSectorEnum;

public interface Bedrijf extends Filterable<B2BBedrijfDTO>, Searchable, UUIDIdentifiable {

	String getNaam();

	AdresInterface getAdres();

	int getAantalKlanten();

	BooleanProperty isActiefProperty();

	BedrijfSectorEnum getSector();

	String getEmail();

	String getWebsiteURL();

	String getTelefoonnummer();

	StringProperty naamProperty();

	StringProperty adresProperty();

	IntegerProperty aantalOpenstaandeBestellingenInkomendProperty();

	ObjectProperty<BedrijfSectorEnum> sectorProperty();

	StringProperty emailProperty();

	StringProperty telefoonnummerProperty();

	StringProperty getAdresProperty();

	StringProperty kortAdresProperty();

	StringProperty getKortAdres();

	List<Bestelling> getUitgaandeBestellingen();

	IntegerProperty aantalOpenstaandeBestellingenUitgaandProperty(UUID id);

	ObservableList<Klant> getKlantAccounts();

	ObservableList<Leverancier> getLeverancierAccounts();
	
	String getBtw();
}
