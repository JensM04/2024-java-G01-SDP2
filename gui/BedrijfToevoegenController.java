package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import controllers.Controller;
import dto.AdresDTO;
import dto.B2BBedrijfDTO;
import exceptions.RegistreerException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import utils.BedrijfSectorEnum;

public class BedrijfToevoegenController extends VBox implements Initializable {
	@FXML
	private TextField txtfldBedrijfsnaam;
	@FXML
	private Label lblErrorNaam;
	@FXML
	private TextField txtfldEmail;
	@FXML
	private Label lblErrorEmail;
	@FXML
	private TextField txtfldTelefoonnummer;
	@FXML
	private Label lblErrorTelefoonnummer;
	@FXML
	private TextField txtfldUrl;
	@FXML
	private Label lblErrorUrl;
	@FXML
	private ComboBox<BedrijfSectorEnum> comboSector;
	@FXML
	private Label lblErrorSector;
	@FXML
	private TextField txtfldStraat;
	@FXML
	private Label lblErrorStraat;
	@FXML
	private TextField txtfldHuisnummer;
	@FXML
	private Label lblErrorHuisnummer;
	@FXML
	private TextField txtfldBus;
	@FXML
	private Label lblErrorBus;
	@FXML
	private TextField txtfldPostcode;
	@FXML
	private Label lblErrorPostcode;
	@FXML
	private TextField txtfldGemeente;
	@FXML
	private Label lblErrorGemeente;
	@FXML
	private Button btnVoegToe;
	@FXML
	private TextField Btwnr_input;
	private GuiController guiController;
	private Controller controller;

	public BedrijfToevoegenController(GuiController guiController, Controller controller) {
		this.guiController = guiController;
		this.controller = controller;

		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("BedrijfToevoegen.fxml"));
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
		this.comboSector.setItems(controller.getAlleBedrijfSectoren());
	}

	@FXML
	public void onBtnVoegToe(ActionEvent event) {
		B2BBedrijfDTO bedrijfDTO = new B2BBedrijfDTO();

		bedrijfDTO.naam = txtfldBedrijfsnaam.getText();
		bedrijfDTO.email = txtfldEmail.getText();
		bedrijfDTO.telefoonnummer = txtfldTelefoonnummer.getText();
		bedrijfDTO.websiteURL = txtfldUrl.getText();
		bedrijfDTO.sector = comboSector.getValue();
		bedrijfDTO.btw = Btwnr_input.getText();

		AdresDTO adresDTO = new AdresDTO();

		adresDTO.straat = txtfldStraat.getText();
		try {
			adresDTO.huisnummer = Integer.valueOf(txtfldHuisnummer.getText());
		} catch (NumberFormatException e) {
			adresDTO.hasError = true;
			adresDTO.huisnummerError = "Moet numerieke waarde hebben";
		}
		adresDTO.huisnummer = Integer.valueOf(txtfldHuisnummer.getText());
		adresDTO.bus = txtfldHuisnummer.getText();
		try {
			adresDTO.postcode = Integer.valueOf(txtfldPostcode.getText());
		} catch (NumberFormatException e) {
			adresDTO.hasError = true;
			adresDTO.postcodeError = "Moet numerieke waarde hebben";
		}
		adresDTO.gemeente = txtfldGemeente.getText();

		bedrijfDTO.adres = adresDTO;

		try {
			if(adresDTO.hasError || bedrijfDTO.hasError) throw new RegistreerException();
			controller.addBedrijf(bedrijfDTO);
			guiController.setSideWindow(new BedrijfToevoegenController(guiController, controller));
		} catch (RegistreerException ex) {
			lblErrorNaam.setText(bedrijfDTO.naamError);
			lblErrorEmail.setText(bedrijfDTO.emailError);
			lblErrorTelefoonnummer.setText(bedrijfDTO.telefoonnummerError);
			lblErrorUrl.setText(bedrijfDTO.websiteUrlError);
			lblErrorSector.setText(bedrijfDTO.sectorError);

			lblErrorStraat.setText(adresDTO.straatError);
			lblErrorHuisnummer.setText(adresDTO.huisnummerError);
			lblErrorBus.setText(adresDTO.busError);
			lblErrorPostcode.setText(adresDTO.postcodeError);
			lblErrorGemeente.setText(adresDTO.gemeenteError);
		}
	}

}
