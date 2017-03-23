package sk.upjs.ics.obchod.gui.Controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import sk.upjs.ics.obchod.gui.ViewFactory;
import sk.upjs.ics.obchod.managers.EntityManagerFactory;

public class AdministraciaController extends AbstractController {

    @FXML
    public void onOdhlasitLabelClicked() {
        Scene obchodScene = ViewFactory.INSTANCE.getObchodScene(mainStage);
        EntityManagerFactory.INSTANCE.getPouzivatelManager().odhlasPouzivatela();
        mainStage.setScene(obchodScene);     
    }
}
