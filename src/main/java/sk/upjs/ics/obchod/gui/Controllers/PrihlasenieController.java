package sk.upjs.ics.obchod.gui.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sk.upjs.ics.obchod.gui.ViewFactory;
import sk.upjs.ics.obchod.managers.EntityManagerFactory;
import sk.upjs.ics.obchod.managers.IAccountManager;
import sk.upjs.ics.obchod.utils.GuiUtils;

public class PrihlasenieController extends Controller implements Initializable {

    @FXML
    private Label spatLabel;

    @FXML
    private TextField prihlasovacieMenoTextField;

    @FXML
    private PasswordField hesloPasswordField;

    @FXML
    private Button prihlasitButton;

    private IAccountManager accountManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        accountManager = EntityManagerFactory.INSTANCE.getAccountManager();
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

        if (accountManager.signInPerson(meno, heslo)) {
            prihlasovacieMenoTextField.clear();
            hesloPasswordField.clear();
            Scene obchodScene = ViewFactory.INSTANCE.getObchodScene(mainStage);
            
            if(accountManager.getActiveAccount().isAdministrator()) {
               ViewFactory.INSTANCE.getAdministraciaScene(mainStage);
            } else {
                mainStage.setScene(obchodScene);
            }

        } else {
            GuiUtils.showWarning("Nesprávne meno alebo heslo");
        }

    }
}
