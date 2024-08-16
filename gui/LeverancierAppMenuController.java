package gui;

import controllers.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class LeverancierAppMenuController extends AppMenuController implements Initializable {
	private EventHandler<ActionEvent> onBtnBestellingen = new EventHandler<>() {
		@Override
		public void handle(ActionEvent arg0) {
			getGuiController().setAppWindow(new BestellingenOverzichtController(getGuiController(), getController()));
		}
	};
	private EventHandler<ActionEvent> onBtnKlanten = new EventHandler<>() {
		@Override
		public void handle(ActionEvent arg0) {
			getGuiController().setAppWindow(new KlantenOverzichtController(getGuiController(), getController()));
		}
	};

	public LeverancierAppMenuController(GuiController guiController, Controller controller) {
		super(guiController, controller);

		Button btnBestellingen = new Button("Bestellingen");
		btnBestellingen.setOnAction(onBtnBestellingen);
		super.addButton(btnBestellingen);

		Button btnKlanten = new Button("Klanten");
		btnKlanten.setOnAction(onBtnKlanten);
		super.addButton(btnKlanten);
	}
}
