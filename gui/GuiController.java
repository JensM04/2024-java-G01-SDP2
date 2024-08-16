package gui;

import java.io.IOException;

import controllers.AanmeldController;
import controllers.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GuiController extends BorderPane {
	@FXML
	private SplitPane mainSplit;
	@FXML
	private VBox appWindow;
	@FXML
	private VBox sideWindow;
	@FXML
	private ImageView logoDelaware;
	@FXML
	private VBox vbxSideBar;
	@FXML
	private Label lblGebruikersnaam;
	@FXML
	private Button btnUitloggen;

	private Controller controller;

	public GuiController(Controller controller) {
		this.controller = controller;

		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("Gui.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			this.controller.close();
		}));

		switch (controller.getControllerSoort()) {
		case LEVERANCIER: {
			this.setSideBar(new LeverancierAppMenuController(this, controller));
			break;
		}
		case ADMINISTRATOR: {
			this.setSideBar(new AdminAppMenuController(this, controller));
			break;
		}
		default:
			break;
		}

	}

	private void setSideBar(AppMenuController leverancierAppMenuController) {
		this.vbxSideBar.getChildren().clear();
		this.vbxSideBar.getChildren().add(leverancierAppMenuController);
	}

	public void setAppWindow(Pane pane) {
		this.appWindow.getChildren().clear();
		this.sideWindow.getChildren().clear();
		this.appWindow.getChildren().add(pane);
	}

	public void setSideWindow(Pane pane) {
		this.sideWindow.getChildren().clear();
		this.sideWindow.getChildren().add(pane);
	}

	public void setGebruikersnaam(String gebruikersnaam) {
        lblGebruikersnaam.setText(gebruikersnaam);
    }

	@FXML
	private void onBtnUitloggen(ActionEvent event) {
		try {
			AanmeldController ac = new AanmeldController();
			AanmeldschermController root = new AanmeldschermController(ac);
			Scene scene = new Scene(root);
			Stage stage = (Stage) this.getScene().getWindow();
			stage.setScene(scene);
			stage.setTitle("B2B Applicatie");
			stage.centerOnScreen();
			stage.setResizable(false);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
