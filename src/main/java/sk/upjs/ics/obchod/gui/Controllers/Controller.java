package sk.upjs.ics.obchod.gui.Controllers;

import javafx.stage.Stage;

public abstract class Controller {
        
    protected Stage mainStage;

    public void setStage(Stage mainStage) {
        this.mainStage = mainStage;
    }
}
