package sk.upjs.ics.obchod.gui.Controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sk.upjs.ics.obchod.gui.ViewFactory;
import sk.upjs.ics.obchod.managers.DefaultPouzivatelManager;

public class AdministraciaController {

    private Stage mainStage;


    public void setStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @FXML
    public void onOdhlasitLabelClicked() {
        Scene obchodScene = ViewFactory.INSTANCE.getObchodScene(mainStage);
        DefaultPouzivatelManager.INSTANCE.odhlasPouzivatela();
        mainStage.setScene(obchodScene);     
    }
}
