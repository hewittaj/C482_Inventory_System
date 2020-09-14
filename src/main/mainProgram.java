package main;

import static controllers.MainScreenController.addTestDataToInventory;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.InHouse;
import models.Inventory;
import models.Outsourced;
import models.Part;
import models.Product;

/**
 * This is the main program that runs our Inventory System
 * @author alexhewitt
 */
public class mainProgram extends Application{
    /**
     * Launches the main program
     * @param args The command line arguments
     * 
     */
    public static void main(String[] args){
        launch(args);
    }
    /**
     * 
     * @param stage Stage that we start with
     * @throws Exception thrown if there is an error starting the stage
     */
    
    @Override
    public void start(Stage stage) throws Exception {
        //TO DO

        Parent root = FXMLLoader.load(getClass().getResource("/views/MainScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        
    }
    
}
