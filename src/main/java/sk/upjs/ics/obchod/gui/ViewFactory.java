package sk.upjs.ics.obchod.gui;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sk.upjs.ics.obchod.gui.Controllers.AbstractController;

public enum ViewFactory {
    INSTANCE;

    private Scene obchodScene;

    private Scene prihlasenieScene;

    private Scene registraciaScene;

    public Scene getObchodScene(Stage mainStage) {
        if (obchodScene == null) 
            obchodScene = sceneLoader("/fxml/Obchod.fxml", mainStage);
        
        return obchodScene;
    }

    public Scene getPrihlasenieScene(Stage mainStage) {
        if (prihlasenieScene == null) 
            prihlasenieScene = sceneLoader("/fxml/Prihlasenie.fxml", mainStage);

        return prihlasenieScene;
    }

    public Scene getRegistraciaScene(Stage mainStage) {
        if (registraciaScene == null) 
            registraciaScene = sceneLoader("/fxml/Registracia.fxml", mainStage);
        
        return registraciaScene;
    }

    public Scene getAdministraciaScene(Stage mainStage) {           
        return sceneLoader("/fxml/Administracia.fxml", mainStage);
    }
    
    private Scene sceneLoader(String filePath, Stage mainStage)
    {
        Scene scene = null;
        
        try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(filePath));
                scene = new Scene(loader.load());
                AbstractController controller = loader.getController();
                controller.setStage(mainStage);

            } catch (IOException e) {
                System.err.printf("Nepodarilo sa nacitat %s", filePath);
            }
        
        return scene;
    }
}
