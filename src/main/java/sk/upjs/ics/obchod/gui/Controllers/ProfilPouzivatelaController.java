package sk.upjs.ics.obchod.gui.Controllers;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import sk.upjs.ics.obchod.dao.DaoFactory;
import sk.upjs.ics.obchod.dao.IPersonDao;
import sk.upjs.ics.obchod.entity.Account;
import sk.upjs.ics.obchod.entity.Person;
import sk.upjs.ics.obchod.gui.ViewFactory;
import sk.upjs.ics.obchod.managers.EntityManagerFactory;
import sk.upjs.ics.obchod.managers.IAccountManager;
import sk.upjs.ics.obchod.managers.IPersonManager;

public class ProfilPouzivatelaController extends Controller implements Initializable {

    @FXML
    private TextField prihlasovacieMenoTextField;

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
    private Label spatLabel;

    @FXML
    private Button ulozitButton;

    @FXML
    private Button zmenitHesloButton;

    private Account activeAccount;
    
    private Person activeAccountPerson;
    
    private IAccountManager accountManager;

    private IPersonManager personManager;
    
    private IPersonDao personDao;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        accountManager = EntityManagerFactory.INSTANCE.getAccountManager();
        personDao = DaoFactory.INSTANCE.getMysqlPersonDao();
        activeAccount = accountManager.getActiveAccount();

        prihlasovacieMenoTextField.setText(activeAccount.getUsername());
        emailTextField.setText(activeAccountPerson.getEmail());
        menoTextField.setText(activeAccountPerson.getName());
        priezviskoTextField.setText(activeAccountPerson.getSurname());
        mestoTextField.setText(activeAccountPerson.getName());
        ulicaTextField.setText(activeAccountPerson.getStreet());
        pscTextField.setText(Integer.toString(activeAccountPerson.getPostalCode()));

    }

    @FXML
    public void onUlozitButtonClicked() {
        activeAccountPerson.setEmail(emailTextField.getText());
        activeAccountPerson.setName(menoTextField.getText());
        activeAccountPerson.setSurname(priezviskoTextField.getText());
        activeAccountPerson.setCity(mestoTextField.getText());
        activeAccountPerson.setStreet(ulicaTextField.getText());
        activeAccountPerson.setPostalCode(Integer.parseInt(pscTextField.getText()));

        personDao.saveOrUpdate(activeAccountPerson);
    }

    @FXML
    public void onZmenitHesloButtonClicked() {
        Optional<String> noveHeslo = ukazZmenitHesloDialog();

        if (noveHeslo.isPresent()) {
            accountManager.changePassword(activeAccount, noveHeslo.get());
            System.out.println(noveHeslo.get());
        }
    }

    @FXML
    public void onSpatLabelClicked() {
        mainStage.setScene(ViewFactory.INSTANCE.getObchodScene(mainStage));
    }

    private Optional<String> ukazZmenitHesloDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Zmena hesla");
        dialog.setHeaderText("Zadajte nové heslo");

        ButtonType zmenitButtonType = new ButtonType("Zmeniť", ButtonData.OK_DONE);
        ButtonType zrusitButtonType = new ButtonType("Zrušiť", ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().clear();
        dialog.getDialogPane().getButtonTypes().addAll(zmenitButtonType, zrusitButtonType);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        PasswordField noveHeslo = new PasswordField();
        noveHeslo.setPromptText("Nové heslo");
        PasswordField noveHesloOpat = new PasswordField();
        noveHesloOpat.setPromptText("Nové heslo");

        grid.add(new Label("Nové heslo: "), 0, 0);
        grid.add(noveHeslo, 1, 0);
        grid.add(new Label("Zopakujte nové heslo: "), 0, 1);
        grid.add(noveHesloOpat, 1, 1);

        Node zmenitHeslo = dialog.getDialogPane().lookupButton(zmenitButtonType);
        zmenitHeslo.setDisable(true);

        noveHeslo.textProperty().addListener((observable, staraHodnota, novaHodnota) -> {
            zmenitHeslo.setDisable(novaHodnota.trim().isEmpty());
            zmenitHeslo.disableProperty().bind(Bindings.equal(noveHeslo.textProperty(), noveHesloOpat.textProperty()).not());        
        });
        
       //  zmenitHeslo.setDisable(!noveHeslo.getText().equals(noveHesloOpat.getText()));
        
        noveHesloOpat.textProperty().addListener((observable, staraHodnota, novaHodnota) -> zmenitHeslo.setDisable(novaHodnota.trim().isEmpty()));

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter((ButtonType b) -> {
            if (b == zmenitButtonType) {
                return noveHeslo.getText();
            }
            
            return null;
        });

        return dialog.showAndWait();
    }
}
