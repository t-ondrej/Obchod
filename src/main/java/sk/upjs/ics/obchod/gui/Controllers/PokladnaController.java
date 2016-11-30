package sk.upjs.ics.obchod.gui.Controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sk.upjs.ics.obchod.gui.ViewFactory;

public class PokladnaController {
 
    @FXML
    private Button kupitButton;
    
    private Stage mainStage;
    
    public void setStage(Stage mainStage) {
        this.mainStage = mainStage;
    }
    
    @FXML
    public void onKupitButtonClicked() {
        
    }
    
    @FXML
    public void onSpatLabelClicked() {
        Scene obchodScene = ViewFactory.INSTANCE.getObchodScene(mainStage);
        mainStage.setScene(obchodScene);
    }
}
