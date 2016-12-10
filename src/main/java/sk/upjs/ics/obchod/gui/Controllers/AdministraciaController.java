package sk.upjs.ics.obchod.gui.Controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sk.upjs.ics.obchod.gui.ViewFactory;
import sk.upjs.ics.obchod.services.DefaultPouzivatelManager;

public class AdministraciaController {

    private Stage mainStage;


    public void setStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @FXML
    public void onOdhlasitLabelClicked() {
        Scene obchodScene = ViewFactory.INSTANCE.getObchodScene(mainStage);
        mainStage.setScene(obchodScene);
        DefaultPouzivatelManager.INSTANCE.odhlasPouzivatela();
    }
}
