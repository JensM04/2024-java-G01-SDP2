package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import controllers.Controller;
import domain.Bedrijf;
import domain.Klant;
import domain.Leverancier;
import dto.B2BBedrijfDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import utils.BedrijfSectorEnum;

public class BedrijfDetailsController extends VBox implements Initializable {
	@FXML
	private TitledPane titledPaneKlantgegevens;
	@FXML
	private ImageView imgViewVarLogo;
	@FXML
	private Label lblVarBedrijfsnaam;
	// verandert naar enum
	@FXML
	private ComboBox<BedrijfSectorEnum> comboBoxVarSector;
	@FXML
	private Label lblVarAantalKlanten;
	@FXML
	private CheckBox checkboxVarIsActief;
	@FXML
	private Label lblVarBTWNummer;
	@FXML
	private TitledPane titledPaneContactgegevens1;
	@FXML
	private Label lblVarKlantGebruikersnaam;
	@FXML
	private Label lblVarKlantWachtwoord;
	@FXML
	private TitledPane titledPaneContactgegevens11;
	@FXML
	private Label lblVarLeverancierGebruikersnaam;
	@FXML
	private Label lblVarLeverancierWachtwoord;
	@FXML
	private TitledPane titledPaneContactgegevens;
	@FXML
	private Label lblVarTelefoonnummer;
	@FXML
	private Hyperlink hyperlinkVarWebsite;
	@FXML
	private Hyperlink hyperlinkVarEmail;
	@FXML
	private TitledPane titledPaneAdres;
	@FXML
	private Label lblVarBus;
	@FXML
	private Label lblVarPostcode;
	@FXML
	private Label lblVarGemeente;
	@FXML
	private Label lblVarHuisnummer;
	@FXML
	private Label lblVarStraat;
	private Controller controller;
	private Bedrijf bedrijf;
	@FXML
	private Button btnVoegAccountToe;
	private GuiController guiController;

	private B2BBedrijfDTO bedrijfWijzigingen;

	@FXML
	private Button btnWijzigBedrijf;

	@FXML
	private Button btnAnnuleerWijziging;
	
	@FXML
	private TableView<Klant> klantTableView;
	@FXML
	private TableColumn<Klant, String> colGebruikersnaam_klant;
	@FXML
	private TableColumn<Klant, String> colWachtwoord_klant;
	
	@FXML
	private TableView<Leverancier> leverancierTableView;
	@FXML
	private TableColumn<Leverancier, String> colGebruikersnaam_leverancier;
	@FXML
	private TableColumn<Leverancier, String> colWachtwoord_leverancier;

	public BedrijfDetailsController(GuiController guiController, Controller controller) {
		this.guiController = guiController;
		this.controller = controller;
		this.bedrijf = controller.getHuidigBedrijf();

		if (bedrijf != null) {
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("BedrijfDetails.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			try {
				loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	public void onCheckboxIsActief(ActionEvent event) {
		controller.wijzigHuidigBedrijfIsActief();
	}

	@FXML
	void onComboBoxSector(ActionEvent event) {

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lblVarBedrijfsnaam.setText(bedrijf.getNaam());
		imgViewVarLogo.setImage(null);
		comboBoxVarSector.setItems(controller.getAlleSectoren());
		comboBoxVarSector.setValue(bedrijf.getSector());
		lblVarAantalKlanten.setText(Integer.toString(bedrijf.getAantalKlanten()));
		checkboxVarIsActief.selectedProperty().bind(bedrijf.isActiefProperty());

		// TODO Hoe klant account en lev account geimplementeer en accederen?

		hyperlinkVarWebsite.setText(bedrijf.getWebsiteURL());
		hyperlinkVarEmail.setText(bedrijf.getEmail());
		lblVarTelefoonnummer.setText(bedrijf.getTelefoonnummer());

		lblVarStraat.setText(bedrijf.getAdres().getStraat());
		lblVarHuisnummer.setText(Integer.toString(bedrijf.getAdres().getHuisnummer()));
		lblVarBus.setText(bedrijf.getAdres().getBus());
		lblVarPostcode.setText(String.format("%04d", bedrijf.getAdres().getPostcode()));
		lblVarGemeente.setText(bedrijf.getAdres().getGemeente());
		lblVarBTWNummer.setText(bedrijf.getBtw());
		
//		String linkAfbeelding = String.format("/images/%s.png", this.bedrijf.getNaam());
//		String linkAfbeelding = "https://img.freepik.com/free-vector/bird-colorful-logo-gradient-vector_343694-1365.jpg?size=338&ext=jpg&ga=GA1.1.1141335507.1718064000&semt=sph";
		String linkAfbeelding = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRfE4QxcUZhYJuAKGSySAL06PwVK9FBp_hKkA&s";
		imgViewVarLogo.setFitHeight(110);
		imgViewVarLogo.setPreserveRatio(true);
//		imgViewVarLogo.setImage(new Image(this.getClass().getResourceAsStream(linkAfbeelding)));
		imgViewVarLogo.setImage(new Image(linkAfbeelding));
		
		klantTableView.setItems(bedrijf.getKlantAccounts());
		
		colGebruikersnaam_klant.setCellValueFactory(cellData -> cellData.getValue().gebruikersnaamProperty());
		colWachtwoord_klant.setCellValueFactory(cellData -> cellData.getValue().wachtwoordProperty());
		
		colWachtwoord_klant.setCellFactory((TableColumn<Klant, String> param) -> {
            return new PasswordFieldCell<Klant>();
        });
		
		leverancierTableView.setItems(bedrijf.getLeverancierAccounts());
		
		colGebruikersnaam_leverancier.setCellValueFactory(cellData -> cellData.getValue().gebruikersnaamProperty());
		colWachtwoord_leverancier.setCellValueFactory(cellData -> cellData.getValue().wachtwoordProperty());
		
		colWachtwoord_leverancier.setCellFactory((TableColumn<Leverancier, String> param) -> {
            return new PasswordFieldCell<Leverancier>();
        });
	}

	@FXML
	public void onVoegAccountToe(ActionEvent event) {
		guiController.setSideWindow(new AccountBedrijfToevoegenController(guiController, controller));
	}

	@FXML
	void wijzigBedrijf(ActionEvent event) {
		if (btnWijzigBedrijf.getText().contains("Wijzig")) {
			 this.btnAnnuleerWijziging.setVisible(true);
			 this.checkboxVarIsActief.setDisable(false);
		     this.comboBoxVarSector.setDisable(false);
		     bedrijfWijzigingen = new B2BBedrijfDTO();
		     this.btnWijzigBedrijf.setText("Bevestig");
		}
		else if (btnWijzigBedrijf.getText() == "Bevestig") {
			bedrijfWijzigingen.isActief = checkboxVarIsActief.isSelected();
			bedrijfWijzigingen.sector = comboBoxVarSector.getValue();
			this.checkboxVarIsActief.setDisable(true);
			this.comboBoxVarSector.setDisable(true);
			controller.wijzigHuidigBedrijfIsActief();
			controller.wijzigSectorHuidigBedrijf(comboBoxVarSector.getValue());
			this.btnWijzigBedrijf.setText("Wijzig");
			 this.btnAnnuleerWijziging.setVisible(false);
		}
	}

	@FXML
	void annuleerWijzigen(ActionEvent event) {
		 this.checkboxVarIsActief.setDisable(true);
	     this.comboBoxVarSector.setDisable(true);
	     this.btnWijzigBedrijf.setText("Wijzig");
		 this.btnAnnuleerWijziging.setVisible(true);
	}
}
