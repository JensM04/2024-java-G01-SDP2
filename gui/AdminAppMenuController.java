package gui;

import controllers.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class AdminAppMenuController extends AppMenuController {
	private EventHandler<ActionEvent> onBtnBedrijven = new EventHandler<>() {
		@Override
		public void handle(ActionEvent arg0) {
			getGuiController().setAppWindow(new BedrijvenOverzichtController(getGuiController(), getController()));
		}
	};

	public AdminAppMenuController(GuiController guiController, Controller controller) {
		super(guiController, controller);

		Button btnBedrijven = new Button("Bedrijven");
		btnBedrijven.setOnAction(onBtnBedrijven);
		super.addButton(btnBedrijven);
	}


}
