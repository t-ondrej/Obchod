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
import sk.upjs.ics.obchod.managers.EntityManagerFactory;
import sk.upjs.ics.obchod.managers.IAccountManager;
import sk.upjs.ics.obchod.managers.PersonManager;
import sk.upjs.ics.obchod.utils.StringUtilities;
import sk.upjs.ics.obchod.utils.GuiUtils;
import sk.upjs.ics.obchod.managers.IPersonManager;

public class RegistraciaController extends Controller implements Initializable {

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

    private IPersonManager pouzivatelManager;
    
    private IAccountManager accountManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pouzivatelManager = EntityManagerFactory.INSTANCE.getPersonManager();
        accountManager = EntityManagerFactory.INSTANCE.getAccountManager();
    }

    @FXML
    public void onSpatLabelClicked() {
        Scene obchodScene = ViewFactory.INSTANCE.getObchodScene(mainStage);
        mainStage.setScene(obchodScene);
    }

    
    // TODO MAKE A PERSON REGISTRATION
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
        if (StringUtilities.isNumber(pscRetazec)) {
            psc = Integer.parseInt(pscRetazec);
        }     
        
        if (!accountManager.isUsernameAvailable(prihlasovacieMeno)) {
            GuiUtils.showWarning("Zadané prihlasovacie meno už existuje.");
            return;
        } else if (prihlasovacieMeno.trim().isEmpty() || heslo.trim().isEmpty() || email.trim().isEmpty()) {
            GuiUtils.showWarning("Nevyplnili ste všetky údaje.");
            return;
        } else if (!StringUtilities.isValidEmailFormat(email)) {
            GuiUtils.showWarning("Formát emailovej adresy nie je valídny.");
            return;
        } else {
            accountManager.signUpPerson(prihlasovacieMeno, heslo);
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

}
