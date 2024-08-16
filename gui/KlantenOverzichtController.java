package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import controllers.Controller;
import domain.Bedrijf;
import dto.B2BBedrijfDTO;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
//import dto.KlantFilterDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class KlantenOverzichtController extends VBox implements Initializable {
	@FXML
	private TextField txtfldZoekKlant;
	@FXML
	private Button btnZoekKlant;
	@FXML
	private TableView<Bedrijf> klantenOverzichtTableView;
	@FXML
	private TableColumn<Bedrijf, String> naamCol;
	@FXML
	private TextField txtfldNaamColFilter;
	@FXML
	private Button btnNaamColSortAsc;
	@FXML
	private Button btnNaamColSortDesc;
	@FXML
	private TableColumn<Bedrijf, String> adresCol;
	@FXML
	private TextField txtfldAdresColFilter;
	@FXML
	private Button btnAdresColSortAsc;
	@FXML
	private Button btnAdresColSortDesc;
	@FXML
	private TableColumn<Bedrijf, Integer> aantalOpenstaandeBestellingenCol;
	@FXML
	private TextField txtfldAantalOpenstaandeBestellingenColFilter;
	@FXML
	private Button btnAantalOpenstaandeBestellingenColSortAsc;
	@FXML
	private Button btnAantalOpenstaandeBestellingenColSortDesc;
	@FXML
	private TableColumn<Bedrijf, String> telefoonNummerCol;
	@FXML
	private TextField txtfldTelefoonnummerColFilter;
	@FXML
	private Button btnSectorColSortAsc;
	@FXML
	private Button btnSectorColSortDesc;

	private final Controller controller;
	// TODO
//	private final KlantFilterDTO klantFilterDTO;

	private GuiController guiController;

	public KlantenOverzichtController(GuiController guiController, Controller controller) {
		this.controller = controller;
		this.guiController = guiController;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("KlantenOverzicht.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// TODO
//		this.klantFilterDTO = new KlantFilterDTO();

		// cell value factories

//		 aantalOpenstaandeBestellingenCol.setCellFactory(TextFieldTableCell.forTableColumn());

		// sectorCol.setCellValueFactory(cellData -> cellData.getValue().getSector());
		// sectorCol.setCellFactory(TextFieldTableCell.forTableColumn());

		// change listener voor geselecteerde rij
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		ObservableList<Bedrijf> klanten =  controller.getKlantenList();

		naamCol.setCellValueFactory(cellData -> cellData.getValue().naamProperty());
		adresCol.setCellValueFactory(cellData -> cellData.getValue().adresProperty());
		telefoonNummerCol.setCellValueFactory(cellData -> cellData.getValue().telefoonnummerProperty());
		aantalOpenstaandeBestellingenCol.setCellValueFactory(cellData ->
		 cellData.getValue().aantalOpenstaandeBestellingenUitgaandProperty(controller.getHuidigeLeverancier().getBedrijf().getUuid()).asObject());
		adresCol.setCellValueFactory(cellData -> cellData.getValue().getKortAdres());

		klantenOverzichtTableView.setItems(klanten);
		klantenOverzichtTableView.getSelectionModel().selectedItemProperty()
				.addListener((observableValue, oldKlant, newKlant) -> {
					if (newKlant != null) {
						this.controller.setHuidigeKlant(newKlant);
						this.initializeSideScreen();
					}
				});
		SortedList<Bedrijf> sortedKlanten = new SortedList<>(klanten);
		klantenOverzichtTableView.setItems(sortedKlanten);
		sortedKlanten.comparatorProperty().bind(klantenOverzichtTableView.comparatorProperty());
	}

	private void initializeSideScreen() {
		this.guiController.setSideWindow(new KlantDetailsController(guiController, controller));
	}

	@FXML
	private void handleClickKlantenView(MouseEvent event) {
		Bedrijf selectedKlant = klantenOverzichtTableView.getSelectionModel().getSelectedItem();
		controller.setHuidigeKlant(selectedKlant);
		initializeSideScreen();
	}

	@FXML
	public void onBtnZoekKlantClick(ActionEvent event) {
		String searchQuery = txtfldZoekKlant.getText();
		controller.searchKlantList(searchQuery);
	}

	// Event Listener on TextField[#txtfldNaamColFilter].onInputMethodTextChanged
	@FXML
	public void onNaamColFilterChange(InputMethodEvent event) {
		String filterQuery = txtfldNaamColFilter.getText();
//		klantFilterDTO.naam = filterQuery;
		// TODO
//		controller.changeKlantListFilter(klantFilterDTO);
	}

	// Event Listener on Button[#btnNaamColSortAsc].onAction
	@FXML
	public void onBtnNaamColSortAscClick(ActionEvent event) {
		controller.sortKlantenList(Controller.BY_KLANT_NAAM_ASC);
	}

	@FXML
	public void onBtnNaamColSortDescClick(ActionEvent event) {
		controller.sortKlantenList(Controller.BY_KLANT_NAAM_DESC);
	}

	@FXML
	public void onAdresColFilterChange(InputMethodEvent event) {
		String filterQuery = txtfldAdresColFilter.getText();
		// TODO
//		klantFilterDTO.adres = filterQuery;
//		controller.changeKlantListFilter(klantFilterDTO);
	}

	@FXML
	public void OnBtnAdresColSortAscClick(ActionEvent event) {
		controller.sortKlantenList(Controller.BY_KLANT_ADRES_ASC);
	}

	@FXML
	public void onBtnAdresColSortDescClick(ActionEvent event) {
		controller.sortKlantenList(Controller.BY_KLANT_ADRES_DESC);
	}

	@FXML
	public void onAantalOpenstaandeBestellingenColFilter(InputMethodEvent event) {
//		String filterQuery = txtfldAantalOpenstaandeBestellingenColFilter.getText();
	}

	@FXML
	public void onBtnAantalOpenstaandeBestellingenColSortAscClick(ActionEvent event) {
	}

	@FXML
	public void onBtnAantalOpenstaandeBestellingenColSortDesc(ActionEvent event) {
	}

	@FXML
	public void onSectorColFilter(InputMethodEvent event) {
//		String filterQuery = txtfldSectorColFilter.getText();
	}

	@FXML
	public void onBtnSectorColSortAscClick(ActionEvent event) {
	}

	@FXML
	public void onBtnSectorColSortDescClick(ActionEvent event) {
	}
	
	@FXML
	public void filter(ActionEvent event) {
		B2BBedrijfDTO dto = new B2BBedrijfDTO();
		dto.naam = txtfldNaamColFilter.getText();
		dto.adresString = txtfldAdresColFilter.getText();
		dto.aantalOpenstaandeBestellingen = txtfldAantalOpenstaandeBestellingenColFilter.getText();
		dto.telefoonnummer = txtfldTelefoonnummerColFilter.getText();
		controller.changeKlantListFilter(dto);
	}

}
