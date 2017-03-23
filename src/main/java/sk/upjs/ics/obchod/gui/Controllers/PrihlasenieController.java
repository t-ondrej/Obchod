package sk.upjs.ics.obchod.gui.Controllers;

import java.io.IOException;
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
import sk.upjs.ics.obchod.gui.ViewFactory;
import sk.upjs.ics.obchod.managers.EntityManagerFactory;
import sk.upjs.ics.obchod.managers.PouzivatelManager;
import sk.upjs.ics.obchod.managers.IPouzivatelManager;

public class PrihlasenieController extends AbstractController implements Initializable {

    @FXML
    private Label spatLabel;

    @FXML
    private TextField prihlasovacieMenoTextField;

    @FXML
    private PasswordField hesloPasswordField;

    @FXML
    private Button prihlasitButton;

    private IPouzivatelManager pouzivatelManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pouzivatelManager = EntityManagerFactory.INSTANCE.getPouzivatelManager();
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

        if (pouzivatelManager.prihlasPouzivatela(meno, heslo)) {
            prihlasovacieMenoTextField.clear();
            hesloPasswordField.clear();
            Scene obchodScene = ViewFactory.INSTANCE.getObchodScene(mainStage);
            
            if(pouzivatelManager.getAktivnyPouzivatel().isJeAdministrator()) {
               ViewFactory.INSTANCE.getAdministraciaScene(mainStage);
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
