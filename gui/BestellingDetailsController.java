package gui;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.UUID;

import controllers.Controller;
import domain.Adres;
import domain.Bedrijf;
import domain.Bestelling;
import domain.Betaling;
import domain.ProductInBestellingInterface;
import dto.BestellingDTO;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import utils.BestellingStatusEnum;
import utils.BetaalStatusEnum;

public class BestellingDetailsController extends VBox implements Initializable {

	@FXML
	private TitledPane titledPaneBestellingnummer;
	@FXML
	private Label lblVarBestellingID;
	@FXML
	private Label lblVarBesteldatum;
	@FXML
	private Label lblVarDatumLaatsteHerinnering;
	@FXML
	private Label totaalbedrag;
	@FXML
	private Label lblVarTotaalbedrag;
	@FXML
	private Label lblVarTotaalbedragInclBTW;
	@FXML
	private CheckBox chkIsBetaald;
	@FXML
	private Button btnWijzigBestelling;
	@FXML
	private Button btnStuurHerinnering;
	@FXML
	private ChoiceBox<BestellingStatusEnum> cbxStatusBestelling;
	@FXML
	private ChoiceBox<BetaalStatusEnum> cbxBetaalStatus;

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

	ObservableList<BestellingStatusEnum> statusBestelling = FXCollections
			.observableArrayList(BestellingStatusEnum.values());
	ObservableList<BetaalStatusEnum> statusBetaling = FXCollections.observableArrayList(BetaalStatusEnum.values());

	@FXML
	private TitledPane titledPaneInDetail;
	@FXML
	private TableView<ProductInBestellingInterface> tableBesteldeProducten;
	@FXML
	private TableColumn<ProductInBestellingInterface, String> colProductID;
	@FXML
	private TableColumn<ProductInBestellingInterface, String> colProduct;
	@FXML
	private TableColumn<ProductInBestellingInterface, Boolean> colInVoorraad;
	@FXML
	private TableColumn<ProductInBestellingInterface, BigDecimal> colEenheidsprijs;
	@FXML
	private TableColumn<ProductInBestellingInterface, Integer> colAantalBesteld;
	@FXML
	private TableColumn<ProductInBestellingInterface, BigDecimal> colTotaal;

	@FXML
	private TitledPane titledPaneKlantGegevens;
	@FXML
	private Label lblNaam;
	@FXML
	private Label lblEmail;
	@FXML
	private Label lblTelefoonnummer;
	@FXML
	private Label lbl_btw;

	@FXML
	private TitledPane titledPaneLeveringsadres;
	@FXML
	private Label lblStraat;
	@FXML
	private Label lblHuisnummer;
	@FXML
	private Label lblPostcode;
	@FXML
	private Label lblGemeente;

	private final Controller controller;
//	private final List<Bestelling> bestellingLijst;
	private Bestelling huidigeBestelling;
	private final Bedrijf klant;
	private final Adres adres;
	private BestellingDTO bestellingWijziging;
	private GuiController guiController;

