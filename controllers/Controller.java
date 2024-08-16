package controllers;

import java.util.Comparator;
import java.util.List;

import domain.Bedrijf;
import domain.Bestelling;
import domain.Betaling;
import domain.Leverancier;
import domain.ProductInBestellingInterface;
import dto.B2BBedrijfDTO;
import dto.BestellingDTO;
import dto.GebruikerDTO;
import dto.KlantDTO;
import exceptions.RegistreerException;
import exceptions.RegistreerGebruikerException;
import javafx.collections.ObservableList;
import repository.BedrijfDaoJpa;
import utils.BedrijfSectorEnum;
import utils.BulkOpsEnum;
import utils.ControllerSoortEnum;

public abstract class Controller {
	public static final Comparator<Bedrijf> BY_KLANT_NAAM_ASC = (k1, k2) -> k1.getNaam()
			.compareToIgnoreCase(k2.getNaam());
	public static final Comparator<Bedrijf> BY_KLANT_NAAM_DESC = BY_KLANT_NAAM_ASC.reversed();
	public static final Comparator<Bedrijf> BY_KLANT_ADRES_ASC = (k1, k2) -> k1.getAdres().getAdresString()
			.compareToIgnoreCase(k2.getAdres().getAdresString());
	public static final Comparator<Bedrijf> BY_KLANT_ADRES_DESC = BY_KLANT_ADRES_ASC.reversed();

//	public final static Comparator<Bestelling> BY_BESTELLING_ID_ASC = (p1, p2) -> Integer.compare(p1.getId(),
//			p2.getId());
//	public final static Comparator<Bestelling> BY_BESTELLING_ID_DESC = BY_BESTELLING_ID_ASC.reversed();

	public final static Comparator<Bestelling> BY_BESTELDATUM_ASC = (p1, p2) -> p1.getBestellingDatum()
			.compareTo(p2.getBestellingDatum());
	public final static Comparator<Bestelling> BY_BESTELDATUM_DESC = BY_BESTELDATUM_ASC.reversed();

	public final static Comparator<Bestelling> BY_TOTAAL_BEDRAG_ASC = (p1, p2) -> p1.getTotaalBestellingbedragMetBTW()
			.compareTo(p2.getTotaalBestellingbedragMetBTW());
	public final static Comparator<Bestelling> BY_TOTAAL_BEDRAG_DESC = BY_BESTELDATUM_ASC.reversed();

	public final static Comparator<Bestelling> BY_STATUS_ASC = (p1, p2) -> p1.getBestellingStatus()
			.compareTo(p2.getBestellingStatus());
	public final static Comparator<Bestelling> BY_STATUS_DESC = BY_STATUS_ASC.reversed();

//	public final static Comparator<Bestelling> BY_IS_BETAALD_ASC = (p1, p2) -> Boolean.compare(p1.getIsBetaald(),
//			p2.getIsBetaald());
//	public final static Comparator<Bestelling> BY_IS_BETAALD_DESC = BY_IS_BETAALD_ASC.reversed();

	public AanmeldController afmelden() {
		throw new IllegalAccessError();
	}

	public ControllerSoortEnum getControllerSoort() {
		return ControllerSoortEnum.CONTROLLER;
	}

	public List<BestellingDTO> getAlleBestellingen() {
		throw new IllegalAccessError();
	}

	public Bestelling getBestelling() {
		throw new IllegalAccessError();
	}

	public void wijzigBestelling(BestellingDTO bestellingWijziging) {
		throw new IllegalAccessError();
	}

	public List<KlantDTO> getAlleKlanten() {
		throw new IllegalAccessError();
	}

	public Bedrijf getKlant(int klantId) {
		throw new IllegalAccessError();
	}

	public void setHuidigeBestelling(Bestelling bestelling) {
		throw new IllegalAccessError();
	}

	public ObservableList<? extends Bedrijf> getAlleBedrijven() {
		throw new IllegalAccessError();
	}

	public Bedrijf getBedrijf(long bedrijfId) {
		throw new IllegalAccessError();
	}

	public void addBedrijf(B2BBedrijfDTO bedrijf) throws RegistreerException {
		throw new IllegalAccessError();
	}

	public void verwijderBedrijf(long id) {
		throw new IllegalAccessError("Not allowed method call");
	}

	public ObservableList<Bedrijf> getKlantenList() {
		throw new IllegalAccessError("Not allowed method call");
	}

	public void sortKlantenList(Comparator<Bedrijf> comparator) {
		throw new IllegalAccessError("Not allowed method call");
	}

	public ObservableList<Bestelling> getBestellingenListHuidigeKlant() {
		throw new IllegalAccessError();
	}

