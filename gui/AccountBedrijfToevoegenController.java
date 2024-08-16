package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import controllers.Controller;
import dto.GebruikerDTO;
import exceptions.RegistreerGebruikerException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.VBox;
import utils.GebruikerRolEnum;

public class AccountBedrijfToevoegenController extends VBox implements Initializable {
	@FXML
	private Button btnVoegToe;
	@FXML
	private ToggleGroup gebruikerRol;
	@FXML
	private RadioButton radioKlant;
	@FXML
	private RadioButton radioLeverancier;
	@FXML
	private TextField txtfldEmail;
	@FXML
	private TextField txtfldGebruikersnaam;
	@FXML
	private TextField txtfldWachtwoord;
	private GebruikerDTO gebruikerDTO;
	private Controller controller;
	private GuiController guiController;
	@FXML
	private Label lblErrorEmail;
	@FXML
	private Label lblErrorNaam;
	@FXML
	private Label lblErrorRol;
	@FXML
	private Label lblErrorWachtwoord;

	public AccountBedrijfToevoegenController(GuiController guiController, Controller controller) {
		this.guiController = guiController;
		this.controller = controller;
		this.gebruikerDTO = new GebruikerDTO();

		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("AccountBedrijfToevoegen.fxml"));
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
		gebruikerRol.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> ob, Toggle o, Toggle n) {
				if (radioKlant.isSelected())
					gebruikerDTO.rol = GebruikerRolEnum.KLANT;
				if (radioLeverancier.isSelected())
					gebruikerDTO.rol = GebruikerRolEnum.LEVERANCIER;
			}
		});
	}

	private GebruikerRolEnum getGebruikerRol() {
		if (radioKlant.isSelected())
			return GebruikerRolEnum.KLANT;
		if (radioLeverancier.isSelected())
			return GebruikerRolEnum.LEVERANCIER;
		return null;
	}

	@FXML
	public void onBtnVoegToe(ActionEvent event) {
		GebruikerDTO gebruiker = new GebruikerDTO();
		gebruiker.naam = this.txtfldGebruikersnaam.getText();
		gebruiker.email = this.txtfldEmail.getText();
		gebruiker.wachtwoord = this.txtfldWachtwoord.getText();
		gebruiker.rol = this.getGebruikerRol();
		try {
			this.controller.voegGebruikerToeHuidigBedrijf(gebruiker);
			this.gebruikerDTO = new GebruikerDTO();
			this.resetVelden();
			this.guiController.setSideWindow(new BedrijfDetailsController(guiController, controller));
		} catch (RegistreerGebruikerException ex) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Fout bij toevoegen gebruiker");
			alert.setHeaderText(ex.getClass().getSimpleName());
			alert.setContentText(ex.getMessage());
			alert.showAndWait();
			this.lblErrorNaam.setText(gebruiker.naamError);
			this.lblErrorEmail.setText(gebruiker.emailError);
			this.lblErrorRol.setText(gebruiker.rolError);
			this.lblErrorWachtwoord.setText(gebruiker.wachtwoordError);
		}
	}

	private void resetVelden() {
		this.txtfldEmail.setText("");
		this.txtfldGebruikersnaam.setText("");
		this.txtfldWachtwoord.setText("");
		this.gebruikerRol.getSelectedToggle().setSelected(false);
	}

	@FXML
	public void onTxtfldEmailChanged(InputMethodEvent event) {
		this.gebruikerDTO.email = txtfldEmail.getText();
	}

	@FXML
	public void onTxtfldGebruikersnaamChanged(InputMethodEvent event) {
		this.gebruikerDTO.naam = txtfldGebruikersnaam.getText();
	}

	@FXML
	public void onTxtfldWachtwoordChanged(InputMethodEvent event) {
		this.gebruikerDTO.wachtwoord = txtfldWachtwoord.getText();
	}
}
