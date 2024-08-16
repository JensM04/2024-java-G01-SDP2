package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import controllers.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public abstract class AppMenuController extends VBox implements Initializable {
	private GuiController guiController;
	private Controller controller;
	private Button disabledButton;

	public AppMenuController(GuiController guiController, Controller controller) {
		this.guiController = guiController;
		this.controller = controller;
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("AppMenu.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void addButton(Button button) {
		this.getChildren().add(button);
		EventHandler<ActionEvent> oldEventHandler = button.getOnAction();
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				oldEventHandler.handle(arg0);
				if(disabledButton != null) disabledButton.setDisable(false);
				button.setDisable(true);
				disabledButton = button;
			}
		});
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

	public GuiController getGuiController() {
		return guiController;
	}

	public Controller getController() {
		return controller;
	}
}
