package gui;

import java.io.IOException;

import controllers.AanmeldController;
import controllers.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class NieuwWachtwoordController extends AnchorPane{
	private Controller controller;
	
	private String naam;
	
    @FXML
    private PasswordField txfNieuwWw;

    @FXML
    private PasswordField txfBevWw;

    @FXML
    private Button btnBevestig;

    @FXML
    private Button btnAnnuleer;

    @FXML
    private Text txtError;
    
    public NieuwWachtwoordController(Controller controller, String gebruikersnaam) {
    	this.controller = controller;
    	this.naam = gebruikersnaam;
    	
    	   	
    	FXMLLoader loader = new FXMLLoader(this.getClass().getResource("NieuwWachtwoordForm.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
    }

    @FXML
    void onAnnuleer(MouseEvent event) {
    	try {
    		AanmeldController ac = new AanmeldController();
			AanmeldschermController root = new AanmeldschermController(ac);
			Scene scene = new Scene(root);
			Stage stage = (Stage) this.getScene().getWindow();
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
	@FXML
	public void onKeyPressedNieuwWw(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER)) {
			String nieuwWachtwoord = txfNieuwWw.getText();
	    	String bevestigingWw = txfBevWw.getText();
	    	
	    	checkPasswords(nieuwWachtwoord, bevestigingWw);
		}
	}

	@FXML
	public void onKeyPressedBevWw(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER)) {
			String nieuwWachtwoord = txfNieuwWw.getText();
	    	String bevestigingWw = txfBevWw.getText();
	    	
	    	checkPasswords(nieuwWachtwoord, bevestigingWw);
		}
	}

    @FXML
    void onBevestigWachtwoord(MouseEvent event) {
    	String nieuwWachtwoord = txfNieuwWw.getText();
    	String bevestigingWw = txfBevWw.getText();
    	
    	checkPasswords(nieuwWachtwoord, bevestigingWw);
    }
    
    private void checkPasswords(String nieuwWachtwoord, String bevestigingWw) {
    	try {
    		this.controller.wijzigWachtwoord(nieuwWachtwoord, bevestigingWw);
    		showWindow();
    	} catch(IllegalArgumentException iae) {
    		this.txtError.setText(iae.getMessage());
    	}
    }

	private void showWindow() {
		GuiController guiController = new GuiController(this.controller);
		guiController.setGebruikersnaam(naam);
		Scene scene = new Scene(guiController);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.setFullScreen(false);
		stage.show();		
	}

}
