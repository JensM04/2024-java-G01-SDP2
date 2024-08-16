package gui;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import controllers.Controller;
import domain.Bedrijf;
import domain.Bestelling;
import dto.B2BBedrijfDTO;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import utils.BedrijfSectorEnum;
import utils.BulkOpsEnum;

public class BedrijvenOverzichtController extends VBox implements Initializable {
	Controller controller;
	B2BBedrijfDTO filterDTO;
	VBox sideWindow;

	@FXML
	private TableView<Bedrijf> bedrijfOverzichtTableView;

    @FXML
    private TableColumn<Bedrijf, Boolean> selectCol;

	@FXML
	private TableColumn<Bedrijf, String> naamCol;

	@FXML
	private TableColumn<Bedrijf, String> adresCol;

	@FXML
	private TableColumn<Bedrijf, Integer> aantalBestellingenCol;

	@FXML
	private TableColumn<Bedrijf, BedrijfSectorEnum> sectorCol;

	@FXML
	private TableColumn<Bedrijf, Boolean> isActiefCol;

	@FXML
	private TextField zoekBedrijfInput;
	@FXML
	private Button btnZoekBedrijf;

	@FXML
	private TextField naamFilter;

	@FXML
	private TextField adresFilter;

	@FXML
	private TextField aantalBestellingenfilter;

	@FXML
	private Button btnFilterNaam;

	@FXML
	private Button btnFilterAdres;

	@FXML
	private Button btnFilterAantalBestellingen;

    @FXML
    private Button btnVoerActieUit;

    @FXML
    private Button btnVoegBedrijfToe;

	@FXML
	private ComboBox<BedrijfSectorEnum> cbxSector;

	@FXML
    private ComboBox<Enum> cbxActies;

    @FXML
    private ComboBox<BulkOpsEnum> cbxBulkOps;

	private Set<Bedrijf> selectedBedrijven = new HashSet<>();

	private GuiController guiController;


	ObservableList<BedrijfSectorEnum> sectorBedrijf = FXCollections.observableArrayList(BedrijfSectorEnum.values());

	public BedrijvenOverzichtController(GuiController guiController, Controller controller) {
		this.controller = controller;
		this.guiController = guiController;
		filterDTO = new B2BBedrijfDTO();

		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("BedrijvenOverzicht.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		ObservableList<? extends Bedrijf> bedrijven = controller.getAlleBedrijven();
		selectCol.setCellFactory(column -> new CheckBoxTableCell<>());
        selectCol.setCellValueFactory(cellData -> {
            Bedrijf bedrijf = cellData.getValue();
            BooleanProperty property = new SimpleBooleanProperty(false);

            property.addListener((observable, oldValue, newValue) -> {
            	if(newValue) {
            		selectedBedrijven.add(bedrijf);
            	} else {
            		selectedBedrijven.remove(bedrijf);
            	}
            });

            return property;
        });
		naamCol.setCellValueFactory(cellData -> cellData.getValue().naamProperty());
		adresCol.setCellValueFactory(cellData -> cellData.getValue().getKortAdres());
		aantalBestellingenCol.setCellValueFactory(
				cellData -> cellData.getValue().aantalOpenstaandeBestellingenInkomendProperty().asObject());
		sectorCol.setCellValueFactory(cellData -> cellData.getValue().sectorProperty());
        isActiefCol.setCellValueFactory(cellData -> cellData.getValue().isActiefProperty());
        isActiefCol.setCellFactory(CheckBoxTableCell.forTableColumn(isActiefCol));

		SortedList<Bedrijf> sortedBedrijven = new SortedList<>(bedrijven);
		bedrijfOverzichtTableView.setItems(sortedBedrijven);
		sortedBedrijven.comparatorProperty().bind(bedrijfOverzichtTableView.comparatorProperty());
		bedrijfOverzichtTableView.getSelectionModel().selectedItemProperty()
		.addListener((observableValue, oldBedrijf, newBedrijf) -> {
			if (newBedrijf != null) {
				this.controller.setHuidigBedrijf(newBedrijf);
				this.initializeSideScreen();
			}
		});
		cbxSector.setItems(sectorBedrijf);

		cbxBulkOps.setItems(controller.getBulkOpsBedrijvenList());
		cbxActies.setItems(controller.getActiesList());
	}

	@FXML
	void handleClickBedrijvenView(MouseEvent event) {
		Bedrijf selectedBedrijf = bedrijfOverzichtTableView.getSelectionModel().getSelectedItem();
		controller.setHuidigBedrijf(selectedBedrijf);
		initializeSideScreen();
	}

	public void toonVoegAccountToe() {

	}

	private void initializeSideScreen() {
		BedrijfDetailsController bdc = new BedrijfDetailsController(guiController, controller);
		guiController.setSideWindow(bdc);
	}

	@FXML
	void zoekBedrijf(MouseEvent event) {
		String search = zoekBedrijfInput.getText();
		controller.searchBedrijf(search);
	}

	@FXML
	void filterNaam(MouseEvent event) {
		clearFilter();
		filterDTO.naam = naamFilter.getText();
		controller.changeBedrijfListFilter(filterDTO);
		bedrijfOverzichtTableView.refresh();
	}

	@FXML
	void filterAdres(MouseEvent event) {
		clearFilter();
		filterDTO.kortAdres = adresFilter.getText();
		controller.changeBedrijfListFilter(filterDTO);
		bedrijfOverzichtTableView.refresh();
	}

	@FXML
	void filterOpenstaandeBestellingen(MouseEvent event) {
		clearFilter();
		filterDTO.aantalOpenstaandeBestellingen = aantalBestellingenfilter.getText();
		controller.changeBedrijfListFilter(filterDTO);
		bedrijfOverzichtTableView.refresh();
	}

	@FXML
	void filterSector() {
		clearFilter();
		filterDTO.sector = cbxSector.getValue();
		controller.changeBedrijfListFilter(filterDTO);
		bedrijfOverzichtTableView.refresh();
	}

	private void clearFilter() {
		filterDTO.naam = null;
		filterDTO.adres = null;
		filterDTO.sector = null;
		filterDTO.aantalOpenstaandeBestellingen = null;
		filterDTO.search = null;
	}


    @FXML
    void onBtnVoerActieUit(ActionEvent event) {
    	List<Bedrijf> bedrijven = selectedBedrijven.stream().toList();
    	controller.voerActieUitOpBedrijven(bedrijven);
    }



    @FXML
    void onCbxActies(ActionEvent event) {
    	Enum actie = cbxActies.getSelectionModel().getSelectedItem();
    	if(actie != null) {
    		this.btnVoerActieUit.setDisable(false);
    	} else {
    		this.btnVoerActieUit.setDisable(true);
    	}
    	controller.setHuidigeActie(actie);
    }

    @FXML
    void onCbxBulkOps(ActionEvent event) {
    	this.btnVoerActieUit.setDisable(true);
    	BulkOpsEnum bulkOps = cbxBulkOps.getSelectionModel().getSelectedItem();
    	controller.setHuidigeBulkOps(bulkOps);
    	System.out.println("BestellingenOverzichtController -> test cbxBulkOps");
    	this.cbxActies.setDisable(bulkOps == null);
    }


    @FXML
    void onBtnVoegBedrijfToe(ActionEvent event) {
    	this.guiController.setSideWindow(new BedrijfToevoegenController(guiController, controller));
    	this.bedrijfOverzichtTableView.getItems().forEach(el -> System.out.println(el.getNaam()));
    }



}
