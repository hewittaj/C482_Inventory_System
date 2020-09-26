package main;

import controllers.MainScreenController;
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
        // Set up our inventory and add test data to it
        Inventory inventory = new Inventory();
        addTestDataToInventory(inventory);
        
        // Set up our loader for the mainscreen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MainScreen.fxml"));
        
        /**
         * In my MainScreen.fxml I deleted a line called <AnchorPane id="AnchorPane" prefHeight="500.0" 
         * prefWidth="1000.0" xmlns="http://javafx.com/javafx/11.0.1" 
         * xmlns:fx="http://javafx.com/fxml/1"fx:controller="controllers.MainScreenController">
         * This is because the loader needs a zero argument constructor, but we pass it inventory.
         */
        controllers.MainScreenController controller = new MainScreenController(inventory);
        loader.setController(controller);
        
        Parent root = loader.load();
        Scene mainScreenScene = new Scene(root);
        stage.setScene(mainScreenScene);
        stage.show();
    }
    
    /**
     * This function adds test data to our inventory
     * @param inventory Our inventory that we pass through to add test data
     */
    public static void addTestDataToInventory(Inventory inventory){
        
        // Add InHouse Parts
        Part testPartOne = new InHouse(1, "motherboard", 200.00, 5, 1, 10, 201);
        Part testPartTwo = new InHouse(2, "cpu", 299.00, 7, 1, 20, 202);
        Part testPartThree = new InHouse(3, "graphics card", 399.00, 3, 1, 40, 203);
        inventory.addPart(testPartOne);
        inventory.addPart(testPartTwo);
        inventory.addPart(testPartThree);
        
        // Add Outsourced Parts
        Part testPartFour = new Outsourced(4, "high end motherboard", 500.00, 3, 1, 20, "ASUS");
        Part testPartFive = new Outsourced(5, "high end cpu", 700.00, 4, 1, 15, "AMD");
        Part testPartSix = new Outsourced(6, "high end graphics card", 1000.00, 2, 1, 10, "MSI");
        inventory.addPart(testPartFour);
        inventory.addPart(testPartFive);
        inventory.addPart(testPartSix);
        
        // Add Products
        Product productOne = new Product(1, "low end pc", 500.00, 3, 1, 20);
        Product productTwo = new Product(2, "mid range pc", 700.00, 4, 1, 15);
        Product productThree = new Product(3, "high end pc", 1000.00, 2, 1, 10);
        
        // Adding associated parts
        productOne.addAssociatedPart(testPartOne);
        productOne.addAssociatedPart(testPartTwo);
        productOne.addAssociatedPart(testPartThree);
        
        productTwo.addAssociatedPart(testPartOne);
        productTwo.addAssociatedPart(testPartFive);
        productTwo.addAssociatedPart(testPartSix);
        
        productThree.addAssociatedPart(testPartFour);
        productThree.addAssociatedPart(testPartFive);
        productThree.addAssociatedPart(testPartSix);
        
        // Add products to inventory
        inventory.addProduct(productOne);
        inventory.addProduct(productTwo);
        inventory.addProduct(productThree);
    }
}
