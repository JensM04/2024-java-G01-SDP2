package gui;

import java.io.IOException;

import controllers.AanmeldController;
import controllers.Controller;
import exceptions.AanmeldException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AanmeldschermController extends AnchorPane {

	private AanmeldController controller;

	@FXML
	private Button aanmeldKnop;

	@FXML
	private PasswordField wachtwoord;

	@FXML
	private TextField gebruikersnaam;

	public AanmeldschermController(AanmeldController controller) {
		this.controller = controller;
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("Aanmeldscherm.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	@FXML
	void btnAanmeldenOnAction(ActionEvent event) {
		this.meldAan();
	}

	private void meldAan() {
		String naam = gebruikersnaam.getText();
		String ww = wachtwoord.getText();

		try {
			Controller c = controller.meldAan(naam, ww);

			if (!c.isWachtwoordGewijzigd()) {
				NieuwWachtwoordController nwc = new NieuwWachtwoordController(c, naam);
				Scene scene = new Scene(nwc);
				Stage stage = (Stage) this.getScene().getWindow();
				stage.setScene(scene);
				stage.show();
			} else {

				GuiController guiController = new GuiController(c);
				guiController.setGebruikersnaam(naam);
				Scene scene = new Scene(guiController);
				Stage stage = (Stage) this.getScene().getWindow();
				stage.setScene(scene);
				stage.centerOnScreen();
				stage.setFullScreen(false);
				stage.show();
			}

		} catch (AanmeldException ex) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Fout bij aanmelden");
			alert.setHeaderText(ex.getClass().getSimpleName());
			alert.setContentText(ex.getMessage());

			alert.showAndWait();
		}
	}

	@FXML
	public void onKeyPressedGebruikersnaam(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER))
			this.meldAan();
	}

	@FXML
	public void onKeyPressedWachtwoord(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER))
			this.meldAan();
	}
}
