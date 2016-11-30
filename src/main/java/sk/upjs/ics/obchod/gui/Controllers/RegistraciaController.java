package sk.upjs.ics.obchod.gui.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sk.upjs.ics.obchod.gui.ViewFactory;
import sk.upjs.ics.obchod.services.DefaultPouzivatelManager;
import sk.upjs.ics.obchod.services.PouzivatelManager;

public class RegistraciaController implements Initializable {

    @FXML
    private TextField prihlasovacieMenoTextField;

    @FXML
    private PasswordField hesloPasswordField;

    @FXML
    private TextField emailTextField;

    @FXML
    private Button registrovatButton;

    @FXML
    private Label spatLabel;

    private DefaultPouzivatelManager defaultPouzivatelManager;

    private Stage mainStage;

    public void setStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        defaultPouzivatelManager = DefaultPouzivatelManager.INSTANCE;
    }

    @FXML
    public void onSpatLabelClicked() {
        Scene obchodScene = ViewFactory.INSTANCE.getObchodScene(mainStage);
        mainStage.setScene(obchodScene);
    }
    
    @FXML
    public void onRegistrovatButtonClicked() {
        String meno = prihlasovacieMenoTextField.getText();
        String heslo = hesloPasswordField.getText();
        String email = emailTextField.getText();

        System.out.println("REGISTERBUTTOn");
        
        if (!defaultPouzivatelManager.jeVolneMeno(meno)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Upozornenie");
            alert.setHeaderText("Zadané prihlasovacie meno už existuje.");
            alert.showAndWait();

        } else if (meno == null || heslo == null || email == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Upozornenie");
            alert.setHeaderText("Nevyplnili ste všetky údaje.");
            alert.showAndWait();
        } else {

            defaultPouzivatelManager.registrujPouzivatela(meno, heslo, email);
            onSpatLabelClicked();
        }

    }
}
