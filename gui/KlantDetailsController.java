package gui;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.UUID;

import controllers.Controller;
import domain.Bedrijf;
import domain.Bestelling;
import domain.Betaling;
import dto.BestellingDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.VBox;
import utils.BetaalStatusEnum;

public class KlantDetailsController extends VBox implements Initializable {
	@FXML
	private TitledPane titledPaneKlantgegevens;
	@FXML
	private ImageView imgViewVarLogo;
	@FXML
	private Label lblVarBedrijfsnaam;
	@FXML
	private TitledPane titledPaneContactgegevens;
	@FXML
	private Label hyperlinkVarTelefoonnummer;
	@FXML
	private Hyperlink hyperlinkVarWebsite;
	@FXML
	private Hyperlink hyperlinkVarEmail;
	@FXML
	private TitledPane titledPaneAdres;
	@FXML
	private Label lblVarPostcode;
	@FXML
	private Label lblVarGemeente;
	@FXML
	private Label lblVarHuisnummer;
	@FXML
	private Label lblVarStraat;
	@FXML
	private TableView<Bestelling> tableBestellingenKlant;
	@FXML
	private TableColumn<Bestelling, String> colBestelID;
	@FXML
	private Label lblBestelId;
	@FXML
	private TextField txtfldBestelIdFilter;
	@FXML
	private Button btnBestelIdSortAsc;
	@FXML
	private Button btnBestelIdSortDesc;
	@FXML
	private TableColumn<Bestelling, String> colBesteldatum;
	@FXML
	private Label lblBesteldatum;
	@FXML
	private TextField txtfldBesteldatumFilter;
	@FXML
	private Button btnBesteldatumSortAsc;
	@FXML
	private Button btnBesteldatumSortDesc;
	@FXML
	private TableColumn<Bestelling, String> colTotaalbedrag;
	@FXML
	private Label lblTotaalbedrag;
	@FXML
	private TextField txtfldTotaalbedragFilter;
	@FXML
	private Button btnTotaalbedragSortAsc;
	@FXML
	private Button btnTotaalbedragSortDesc;
	@FXML
	private TableColumn<Bestelling, String> colStatus;
	@FXML
	private Label lblStatus;
	@FXML
	private TextField txtfldStatusFilter;
	@FXML
	private Button btnStatusSortAsc;
	@FXML
	private Button btnStatusSortDesc;
	@FXML
	private TableColumn<Bestelling, BetaalStatusEnum> colIsBetaald;
	@FXML
	private Label lblIsBetaald;
	@FXML
	private TextField txtfldIsBetaaldFilter;
	@FXML
	private Button btnIsBetaaldSortAsc;
	@FXML
	private Button btnIsBetaaldSortDesc;
	@FXML
	private Label lblVarBus;
	@FXML
	private TableView<Betaling> tblBetalingen;

	@FXML
	private TableColumn<Betaling, UUID> colBestellingId;

	@FXML
	private TableColumn<Betaling, BigDecimal> colTeBetalen;

	@FXML
	private TableColumn<Betaling, BigDecimal> colBetaaldeBedrag;

	@FXML
	private TableColumn<Betaling, Boolean> colVerwerkt;

	@FXML
	private TableColumn<Betaling, Boolean> colGoedgekeurd;

	private final Controller controller;
	private final BestellingDTO bestellingFilterDTO;
	private final Bedrijf klant;
	private GuiController guiController;

