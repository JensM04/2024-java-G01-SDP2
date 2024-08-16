package controllers;

import java.util.List;
import java.util.function.Function;

import domain.ActieEnumFactory;
import domain.B2BBestelling;
import domain.B2BPortaal;
import domain.Bedrijf;
import domain.Bestelling;
import domain.BestellingCommandFactory;
import domain.Betaling;
import domain.BulkOpsEnumFactory;
import domain.ConcreteCommand;
import domain.Leverancier;
import domain.PredicateFactory;
import domain.ProductInBestellingInterface;
import dto.B2BBedrijfDTO;
import dto.BestellingDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import utils.BulkOpsEnum;
import utils.ControllerSoortEnum;

public class LeverancierController extends Controller {

	private ObservableList<Bedrijf> klantenList;
	private FilteredList<Bedrijf> filteredKlantenList;
	private ObservableList<Bestelling> bestellingList;
	private FilteredList<Bestelling> filteredBestellingList;
	private ObservableList<BulkOpsEnum> bulkOps = FXCollections.observableArrayList();
	private ObservableList<Enum> actiesList = FXCollections.observableArrayList();
	private Bedrijf huidigeKlant;
	private Leverancier huidigeLeverancier;
	private Bestelling huidigeBestelling;
	private BulkOpsEnum huidigeBulkOpsEnum;
	private Enum huidigeActie;
	private B2BPortaal b2bPortaal;

	public LeverancierController(Leverancier leverancier) {
		this.huidigeLeverancier = leverancier;
		setKlantenList(huidigeLeverancier.getAlleKlanten());
	}
	
	@Override
	public Leverancier getHuidigeLeverancier() {
		return huidigeLeverancier;
	}

	@Override
	public void changeKlantListFilter(B2BBedrijfDTO filter) {
		if (filter == null) {
			throw new IllegalArgumentException("Er werd geen klantfilter meegegeven");
		}
		this.filteredKlantenList.setPredicate(PredicateFactory.createBedrijfFilterPredicate(filter));
	}

	@Override
	public Bestelling getBestelling() {
		return this.huidigeLeverancier.getHuidigeBestelling();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ObservableList<Bestelling> getBestellingenList() {
		this.setBestellingenList((List<Bestelling>) huidigeLeverancier.getAlleBestellingen());
		return this.filteredBestellingList;
	}

	@Override
	public ObservableList<Bestelling> getBestellingenListHuidigeKlant() {
		this.setBestellingenList(huidigeKlant.getUitgaandeBestellingen());
		return this.bestellingList;
	}

	@Override
	public ControllerSoortEnum getControllerSoort() {
		return ControllerSoortEnum.LEVERANCIER;
	}

	@Override
	public Bedrijf getHuidigeKlant() {
		return this.huidigeKlant;

	}

	@Override
	public Bedrijf getKlant(int klantId) {
		return huidigeLeverancier.getKlantById(klantId);
	}

	@Override
	public ObservableList<Bedrijf> getKlantenList() {
		return this.filteredKlantenList;
	}

	@Override
	public ObservableList<ProductInBestellingInterface> getProductenInBestelling() {
		return FXCollections
				.observableArrayList(this.huidigeLeverancier.getHuidigeBestelling().getProductenInBestelling());
	}

	@Override
	public void searchKlantList(String query) {
		if (query == null) {
			throw new IllegalArgumentException("Er werd geen klantfilter meegegeven");
		}
//		System.out.println(query);
		this.filteredKlantenList.setPredicate(PredicateFactory.createBedrijfSearchPredicate(query));
	}

	private void setBestellingenList(List<Bestelling> bestellingen) {
		this.bestellingList = FXCollections.observableList(bestellingen);
		this.filteredBestellingList = new FilteredList<>(bestellingList, p -> true);
	}

	@Override
	public void setHuidigeBestelling(Bestelling bestelling) {
		this.huidigeLeverancier.setHuidigeBestelling(bestelling);
	}

	@Override
	public void setHuidigeKlant(Bedrijf klant) {
		this.huidigeKlant = klant;
	}

	private void setKlantenList(List<Bedrijf> klanten) {
		this.klantenList = FXCollections.observableList(klanten);
		this.filteredKlantenList = new FilteredList<>(klantenList, k -> true);
	}

	@Override
	public void wijzigBestelling(BestellingDTO bestellingWijziging) {
		huidigeLeverancier.wijzigBestelling(bestellingWijziging.bestellingStatus, bestellingWijziging.betaalStatus);
	}

	@Override
	public ObservableList<BulkOpsEnum> getBulkOpsBestellingenList() {
		this.bulkOps.clear();
		List<BulkOpsEnum> bulkOpsList = BulkOpsEnumFactory.createBulkOpsEnumListVoorBestellingen();
		this.bulkOps.addAll(bulkOpsList);
		return this.bulkOps;
	}

	public void searchBestellingList(String query) {
		filteredBestellingList.setPredicate(PredicateFactory.createBestellingSearchPredicate(query));
	}

	@Override
	public ObservableList<Enum> getActiesList() {
		return this.actiesList;
	}

	@Override
	public void setHuidigeBulkOps(BulkOpsEnum bulkOps) {
		this.actiesList.clear();
		this.huidigeActie = null;
		if (bulkOps != null) {
			this.huidigeBulkOpsEnum = bulkOps;
			this.actiesList.addAll(ActieEnumFactory.createActieEnumListVoorBulkOpsBestellingen(bulkOps));
		}
	}

	@Override
	public void setHuidigeActie(Enum actie) {
		if (actie != null) {
			this.huidigeActie = actie;
		}
	}

	@Override
	public void voerActieUitOpBestellingen(List<Bestelling> bestellingen) {
		Function<B2BBestelling, ConcreteCommand<B2BBestelling>> commandFunctie = BestellingCommandFactory.createBestellingCommand(huidigeBulkOpsEnum, huidigeActie);
		this.huidigeLeverancier.voerBestellingCommandUitOpBestellingList(bestellingen, commandFunctie);
	}
	
	@Override
	public void verwerkBetalingen() {
		this.huidigeLeverancier.verwerkBetalingen();
	}
	
	@Override
	public void wijzigWachtwoord(String nieuwWachtwoord, String bevestigingWachtwoord) {
		if (this.b2bPortaal == null) {
			this.b2bPortaal = new B2BPortaal();
		}
		this.b2bPortaal.wijzigWachtwoord(huidigeLeverancier, nieuwWachtwoord, bevestigingWachtwoord);
	}
	
	@Override
	public boolean isWachtwoordGewijzigd() {
		return this.huidigeLeverancier.isWachtwoordGewijzigd();
	}
	
	@Override
	public ObservableList<Betaling> getBetalingenKlant(Bedrijf klant){
		return FXCollections.observableArrayList(this.huidigeLeverancier.getBetalingenBedrijf(klant));
	}
	
	@Override
	public void changeBestellingListFilter(BestellingDTO bestelling) {
		this.filteredBestellingList.setPredicate(PredicateFactory.createBestellingFilterPredicate(bestelling));
	}
}
