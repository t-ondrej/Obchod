package sk.upjs.ics.obchod.utils;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sk.upjs.ics.obchod.gui.Controllers.Controller;

public class SceneLoader {
    
    public static Scene load(String filePath, Stage mainStage)
    {
        Scene scene = null;
        
        try {
                FXMLLoader loader = new FXMLLoader(new URL(filePath));
                scene = new Scene(loader.load());
                Controller controller = loader.getController();
                controller.setStage(mainStage);

            } catch (IOException e) {
                System.err.printf("Nepodarilo sa nacitat %s", filePath);
            }
        
        return scene;
    }
}