	public KlantDetailsController(GuiController guiController, Controller controller) {
		this.guiController = guiController;
		this.controller = controller;

		this.bestellingFilterDTO = new BestellingDTO();
		this.klant = controller.getHuidigeKlant();

		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("KlantDetails.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void onBestelIdFilterChange(InputMethodEvent event) {
		String filterQuery = txtfldBestelIdFilter.getText();
		this.bestellingFilterDTO.uuidString = filterQuery;
		controller.changeKlantBestellingListFilter(bestellingFilterDTO);
	}

	@FXML
	public void onBtnBestelIdSortAsc(ActionEvent event) {
//		controller.changeKlantBestellingListSort(Controller.BY_BESTELLING_ID_ASC);
	}

	@FXML
	public void onBtnBestelIdSortDesc(ActionEvent event) {
//		controller.changeKlantBestellingListSort(Controller.BY_BESTELLING_ID_DESC);
	}

	@FXML
	public void onBesteldatumFilterChange(InputMethodEvent event) {
		String filterQuery = txtfldBestelIdFilter.getText();
//		this.bestellingFilterDTO.bestelDatum = filterQuery;
		controller.changeKlantBestellingListFilter(bestellingFilterDTO);
	}

	@FXML
	public void onBtnBesteldatumSortAsc(ActionEvent event) {
		controller.changeKlantBestellingListSort(Controller.BY_BESTELDATUM_ASC);
	}

	@FXML
	public void onBtnBesteldatumSortDesc(ActionEvent event) {
		controller.changeKlantBestellingListSort(Controller.BY_BESTELDATUM_DESC);
	}

	@FXML
	public void onTotaalbedragFilterChange(InputMethodEvent event) {
		String filterQuery = txtfldBestelIdFilter.getText();
//		this.bestellingFilterDTO.totaalBedrag = filterQuery;
		controller.changeKlantBestellingListFilter(bestellingFilterDTO);
	}

	@FXML
	public void onBtnTotaalbedragSortAsc(ActionEvent event) {
		controller.changeKlantBestellingListSort(Controller.BY_TOTAAL_BEDRAG_ASC);
	}

	@FXML
	public void onBtnTotaalbedragSortDesc(ActionEvent event) {
		controller.changeKlantBestellingListSort(Controller.BY_TOTAAL_BEDRAG_DESC);
	}

	@FXML
	public void onStatusFilterChange(InputMethodEvent event) {
		String filterQuery = txtfldBestelIdFilter.getText();
//		this.bestellingFilterDTO.status = filterQuery;
		controller.changeKlantBestellingListFilter(bestellingFilterDTO);
	}

	@FXML
	public void onBtnStatusSortAsc(ActionEvent event) {
		controller.changeKlantBestellingListSort(Controller.BY_STATUS_ASC);
	}

	@FXML
	public void onBtnStatusSortDesc(ActionEvent event) {
		controller.changeKlantBestellingListSort(Controller.BY_STATUS_DESC);
	}

	@FXML
	public void onIsBetaaldFilterChange(InputMethodEvent event) {
		String filterQuery = txtfldBestelIdFilter.getText();
//		this.bestellingFilterDTO.isBetaald = filterQuery;
		controller.changeKlantBestellingListFilter(bestellingFilterDTO);
	}

	@FXML
	public void onBtnIsBetaaldSortAsc(ActionEvent event) {
//		controller.changeKlantBestellingListSort(Controller.BY_IS_BETAALD_ASC);
	}

	// Event Listener on Button[#btnIsBetaaldSortDesc].onAction
	@FXML
	public void onBtnIsBetaaldSortDesc(ActionEvent event) {
//		controller.changeKlantBestellingListSort(Controller.BY_IS_BETAALD_DESC);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		tableBestellingenKlant.setItems(controller.getBestellingenListHuidigeKlant());
		colBestelID
				.setCellValueFactory(cellData -> new SimpleStringProperty(String.format("%s", cellData.getValue().getUuid().toString())));
		colBesteldatum.setCellValueFactory(
				cellData -> {
					LocalDateTime d = cellData.getValue().getBestellingDatum();
					return new SimpleStringProperty(String.format("%d/%d/%d", d.getDayOfMonth(), d.getMonthValue(), d.getYear()));
				});
		colTotaalbedrag.setCellValueFactory(cellData -> new SimpleStringProperty(String.format("%.2f", cellData.getValue().getTotaalBestellingbedragMetBTW().floatValue())));
		colIsBetaald.setCellValueFactory(cellData -> cellData.getValue().betaalStatusProperty());
		colStatus.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getBestellingStatus().toString()));

		lblVarBedrijfsnaam.setText(klant.getNaam());
		lblVarGemeente.setText(klant.getAdres().getGemeente());
		lblVarHuisnummer.setText(String.format("%d", klant.getAdres().getHuisnummer()));
		lblVarBus.setText(klant.getAdres().getBus());
		lblVarPostcode.setText(String.format("%d", klant.getAdres().getPostcode()));
		lblVarStraat.setText(klant.getAdres().getStraat());
		hyperlinkVarEmail.setText(klant.getEmail());
		hyperlinkVarTelefoonnummer.setText(klant.getTelefoonnummer());
		hyperlinkVarWebsite.setText(klant.getWebsiteURL());
		tableBestellingenKlant.getSelectionModel().selectedItemProperty()
				.addListener((observableValue, oldBestelling, newBestelling) -> {
					if (newBestelling != null) {
						int index = tableBestellingenKlant.getSelectionModel().getSelectedIndex();
						this.controller.selecteerHuidigeKlant(index);
					}
				});
		colBestellingId.setCellValueFactory(cellData -> cellData.getValue().bestellingUuidProperty());
		colTeBetalen.setCellValueFactory(cellData -> cellData.getValue().teBetalenProperty());
		colBetaaldeBedrag.setCellValueFactory(cellData -> cellData.getValue().betaaldeBedragProperty());
		colVerwerkt.setCellFactory(CheckBoxTableCell.forTableColumn(colVerwerkt));
		colVerwerkt.setCellValueFactory(cellData -> cellData.getValue().isVerwerktProperty());
		colGoedgekeurd.setCellFactory(CheckBoxTableCell.forTableColumn(colGoedgekeurd));
		colGoedgekeurd.setCellValueFactory(cellData -> cellData.getValue().isGoedgekeurdProperty());
		
		String linkAfbeelding = String.format("/images/%s.png", this.klant.getNaam());
		imgViewVarLogo.setFitHeight(110);
		imgViewVarLogo.setPreserveRatio(true);
//		imgViewVarLogo.setImage(new Image(this.getClass().getResourceAsStream(linkAfbeelding)));
		String imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRfE4QxcUZhYJuAKGSySAL06PwVK9FBp_hKkA&s";
		imgViewVarLogo.setImage(new Image(imageUrl));
		
		tblBetalingen.setItems(this.controller.getBetalingenKlant(this.klant));
	}
	
	
}
