package gui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

import controllers.Controller;
import domain.Bestelling;
import dto.BestellingDTO;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import utils.BetaalStatusEnum;
import utils.BulkOpsEnum;

public class BestellingenOverzichtController extends VBox implements Initializable {
	Controller controller;
	BestellingDTO filterDTO;
	VBox sideWindow;
	@FXML
	private TableView<Bestelling> bestellingOverzichtTableView;

	@FXML
	private TableColumn<Bestelling, Boolean> selectCol;

	@FXML
	private TableColumn<Bestelling, String> uuidCol;

	@FXML
	private TableColumn<Bestelling, String> bestelDatumCol;

	@FXML
	private TableColumn<Bestelling, String> klantCol;

	@FXML
	private TableColumn<Bestelling, BetaalStatusEnum> betaaldCol;

	@FXML
	private Button btnVerwerkBetalingen;

	@FXML
	private TextField zoekBestellingInput;

	@FXML
	private Button btnZoekBestelling;

	@FXML
	private TextField idFilter;

	@FXML
	private TextField bestelDatumFilter;

	@FXML
	private TextField klantFilter;

	@FXML
	private ChoiceBox<BetaalStatusEnum> cbxBetaalStatus;

	ObservableList<BetaalStatusEnum> statusBetaling = FXCollections.observableArrayList(BetaalStatusEnum.values());

	@FXML
	private Button btnFllterBetaalStatus;

	@FXML
	private Button btnBetalingsherinnering;

	@FXML
	private Button btnVerstuur;

	@FXML
	private Button btnAnnuleer;

	@FXML
	private Button btnVoerActieUit;

	@FXML
	private ComboBox<Enum> cbxActies;

	@FXML
	private ComboBox<BulkOpsEnum> cbxBulkOps;

	private List<Bestelling> geselecteerdeBestellingen = new ArrayList<>();

	private GuiController guiController;
	private Set<Bestelling> selectedBestellingen = new HashSet<>();

