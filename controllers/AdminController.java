package controllers;

import java.util.List;
import java.util.ListIterator;
import java.util.function.Function;

import domain.ActieEnumFactory;
import domain.Adres;
import domain.B2BBedrijf;
import domain.B2BPortaal;
import domain.Bedrijf;
import domain.BedrijfCommandFactory;
import domain.BulkOpsEnumFactory;
import domain.ConcreteCommand;
import domain.Gebruiker;
import domain.GebruikerFactory;
import domain.PredicateFactory;
import dto.B2BBedrijfDTO;
import dto.BestellingDTO;
import dto.GebruikerDTO;
import exceptions.RegistreerException;
import exceptions.RegistreerGebruikerException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import utils.BedrijfSectorEnum;
import utils.BulkOpsEnum;
import utils.ControllerSoortEnum;
import utils.GebruikerRolEnum;
import utils.JPAUtil;

public class AdminController extends Controller {
	private B2BBedrijf huidigBedrijf;
	private ObservableList<BedrijfSectorEnum> sectoren = FXCollections
			.observableArrayList(BedrijfSectorEnum.values());
	private ObservableList<? extends Bedrijf> bedrijfList;
	private FilteredList<? extends Bedrijf> filteredBedrijfList;
	private B2BPortaal b2bPortaal = new B2BPortaal();
	private ObservableList<BulkOpsEnum> bulkOps = FXCollections.observableArrayList();
	private ObservableList<Enum> actiesList = FXCollections.observableArrayList();
	private BulkOpsEnum huidigeBulkOpsEnum;
	private Enum huidigeActie;

	private void setBedrijvenList(ObservableList<? extends Bedrijf> observableList) {
		this.bedrijfList = observableList;
		this.filteredBedrijfList = new FilteredList<>(bedrijfList, p -> true);
	}


	@Override
	public ObservableList<? extends Bedrijf> getAlleBedrijven() {
		setBedrijvenList(b2bPortaal.getAlleBedrijven());
		return this.filteredBedrijfList;
	}

	@Override
	public ControllerSoortEnum getControllerSoort() {
		return ControllerSoortEnum.ADMINISTRATOR;
	}

	@Override
	public Bedrijf getBedrijf(long bedrijfId) {
		return this.b2bPortaal.getBedrijfById(bedrijfId);
	}

	@Override
	public void addBedrijf(B2BBedrijfDTO bedrijfDTO) throws RegistreerException {
		Adres adres = null;
		try {
			adres = new Adres(bedrijfDTO.adres);
		} catch (RegistreerException e) {
		}

		B2BBedrijf bedrijf = new B2BBedrijf(bedrijfDTO, adres);
		this.b2bPortaal.addBedrijf(bedrijf);
	}

	@Override
	public void verwijderBedrijf(long id) {
		this.b2bPortaal.deleteBedrijfById(id);
	}

	@Override
	public void selecteerBedrijf(int bedrijfId) {
		this.huidigBedrijf = (B2BBedrijf) this.getBedrijf(bedrijfId);
	}

	@Override
	public Bedrijf getHuidigBedrijf() {
		return this.huidigBedrijf;
	}

	@Override
	public ObservableList<BedrijfSectorEnum> getAlleSectoren() {
		return this.sectoren;
	}

	@Override
	public void wijzigSectorHuidigBedrijf(BedrijfSectorEnum sector) {
		this.b2bPortaal.getBedrijfById(this.huidigBedrijf.getId());
		JPAUtil.beginTransaction();
		this.huidigBedrijf.wijzigSector(sector);
		JPAUtil.commitTransaction();
	}

	@Override
	public void wijzigHuidigBedrijfIsActief() {
		JPAUtil.beginTransaction();
		this.huidigBedrijf.wijzigIsActief();
		JPAUtil.commitTransaction();
	}

	@Override
	public void changeBedrijfListFilter(B2BBedrijfDTO filter) {
		if (filter == null) {
			throw new IllegalArgumentException("Er werd geen bedrijffilter meegegeven");
		}
//		filteredBedrijfList.setPredicate(bedrijf -> bedrijf.isInFilter(filter));
		filteredBedrijfList.setPredicate(PredicateFactory.createBedrijfFilterPredicate(filter));
	}

	@Override
	public void setHuidigBedrijf(Bedrijf b) {
		this.huidigBedrijf = (B2BBedrijf) b;
	}

	@Override
	public void voegGebruikerToeHuidigBedrijf(GebruikerDTO gebruiker) throws RegistreerGebruikerException {
		if(!(gebruiker.rol.equals(GebruikerRolEnum.LEVERANCIER) || gebruiker.rol.equals(GebruikerRolEnum.KLANT)))
			throw new IllegalArgumentException("Gebruiker rol moet leverancier of klant zijn");
		Gebruiker nieuweGebruiker = GebruikerFactory.maakGebruiker(gebruiker, this.huidigBedrijf);
		this.b2bPortaal.addGebruiker(nieuweGebruiker);
	}

	@Override
	public void wijzigBedrijfIsActief(Bedrijf bedrijf) {
		JPAUtil.beginTransaction();
		if(bedrijf instanceof B2BBedrijf b2bBedrijf)
			b2bBedrijf.wijzigIsActief();
		else
			System.err.println("Iets ging mis bij wijzigen bedrijf is actief status");
		JPAUtil.commitTransaction();
	}


	@Override
	public ObservableList<BulkOpsEnum> getBulkOpsBedrijvenList() {
		this.bulkOps.clear();
		List<BulkOpsEnum> bulkOpsList = BulkOpsEnumFactory.createBulkOpsEnumListVoorBedrijven();
		this.bulkOps.addAll(bulkOpsList);
		return this.bulkOps;
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
			List<? extends Enum> acties = ActieEnumFactory.createEnumListVoorBulkOpsBedrijven(bulkOps);
			this.actiesList.addAll(acties);
		}
	}

	@Override
	public void setHuidigeActie(Enum actie) {
		if(actie != null) {
			this.huidigeActie = actie;
		}
	}

	@Override
	public void voerActieUitOpBedrijven(List<Bedrijf> bedrijven) {
		ListIterator<Bedrijf> iterator = bedrijven.listIterator();
		JPAUtil.beginTransaction();
		while(iterator.hasNext()) {
			if(iterator.next() instanceof B2BBedrijf b2bBedrijf) {
				Function<B2BBedrijf, ConcreteCommand<B2BBedrijf>> command = BedrijfCommandFactory.createBedrijfCommand(huidigeBulkOpsEnum, huidigeActie);
				command.apply(b2bBedrijf).execute();
			}
		}
		JPAUtil.commitTransaction();
	}

	@Override
	public ObservableList<BedrijfSectorEnum> getAlleBedrijfSectoren() {
		ObservableList<BedrijfSectorEnum> bedrijfSectoren = FXCollections.observableArrayList();
		bedrijfSectoren.addAll(List.of(BedrijfSectorEnum.values()));
		return bedrijfSectoren;
	}
	
	@Override
	public boolean isWachtwoordGewijzigd() {
		return true;
	}
	
	@Override
	public void searchBedrijf(String query) {
		this.filteredBedrijfList.setPredicate(PredicateFactory.createBedrijfSearchPredicate(query));
	}
	
	@Override
	public void changeKlantBestellingListFilter(BestellingDTO bestellingFilterDTO) {
		// TODO
	}
}