	public ObservableList<Bestelling> getHuidigeBestellingenList() {
		throw new IllegalAccessError();
	}

	public void changeBestellingListSorting(Comparator<Bestelling> comparator) {
		throw new IllegalAccessError();
	}


	public Bedrijf getHuidigeKlant() {
		throw new IllegalAccessError();
	}


	public void changeKlantBestellingListSort(Comparator<Bestelling> comparator) {
		throw new IllegalAccessError();
	}

	public ObservableList<Bestelling> getBestellingenList() {
		throw new IllegalAccessError();
	}

	public void changeKlantListFilter(KlantDTO filter) {
		throw new IllegalAccessError();
	}

	public void selecteerKlant(Bedrijf klant) {
		throw new IllegalAccessError();
	}

	public void selecteerHuidigeKlant(int index) {
		throw new IllegalAccessError();
	}

	public void searchKlantList(String query) {
		throw new IllegalAccessError();
	}

	public void changeBedrijfListFilter(B2BBedrijfDTO filter) {
		throw new IllegalAccessError();
	}

	public void searchBestellingList(String query) {
		throw new IllegalAccessError();
	}

	public Bedrijf getHuidigBedrijf() {
		throw new IllegalAccessError();
	}

	public ObservableList<BedrijfSectorEnum> getAlleSectoren() {
		throw new IllegalAccessError();
	}

	public String getSectorHuidigBedrijf() {
		throw new IllegalAccessError();
	}

	public void wijzigSectorHuidigBedrijf(BedrijfSectorEnum newVal) {
		throw new IllegalAccessError();
	}

	public void selecteerBedrijf(int bedrijfId) {
		throw new IllegalAccessError();
	}

	public void wijzigHuidigBedrijfIsActief() {
		throw new IllegalAccessError();
	}

	public void changeKlantListFilter(B2BBedrijfDTO filter) {
		throw new IllegalAccessError();
	}

	public void changeBedrijvenListFilter(B2BBedrijfDTO filter) {
		throw new IllegalAccessError();
	}

	public ObservableList<ProductInBestellingInterface> getProductenInBestelling() {
		// TODO Auto-generated method stub
		throw new IllegalAccessError();
	}

	public void setHuidigBedrijf(Bedrijf b) {
		throw new IllegalAccessError();
	}

	public void setHuidigeKlant(Bedrijf klant) {
		throw new IllegalAccessError();
	}

	public void voegGebruikerToeHuidigBedrijf(GebruikerDTO gebruiker) throws RegistreerGebruikerException {
		throw new IllegalAccessError();
	}

	public ObservableList<BulkOpsEnum> getBulkOpsBestellingenList() {
		throw new IllegalAccessError();
	}

	public ObservableList<Enum> getActiesList() {
		throw new IllegalAccessError();
	}

	public void setHuidigeBulkOps(BulkOpsEnum bulkOps) {
		throw new IllegalAccessError();
	}

	public void setHuidigeActie(Enum actie) {
		throw new IllegalAccessError();
	}

	public void voerActieUitOpBestellingen(List<Bestelling> bestellingen) {
		throw new IllegalAccessError();
	}

	public void wijzigBedrijfIsActief(Bedrijf bedrijf) {
		throw new IllegalAccessError();
	}

	public void voerActieUitOpBedrijven(List<Bedrijf> bedrijven) {
		throw new IllegalAccessError();
	}

	public ObservableList<BulkOpsEnum> getBulkOpsBedrijvenList() {
		throw new IllegalAccessError();
	}

	public ObservableList<BedrijfSectorEnum> getAlleBedrijfSectoren() {
		throw new IllegalAccessError();
	}

	public void voegBedrijfToe(B2BBedrijfDTO bedrijfDTO) {
		throw new IllegalAccessError();
	}

	public void verwerkBetalingen() {
		throw new IllegalAccessError();
	}

	public Leverancier getHuidigeLeverancier() {
		throw new IllegalAccessError();
	}

	public void wijzigWachtwoord(String nieuwWachtwoord, String bevestigingWachtwoord) {
		throw new IllegalAccessError();
	}

	public boolean isWachtwoordGewijzigd() {
		throw new IllegalAccessError();
	}

	public ObservableList<Betaling> getBetalingenKlant(Bedrijf klant) {
		throw new IllegalAccessError();
	}

	public void close() {
		new BedrijfDaoJpa().closePersistency();
	}

	public void searchBedrijf(String search) {
		throw new IllegalAccessError();
	}

	public void changeBestellingListFilter(BestellingDTO filterDTO) {
		throw new IllegalAccessError();
	}

	public void changeKlantBestellingListFilter(BestellingDTO bestellingFilterDTO) {
		throw new IllegalAccessError();
	}
}