	public BestellingDetailsController(GuiController guiController, Controller controller) {
		this.guiController = guiController;
		this.controller = controller;
		this.huidigeBestelling = controller.getBestelling();
		this.klant = huidigeBestelling.getKlant();
		this.adres = huidigeBestelling.getLeveradres();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("BestellingDetails.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lblVarBestellingID.setText(huidigeBestelling.getUuid().toString());
		LocalDateTime d = huidigeBestelling.getBestellingDatum();
		lblVarBesteldatum.setText(String.format("%d/%d/%d", d.getDayOfMonth(), d.getMonthValue(), d.getYear()));

		LocalDateTime bd = huidigeBestelling.getDatumLaatsteBetalingsHerinnering();
		lblVarDatumLaatsteHerinnering
				.setText(bd == null ? String.format("%d/%d/%d", d.getDayOfMonth(), d.getMonthValue(), d.getYear()) : String.format("%d/%d/%d", bd.getDayOfMonth(), bd.getMonthValue(), bd.getYear()));
		lblVarTotaalbedrag.setText("€ "
				+ huidigeBestelling.getTotaalBestellingbedragExclBTW().setScale(2, RoundingMode.HALF_UP).toString());
		lblVarTotaalbedragInclBTW.setText("€ "
				+ huidigeBestelling.getTotaalBestellingbedragMetBTW().setScale(2, RoundingMode.HALF_UP).toString());
//		 if (bestelling.getIsBetaald()) {
//			    chkIsBetaald.setSelected(true);
//			}
		cbxBetaalStatus.setItems(statusBetaling);
		cbxBetaalStatus.setValue(this.huidigeBestelling.getBetaalStatus());
		cbxBetaalStatus.setDisable(true);
		cbxStatusBestelling.setItems(statusBestelling);
		cbxStatusBestelling.setValue(this.huidigeBestelling.getBestellingStatus());
		cbxStatusBestelling.setDisable(true);

//		 List<ProductInBestelling> productInBestellingLijst = huidigeBestelling.getProductenInBestelling();
		colProduct.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNaam()));
		colInVoorraad.setCellFactory(CheckBoxTableCell.forTableColumn(colInVoorraad));
		colInVoorraad.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().getIsInStock()));
		colEenheidsprijs.setCellValueFactory(cellData -> {
			return new SimpleObjectProperty<>(cellData.getValue().getEenheidsprijs().setScale(2, RoundingMode.HALF_UP));
		});

		colAantalBesteld
				.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAantal()).asObject());
		colTotaal.setCellValueFactory(cellData -> new SimpleObjectProperty<>(
				cellData.getValue().getTotalePrijs().setScale(2, RoundingMode.HALF_UP)));

		tableBesteldeProducten.setItems(controller.getProductenInBestelling());
		
		colBestellingId.setCellValueFactory(cellData -> cellData.getValue().bestellingUuidProperty());
		colTeBetalen.setCellValueFactory(cellData -> cellData.getValue().teBetalenProperty());
		colBetaaldeBedrag.setCellValueFactory(cellData -> cellData.getValue().betaaldeBedragProperty());
		colVerwerkt.setCellFactory(CheckBoxTableCell.forTableColumn(colVerwerkt));
		colVerwerkt.setCellValueFactory(cellData -> cellData.getValue().isVerwerktProperty());
		colGoedgekeurd.setCellFactory(CheckBoxTableCell.forTableColumn(colGoedgekeurd));
		colGoedgekeurd.setCellValueFactory(cellData -> cellData.getValue().isGoedgekeurdProperty());
		
		tblBetalingen.setItems(this.huidigeBestelling.getBetalingen());

		lblNaam.setText(klant.getNaam());
		lblEmail.setText(klant.getEmail());
		lblTelefoonnummer.setText(klant.getTelefoonnummer());
		lbl_btw.setText(klant.getBtw());
		lblStraat.setText(adres.getStraat());
		lblHuisnummer.setText(String.format("%d", adres.getHuisnummer()));
		lblPostcode.setText(String.format("%d", adres.getPostcode()));
		lblGemeente.setText(adres.getGemeente());
	}

	@FXML
	void wijzigBestelling(MouseEvent event) {
		if (btnWijzigBestelling.getText().contains("Wijzig")) {
			this.cbxBetaalStatus.setDisable(false);
			this.cbxStatusBestelling.setDisable(false);
			bestellingWijziging = new BestellingDTO();
			this.btnWijzigBestelling.setText("Bevestig");
		} else if (btnWijzigBestelling.getText() == "Bevestig") {
			bestellingWijziging.betaalStatus = cbxBetaalStatus.getValue();
			bestellingWijziging.bestellingStatus = cbxStatusBestelling.getValue();
			this.cbxBetaalStatus.setDisable(true);
			this.cbxStatusBestelling.setDisable(true);
			controller.wijzigBestelling(bestellingWijziging);
			this.btnWijzigBestelling.setText("Wijzig");
		}
	}

	@FXML
	void stuurBetalingsherinnering() {
		huidigeBestelling.stuurBetalingsHerinnering();
	}

}
