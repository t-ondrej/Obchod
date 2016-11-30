package sk.upjs.ics.obchod.gui;

import sk.upjs.ics.obchod.gui.Controllers.PrihlasenieController;
import sk.upjs.ics.obchod.gui.Controllers.RegistraciaController;
import sk.upjs.ics.obchod.gui.Controllers.ObchodController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Pagination;
import javafx.stage.Stage;

public enum ViewFactory {
    INSTANCE;

    private Scene obchodScene;

    private Scene prihlasenieScene;

    private Scene registraciaScene;
    
    private Pagination tovarPagination;

    public Scene getObchodScene(Stage mainStage) {
        if (obchodScene == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Obchod.fxml"));
                obchodScene = new Scene(loader.load());
                ObchodController obchodController = loader.getController();
                obchodController.setStage(mainStage);
                
            } catch (IOException e) {
                System.err.println("Nepodarilo sa nacitat Obchod.fxml");
            }
        }

        return obchodScene;
    }

    public Scene getPrihlasenieScene(Stage mainStage) {
        if (prihlasenieScene == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Prihlasenie.fxml"));
                prihlasenieScene = new Scene(loader.load());
                PrihlasenieController prihlasenieController = loader.getController();
                prihlasenieController.setStage(mainStage);
                
            } catch (IOException e) {
                System.err.println("Nepodarilo sa nacitat Prihlasenie.fxml");
            }
        }
        return prihlasenieScene;
    }

    public Scene getRegistraciaScene(Stage mainStage) {
        if (registraciaScene == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Registracia.fxml"));
                registraciaScene = new Scene(loader.load());
                RegistraciaController registraciaController = loader.getController();
                registraciaController.setStage(mainStage);
                
            } catch (IOException e) {
                System.err.println("Nepodarilo sa nacitat Registracia.fxml");
            }
        }

        return registraciaScene;
    }
}
