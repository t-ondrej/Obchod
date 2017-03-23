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
import javafx.stage.Stage;
import javafx.util.Callback;
import sk.upjs.ics.obchod.entity.Pouzivatel;
import sk.upjs.ics.obchod.gui.ViewFactory;
import sk.upjs.ics.obchod.managers.EntityManagerFactory;
import sk.upjs.ics.obchod.managers.IPouzivatelManager;
import sk.upjs.ics.obchod.managers.PouzivatelManager;

public class ProfilPouzivatelaController extends AbstractController implements Initializable {

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

    private Pouzivatel aktivnyPouzivatel;
    
    private IPouzivatelManager pouzivatelManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pouzivatelManager = EntityManagerFactory.INSTANCE.getPouzivatelManager();
        aktivnyPouzivatel = pouzivatelManager.getAktivnyPouzivatel();

        prihlasovacieMenoTextField.setText(aktivnyPouzivatel.getPrihlasovacieMeno());
        emailTextField.setText(aktivnyPouzivatel.getEmail());
        menoTextField.setText(aktivnyPouzivatel.getMeno());
        priezviskoTextField.setText(aktivnyPouzivatel.getPriezvisko());
        mestoTextField.setText(aktivnyPouzivatel.getMeno());
        ulicaTextField.setText(aktivnyPouzivatel.getUlica());
        pscTextField.setText(Integer.toString(aktivnyPouzivatel.getPsc()));

    }

    @FXML
    public void onUlozitButtonClicked() {
        aktivnyPouzivatel.setEmail(emailTextField.getText());
        aktivnyPouzivatel.setMeno(menoTextField.getText());
        aktivnyPouzivatel.setPriezvisko(priezviskoTextField.getText());
        aktivnyPouzivatel.setMesto(mestoTextField.getText());
        aktivnyPouzivatel.setUlica(ulicaTextField.getText());
        aktivnyPouzivatel.setPsc(Integer.parseInt(pscTextField.getText()));

        pouzivatelManager.ulozPouzivatela(aktivnyPouzivatel);
    }

    @FXML
    public void onZmenitHesloButtonClicked() {
        Optional<String> noveHeslo = ukazZmenitHesloDialog();

        if (noveHeslo.isPresent()) {
            pouzivatelManager.zmenHeslo(aktivnyPouzivatel, noveHeslo.get());
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
