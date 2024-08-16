package gui;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddProductController implements Initializable {

    @FXML
    private TextField nameInput;

    @FXML
    private TextField priceInput;

    @FXML
    private TextField stockInput;

    @FXML
    private Button btnBevestig;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	btnBevestig.setOnAction(event -> handleBevestig());
    }

    private void handleBevestig() {
        String naam = nameInput.getText();
        BigDecimal prijs = new BigDecimal(priceInput.getText());
        int stock = Integer.parseInt(stockInput.getText());

        Stage stage = (Stage) btnBevestig.getScene().getWindow();
        stage.close();
    }
}
