package gui;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

import controllers.Controller;
import domain.Product;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProductenOverzichtController extends VBox implements Initializable{

    @FXML
    private TextField zoekBestellingInput;

    @FXML
    private Button btnZoekProduct;

    @FXML
    private TableColumn<Product, String> colProductnaam;

    @FXML
    private TableColumn<Product, Boolean> colAantalInStock;

    @FXML
    private TableColumn<Product, BigDecimal> colEenheidsprijs;

    private final Controller controller;

    @FXML
    private Button btnAddProduct;

	private GuiController guiController;


	public ProductenOverzichtController(GuiController guiController, Controller controller) {
		this.guiController = guiController;
		this.controller = controller;

		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("ProductenOverzicht.fxml"));
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
		colProductnaam.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNaam()));
		colAantalInStock.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().isInStock()));
		colEenheidsprijs.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEenheidsprijs()));
	}

	 @FXML
	    void handleAddProduct() {
	        try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddProductPopup.fxml"));
	            VBox root = loader.load();

	            Stage popupStage = new Stage();
	            popupStage.setTitle("Add Product");
	            popupStage.initModality(Modality.APPLICATION_MODAL);
	            popupStage.setScene(new Scene(root));

	            popupStage.showAndWait();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }


}
