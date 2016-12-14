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
import sk.upjs.ics.obchod.managers.DefaultPouzivatelManager;
import sk.upjs.ics.obchod.managers.PouzivatelManager;
import sk.upjs.ics.obchod.services.StringUtilities;

public class RegistraciaController implements Initializable {

    @FXML
    private TextField prihlasovacieMenoTextField;

    @FXML
    private PasswordField hesloPasswordField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField menoTextField;

    @FXML
    private TextField priezviskoTextField;

    @FXML
    private TextField mestoTextField;

    @FXML
    private TextField ulicaTextField;

    @FXML
    private TextField pscTextField;

    @FXML
    private Button registrovatButton;

    @FXML
    private Label spatLabel;

    private PouzivatelManager defaultPouzivatelManager;

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
        String prihlasovacieMeno = prihlasovacieMenoTextField.getText();
        String heslo = hesloPasswordField.getText();
        String email = emailTextField.getText();
        String meno = menoTextField.getText();
        String priezvisko = priezviskoTextField.getText();
        String mesto = mestoTextField.getText();
        String ulica = ulicaTextField.getText();
        int psc = -1;
        
        String pscRetazec = pscTextField.getText();
        if (StringUtilities.jeCislo(pscRetazec)) {
            psc = Integer.parseInt(pscRetazec);
        }     
        
        if (!defaultPouzivatelManager.jeVolneMeno(prihlasovacieMeno)) {
            ukazUpozornenie("Zadané prihlasovacie meno už existuje.");
            return;
        } else if (prihlasovacieMeno.trim().isEmpty() || heslo.trim().isEmpty() || email.trim().isEmpty()) {
            ukazUpozornenie("Nevyplnili ste všetky údaje.");
            return;
        } else if (!StringUtilities.jeValidnyFormatEmailu(email)) {
            ukazUpozornenie("Formát emailovej adresy nie je valídny.");
            return;
        } else {
            defaultPouzivatelManager.registrujPouzivatela(prihlasovacieMeno, heslo, 
                    email, meno, priezvisko, mesto, ulica, psc);
            onSpatLabelClicked();
        }

        prihlasovacieMenoTextField.clear();
        hesloPasswordField.clear();
        emailTextField.clear();

        menoTextField.clear();
        priezviskoTextField.clear();
        mestoTextField.clear();
        ulicaTextField.clear();
        pscTextField.clear();
    }

    private void ukazUpozornenie(String hlavicka) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Upozornenie");
        alert.setHeaderText(hlavicka);
        alert.showAndWait();
    }
}
