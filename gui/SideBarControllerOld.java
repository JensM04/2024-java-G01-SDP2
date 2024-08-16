package gui;

import java.io.IOException;

import controllers.AdminController;
import controllers.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import utils.JPAUtil;

public class SideBarControllerOld extends BorderPane {

	private Controller controller;

	@FXML
	private Button bestellingButton;

	@FXML
	private Button klantenButton;

	@FXML
	private ImageView logoDelaware;

	@FXML
	private SplitPane mainSplit;

	@FXML
	private VBox appWindow;

    @FXML
    private Button btnClose;

	@FXML
	private VBox sideWindow;

	@FXML
	private Label gebruikersnaamLabel;

	@FXML
	private Button btnUitloggen;

    @FXML
    void onClose(MouseEvent event) {
    	JPAUtil.getEntityManager().close();
    	JPAUtil.getEntityManagerFactory().close();
    }

	public SideBarControllerOld(Controller controller) {
		this.controller = controller;

		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("sidebar.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}

		if (this.controller instanceof AdminController) {
			this.bestellingButton.setText("Bedrijven");
			this.klantenButton.setVisible(false);
		}
	}

	public SideBarControllerOld(GuiController guiController, Controller controller2) {
		this.controller = controller;

		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("sidebar.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}

		if (this.controller instanceof AdminController) {
			this.bestellingButton.setText("Bedrijven");
			this.klantenButton.setVisible(false);
		}
	}

	@FXML
	void toonBestellingen(MouseEvent event) {
		// TODO knoppen dynamisch tonen afhankelijk van verschillende controller
		if (controller instanceof AdminController) {
//			try {
////				BedrijvenOverzichtController boc = new BedrijvenOverzichtController(controller, this.sideWindow);
////				loadWindow(boc, "BedrijvenOverzicht");
//			} catch (IOException ex) {
//				throw new RuntimeException(ex);
//			} catch (IllegalAccessException e) {
//				e.printStackTrace();
//			}
		} else {
//
//			try {
////				BestellingenOverzichtController boc = new BestellingenOverzichtController(controller, this.sideWindow);
////				loadWindow(boc, "BestellingenOverzicht");
//			} catch (IllegalAccessException e) {
//				System.out.println("ik ben hier");
//				Alert alert = new Alert(AlertType.ERROR);
//				alert.setTitle("GEEN TOEGANG");
//				alert.setHeaderText(e.getClass().getSimpleName());
//				alert.setContentText(e.getMessage());
//				alert.showAndWait();
//			} catch (IOException ex) {
//				throw new RuntimeException(ex);
//			}
		}
	}

	@FXML
	void toonKlanten(MouseEvent event) {
		if (this.controller instanceof AdminController) {
			this.klantenButton.setVisible(false);
		} else {
//			try {
//				KlantenOverzichtController koc = new KlantenOverzichtController(controller, this.sideWindow);
//				loadWindow(koc, "KlantenOverzicht");
//			} catch (IOException ex) {
//				throw new RuntimeException(ex);
//			} catch (IllegalAccessException e) {
//				Alert alert = new Alert(AlertType.ERROR);
//				alert.setTitle("GEEN TOEGANG");
//				alert.setHeaderText(e.getClass().getSimpleName());
//				alert.setContentText(e.getMessage());
//
//				alert.showAndWait();
//			}
		}

	}

	private void loadWindow(Object controller, String naam) throws IOException, IllegalAccessException {
		appWindow.getChildren().clear();
		sideWindow.getChildren().clear();
		FXMLLoader loader = new FXMLLoader(controller.getClass().getResource(String.format("%s.fxml", naam)));
		loader.setController(controller);
		loader.setRoot(appWindow);
		loader.load();
	}

	private void closeWindow() {

	}

	public void setGebruikersnaam(String gebruikersnaam) {
        gebruikersnaamLabel.setText(gebruikersnaam);
    }
}
