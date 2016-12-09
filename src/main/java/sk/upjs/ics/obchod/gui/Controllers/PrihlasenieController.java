package sk.upjs.ics.obchod.gui.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sk.upjs.ics.obchod.gui.ViewFactory;
import sk.upjs.ics.obchod.services.DefaultPouzivatelManager;
import sk.upjs.ics.obchod.services.PouzivatelManager;

public class PrihlasenieController implements Initializable {

    @FXML
    private Label spatLabel;

    @FXML
    private TextField prihlasovacieMenoTextField;

    @FXML
    private PasswordField hesloPasswordField;

    @FXML
    private Button prihlasitButton;

    private Stage mainStage;

    private PouzivatelManager defaultPouzivatelManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        defaultPouzivatelManager = DefaultPouzivatelManager.INSTANCE;
    }

    public void setStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @FXML
    public void onSpatLabelClicked() {
        Scene obchodScene = ViewFactory.INSTANCE.getObchodScene(mainStage);
        mainStage.setScene(obchodScene);
    }

    @FXML
    public void onPrihlasitButtonClicked() {
        String meno = prihlasovacieMenoTextField.getText();
        String heslo = hesloPasswordField.getText();

        if (defaultPouzivatelManager.prihlasPouzivatela(meno, heslo)) {
            prihlasovacieMenoTextField.clear();
            hesloPasswordField.clear();
            Scene obchodScene = ViewFactory.INSTANCE.getObchodScene(mainStage);
            
            if(defaultPouzivatelManager.getAktivnyPouzivatel().isJeAdministrator()) {
                Scene administraciaScene = ViewFactory.INSTANCE.getAdministraciaScene(mainStage);
                mainStage.setScene(administraciaScene);
            } else {
                mainStage.setScene(obchodScene);
            }

        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Upozornenie");
            alert.setHeaderText("Nesprávne meno alebo heslo");
            alert.showAndWait();
        }

    }
}
