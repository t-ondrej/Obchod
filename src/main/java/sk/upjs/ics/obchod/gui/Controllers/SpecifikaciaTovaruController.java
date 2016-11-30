package sk.upjs.ics.obchod.gui.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sk.upjs.ics.obchod.dao.DaoFactory;
import sk.upjs.ics.obchod.dao.TovarDao;
import sk.upjs.ics.obchod.entity.Kosik;
import sk.upjs.ics.obchod.entity.Tovar;
import sk.upjs.ics.obchod.gui.ViewFactory;
import sk.upjs.ics.obchod.services.DefaultKosikManager;
import sk.upjs.ics.obchod.services.DefaultPouzivatelManager;
import sk.upjs.ics.obchod.services.KosikManager;

public class SpecifikaciaTovaruController {
    
    @FXML
    private Button pridatButtonDoKosika;
    
    @FXML
    private Label cenaLabel;
    
    @FXML
    private Label nazovTovaruLabel;
    
    @FXML
    private ImageView tovarImageView;
    
    @FXML
    private TextArea specifikaciaTovaruTextArea;
    
    private Stage mainStage;
    
    private KosikManager defaultKosikManager;
    
    private TovarDao mysqlTovarDao; 
    
    private Tovar tovar;
    
    public void setStage(Stage mainStage) {
        this.mainStage = mainStage;
    }
    
    public void inicializuj(String nazovTovaru) {
         this.defaultKosikManager = new DefaultKosikManager();
         mysqlTovarDao = DaoFactory.INSTANCE.getMysqlTovarDao();
         Tovar tovar = mysqlTovarDao.dajTovarPodlaNazvu(nazovTovaru);
         
         Image obrazok = new Image("file:" + tovar.getobrazokUrl());  
         
         tovarImageView.setImage(obrazok);
         nazovTovaruLabel.setText(tovar.getNazov());
         specifikaciaTovaruTextArea.setText(tovar.getPopis());
         cenaLabel.setText(Integer.toString(tovar.getCena()) + " €");        
    }
    
    @FXML
    public void onPridatDoKosikaButtonClicked() {
        Tovar tovar = mysqlTovarDao.dajTovarPodlaNazvu(nazovTovaruLabel.getText());
        Kosik kosik = DefaultPouzivatelManager.INSTANCE.getAktivnyPouzivatel().getKosik();
        defaultKosikManager.pridajTovarDoKosika(tovar, kosik);
    }
    
    @FXML
    public void onSpatButtonClicked() {
        Scene obchodScene = ViewFactory.INSTANCE.getObchodScene(mainStage);
        mainStage.setScene(obchodScene);
    }

   
}
