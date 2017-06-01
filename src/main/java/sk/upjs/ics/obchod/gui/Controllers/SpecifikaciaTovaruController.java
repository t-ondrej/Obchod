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
import javafx.util.Duration;
import sk.upjs.ics.obchod.dao.DaoFactory;
import sk.upjs.ics.obchod.entity.Product;
import sk.upjs.ics.obchod.gui.ViewFactory;
import sk.upjs.ics.obchod.managers.EntityManagerFactory;
import sk.upjs.ics.obchod.dao.IProductDao;
import sk.upjs.ics.obchod.entity.Cart;

public class SpecifikaciaTovaruController extends Controller {

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

    private IProductDao mysqlTovarDao;

    private Product tovar;
    
    private Cart activeAccountCart;

    // TODO kosikManager pri prihlasovani/odhlasovani
    public void inicializuj(String nazovTovaru) {
        mysqlTovarDao = DaoFactory.INSTANCE.getMysqlProductDao();
        tovar = mysqlTovarDao.findByName(nazovTovaru);
        
        activeAccountCart = EntityManagerFactory.INSTANCE.getAccountManager().getActiveAccount().getCart();

        Image obrazok = new Image("file:" + tovar.getImagePath());

        tovarImageView.setImage(obrazok);
        nazovTovaruLabel.setText(tovar.getName());
        specifikaciaTovaruTextArea.setText(tovar.getDescription());
        cenaLabel.setText(Integer.toString(tovar.getPrice()) + " €");
        
        tovarImageView.requestFocus();
    }

    @FXML
    public void onPridatDoKosikaButtonClicked() {

        if (EntityManagerFactory.INSTANCE.getAccountManager().getActiveAccount() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Upozornenie");
            alert.setHeaderText("Ak chcete nakupovať, musite sa prihlásiť.");
            alert.showAndWait();
            return;
        }
        
        if (activeAccountCart.addProductToCart(tovar, 1)) {
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