	public BestellingenOverzichtController(GuiController guiController, Controller controller) {
		this.guiController = guiController;
		this.controller = controller;
		filterDTO = new BestellingDTO();
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("BestellingenOverzicht.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		ObservableList<Bestelling> bestellingen = controller.getBestellingenList();
		selectCol.setCellFactory(column -> new CheckBoxTableCell<>());
		selectCol.setCellValueFactory(cellData -> {
			Bestelling bestelling = cellData.getValue();
			BooleanProperty property = new SimpleBooleanProperty(false);

			property.addListener((observable, oldValue, newValue) -> {
				if (newValue) {
					selectedBestellingen.add(bestelling);
				} else {
					selectedBestellingen.remove(bestelling);
				}
			});

			return property;
		});
		cbxBetaalStatus.setItems(this.statusBetaling);
		
		cbxBetaalStatus.getItems().add(0, null);
//		selectCol.setCellValueFactory(el -> new SimpleBooleanProperty(false));
//		selectCol.setCellFactory(CheckBoxTableCell.forTableColumn(selectCol));
		uuidCol.setCellValueFactory(cellData -> cellData.getValue().uuidProperty().map(uuid -> {
					String[] uuidElements = uuid.toString().split("-");
					return uuidElements[uuidElements.length -1];
		}));		
		bestelDatumCol.setCellValueFactory(cellData -> cellData.getValue().datumProperty().map(date -> String.format("%d/%d/%d", date.getDayOfMonth(), date.getMonthValue(), date.getYear())));
		klantCol.setCellValueFactory(cellData -> cellData.getValue().klantNaamProperty());
		betaaldCol.setCellValueFactory(cellData -> cellData.getValue().betaalStatusProperty()); // cellData.getValue().isBetaaldProperty());

		bestellingOverzichtTableView.setItems(bestellingen);
		bestellingOverzichtTableView.getSelectionModel().selectedItemProperty()
				.addListener((observableValue, oldBestelling, newBestelling) -> {
					if (newBestelling != null) {
						this.controller.setHuidigeBestelling(newBestelling);
						this.initializeSideScreen();
					}
				});
		cbxBulkOps.setItems(controller.getBulkOpsBestellingenList());
		cbxActies.setItems(controller.getActiesList());

		SortedList<Bestelling> sortedBestellingen = new SortedList<>(bestellingen);
		bestellingOverzichtTableView.setItems(sortedBestellingen);
		sortedBestellingen.comparatorProperty().bind(bestellingOverzichtTableView.comparatorProperty());
	}

	@FXML
	void handleClickBestellingenView(MouseEvent event) throws IOException {
		Bestelling selectedBestelling = bestellingOverzichtTableView.getSelectionModel().getSelectedItem();
		controller.setHuidigeBestelling(selectedBestelling);
		initializeSideScreen();
	}

	private void initializeSideScreen() {
		BestellingDetailsController bdc = new BestellingDetailsController(guiController, controller);
		guiController.setSideWindow(bdc);
	}

	@FXML
	void filterKlant(MouseEvent event) {
		clearFilter();
		filterDTO.klantNaam = klantFilter.getText();
		controller.changeBestellingListFilter(filterDTO);
		bestellingOverzichtTableView.refresh();
	}

	@FXML
	void filterBesteldatum(MouseEvent event) {
		clearFilter();
//		filterDTO.bestelDatum = bestelDatumFilter.getText();
		controller.changeBestellingListFilter(filterDTO);
		bestellingOverzichtTableView.refresh();
	}

	@FXML
	void filterBetaalStatus(MouseEvent event) {
		clearFilter();
		filterDTO.betaalStatus = cbxBetaalStatus.getValue();
		controller.changeBestellingListFilter(filterDTO);
		bestellingOverzichtTableView.refresh();
	}

	@FXML
	void filterId(MouseEvent event) {
		clearFilter();
//		filterDTO.id = idFilter.getText();
		filterDTO.uuidString = idFilter.getText();
		controller.changeBestellingListFilter(filterDTO);
		bestellingOverzichtTableView.refresh();
	}

	@FXML
	void zoekBestelling(MouseEvent event) {
		clearFilter();
		String query = this.zoekBestellingInput.getText();
		controller.searchBestellingList(query);
//		bestellingOverzichtTableView.refresh();
	}

	private void clearFilter() {
//		filterDTO.search = null;
//		filterDTO.bestelDatum = null;
		filterDTO.id = null;
		filterDTO.klantNaam = null;
		filterDTO.betaalStatus = null;
	}

	@FXML
	void verwerkBetalingen(MouseEvent event) {
		controller.verwerkBetalingen();
	}

	@FXML
	void handleSelecteerBestellingen(ActionEvent event) {
		bestellingOverzichtTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		btnBetalingsherinnering.setStyle("-fx-background-color:  E19693;");
		bestellingOverzichtTableView.setStyle("-fx-border-color:  E19693; " + "-fx-border-width: 2px; "
				+ "-fx-border-radius: 5px; " + "-fx-background-color: #FFFFFF;" + "-fx-selection-bar: E19693;");
		btnVerstuur.setVisible(true);
		btnAnnuleer.setVisible(true);
	}

	@FXML
	void handleStuurBetalingsherinnering(ActionEvent event) {
		ObservableList<Bestelling> geselecteerdeItems = bestellingOverzichtTableView.getSelectionModel()
				.getSelectedItems();

		if (geselecteerdeItems.isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Geen bestelling geselecteerd");
			alert.setHeaderText(null);
			alert.setContentText(
					"Er is geen bestelling geselecteerd. Selecteer minstens 1 bestelling om een betalingsherinnering te sturen, of annuleer!");
			alert.showAndWait();
		}

		geselecteerdeBestellingen.clear();
		geselecteerdeBestellingen.addAll(geselecteerdeItems);

		for (Bestelling bestelling : geselecteerdeBestellingen) {
			bestelling.stuurBetalingsHerinnering();
		}

		btnBetalingsherinnering.setStyle("");
		bestellingOverzichtTableView.setStyle("-fx-border-color: transparent; -fx-background-color: transparent;");
		btnBetalingsherinnering.setStyle("");
		btnVerstuur.setVisible(false);
		btnAnnuleer.setVisible(false);
	}

	@FXML
	void handleAnnuleren(ActionEvent event) {
		bestellingOverzichtTableView.getSelectionModel().clearSelection();
		btnBetalingsherinnering.setStyle("");
		bestellingOverzichtTableView.setStyle("-fx-border-color: transparent; -fx-background-color: transparent;");
		btnAnnuleer.setVisible(false);
		btnVerstuur.setVisible(false);
	}

	@FXML
	void onBtnVoerActieUit(ActionEvent event) {
		List<Bestelling> bestellingen = selectedBestellingen.stream().toList();
		System.out.println(bestellingen);
		controller.voerActieUitOpBestellingen(bestellingen);
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
    	this.cbxActies.setDisable(bulkOps == null);
    }

}
