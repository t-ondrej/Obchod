package sk.upjs.ics.obchod.gui;

import sk.upjs.ics.obchod.gui.Controllers.PrihlasenieController;
import sk.upjs.ics.obchod.gui.Controllers.RegistraciaController;
import sk.upjs.ics.obchod.gui.Controllers.ObchodController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sk.upjs.ics.obchod.gui.Controllers.AdministraciaController;
import sk.upjs.ics.obchod.gui.Controllers.PokladnaController;

public enum ViewFactory {
    INSTANCE;

    private Scene obchodScene;

    private Scene prihlasenieScene;

    private Scene registraciaScene;
    
    private Scene pokladnaScene;
    
    private Scene administraciaScene;

    public Scene getObchodScene(Stage mainStage) {
        if (obchodScene == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Obchod.fxml"));
                obchodScene = new Scene(loader.load());
                ObchodController obchodController = loader.getController();
                obchodController.setStage(mainStage);
                
            } catch (IOException e) {
                System.err.println("Nepodarilo sa nacitat Obchod.fxml");
                e.printStackTrace();
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
                e.printStackTrace();
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
                e.printStackTrace();
            }
        }

        return registraciaScene;
    }
    
    public Scene getPokladnaScene(Stage mainStage) {
        if (pokladnaScene == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Pokladna.fxml"));
                pokladnaScene = new Scene(loader.load());
                PokladnaController pokladnaController = loader.getController();
                pokladnaController.setStage(mainStage);
                
            } catch (IOException e) {
                System.err.println("Nepodarilo sa nacitat Pokladna.fxml");
                e.printStackTrace();
            }
        }

        return pokladnaScene;
    }
    
    public Scene getAdministraciaScene(Stage mainStage) {
        if (administraciaScene == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Administracia.fxml"));
                administraciaScene = new Scene(loader.load());
                AdministraciaController administraciaController = loader.getController();
                administraciaController.setStage(mainStage);
                
            } catch (IOException e) {
                System.err.println("Nepodarilo sa nacitat Administracia.fxml");
                e.printStackTrace();
            }
        }

        return administraciaScene;
    }   
}
