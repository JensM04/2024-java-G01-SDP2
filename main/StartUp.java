package main;

import java.util.List;

import controllers.AanmeldController;
import domain.B2BBestelling;
import exceptions.RegistreerGebruikerException;
import gui.AanmeldschermController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.BestellingDao;
import repository.BestellingDaoJpa;
import utils.BetaalStatusEnum;
import utils.initializeDatabase;

public class StartUp extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			// SideBarController root = new SideBarController();
			AanmeldController ac = new AanmeldController();
			AanmeldschermController root = new AanmeldschermController(ac);
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("B2B Applicatie");
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		simuleerMailServer();
//		try {
//			initializeDatabase.initDB();
//		} catch (RegistreerGebruikerException e) {
//			e.printStackTrace();
//		}
		launch(args);
	}

	private static void simuleerMailServer() {
		BestellingDao repo = new BestellingDaoJpa();
		List<B2BBestelling> bestellingen = repo.findAll();
		bestellingen.stream().filter(b -> b.isAutomatischeBetalingsherinnering() && b.getBetaalStatus() != BetaalStatusEnum.BETAALD).forEach(b -> b.stuurBetalingsHerinnering());
	}
}