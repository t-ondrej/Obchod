package sk.upjs.ics.obchod.gui.Controllers;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import sk.upjs.ics.obchod.dao.DaoFactory;
import sk.upjs.ics.obchod.entity.Tovar;
import sk.upjs.ics.obchod.gui.ViewFactory;
import sk.upjs.ics.obchod.managers.KosikManager;
import sk.upjs.ics.obchod.managers.PouzivatelManager;
import sk.upjs.ics.obchod.managers.IKosikManager;
import sk.upjs.ics.obchod.dao.ITovarDao;
import sk.upjs.ics.obchod.managers.EntityManagerFactory;

public class SpecifikaciaTovaruController extends AbstractController {

    @FXML
    private Button pridatDoKosikaButton;

    @FXML
    private Label cenaLabel;

    @FXML
    private Label nazovTovaruLabel;

    @FXML
    private ImageView tovarImageView;

    @FXML
    private TextArea specifikaciaTovaruTextArea;

    @FXML
    private Label pridatDoKosikaNotifikaciaLabel;

    private IKosikManager kosikManager;

    private ITovarDao mysqlTovarDao;

    private Tovar tovar;

    // TODO kosikManager pri prihlasovani/odhlasovani
    public void inicializuj(String nazovTovaru) {
        this.kosikManager = EntityManagerFactory.INSTANCE.getKosikManager();
        mysqlTovarDao = DaoFactory.INSTANCE.getMysqlTovarDao();
        tovar = mysqlTovarDao.dajTovarPodlaNazvu(nazovTovaru);

        Image obrazok = new Image("file:" + tovar.getObrazokUrl());

        tovarImageView.setImage(obrazok);
        nazovTovaruLabel.setText(tovar.getNazov());
        specifikaciaTovaruTextArea.setText(tovar.getPopis());
        cenaLabel.setText(Integer.toString(tovar.getCena()) + " €");
        
        tovarImageView.requestFocus();
    }

    @FXML
    public void onPridatDoKosikaButtonClicked() {

        if (EntityManagerFactory.INSTANCE.getPouzivatelManager().getAktivnyPouzivatel() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Upozornenie");
            alert.setHeaderText("Ak chcete nakupovať, musite sa prihlásiť.");
            alert.showAndWait();
            return;
        }
        
        if (kosikManager.pridajTovarDoKosika(tovar)) {
            pridatDoKosikaNotifikaciaLabel.setStyle("-fx-text-fill: green");
            pridatDoKosikaNotifikaciaLabel.setText("Tovar bol pridaný!");
            pridatDoKosikaNotifikaciaLabel.setLayoutX(741);
        } else {
            pridatDoKosikaNotifikaciaLabel.setStyle("-fx-text-fill: red");
            pridatDoKosikaNotifikaciaLabel.setText("Tovar nie je možné pridať!");
            pridatDoKosikaNotifikaciaLabel.setLayoutX(660);
        }

        oznamPridanieDoKosika();
    }

    @FXML
    public void onSpatButtonClicked() {
        Scene obchodScene = ViewFactory.INSTANCE.getObchodScene(mainStage);
        mainStage.setScene(obchodScene);
    }

    private void oznamPridanieDoKosika() {
        SequentialTransition st = new SequentialTransition();

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), pridatDoKosikaNotifikaciaLabel);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1.0);
        st.getChildren().add(fadeIn);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), pridatDoKosikaNotifikaciaLabel);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0);
        st.getChildren().add(fadeOut);

        st.playFromStart();
        
    }
}
