package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.InHouse;
import models.Inventory;
import models.Outsourced;
import models.Part;
import models.Product;

/**
 * Controller class for the main screen of the Inventory System
 * @author alexhewitt
 */
public class MainScreenController implements Initializable {
    
    //FXML Instantiation
    @FXML private Button addPartButton;
    @FXML private Button modifyPartButton;
    @FXML private Button deletePartButton;
    @FXML private Button addProductButton;
    @FXML private Button modifyProductButton;
    @FXML private Button deleteProductButton;
    @FXML private Button exitButton;
    @FXML private TableColumn<Part, Integer> partIdColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partInventoryLevelColumn;
    @FXML private TableColumn<Part, Double> partPriceColumn;
    @FXML private TextField partSearchBar;
    @FXML private TableColumn<Product, Integer> productIdColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private TableColumn<Product, Integer> productInventoryLevelColumn;
    @FXML private TableColumn<Product, Double> productPriceColumn;
    @FXML private TableView<Part> partTableView;
    @FXML private TableView<Product> productTableView;
    @FXML private TextField procuctSearchBar;
    
    private ObservableList<Part> partInventoryList = FXCollections.observableArrayList();
    private ObservableList<Product> productInventoryList = FXCollections.observableArrayList();
    private ObservableList<Part> partInventorySearchList = FXCollections.observableArrayList();
    private ObservableList<Product> productInventorySearchList = FXCollections.observableArrayList();
    //FXML Method Instantiation
    
    /**
     *
     * @param event
     * @throws java.io.IOException
     */
    public void addPartButtonPushed(ActionEvent event) throws IOException{
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/views/AddPart.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getTarget()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TO DO
        Inventory allInventory = new Inventory();
        addTestDataToInventory(allInventory);
        partInventoryList.setAll(Inventory.getAllParts());
        partTableView.setItems(partInventoryList);
        partTableView.refresh();

        partIdColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        
    }   
    
    /**
     * This function adds test data to our inventory
     */
    public static void addTestDataToInventory(Inventory inventory){
        //Add test data 
        //Following format for programmer reference (InHouse)
        //int id, String name, double price, int stock, int min, int max, machine id
        
        //Add InHouse Parts
        Part testPartOne = new InHouse(1, "motherboard", 200.00, 5, 1, 10, 201);
        Part testPartTwo = new InHouse(2, "cpu", 299.00, 7, 1, 20, 202);
        Part testPartThree = new InHouse(3, "graphics card", 399.00, 3, 1, 40, 203);
        inventory.addPart(testPartOne);
        inventory.addPart(testPartTwo);
        inventory.addPart(testPartThree);
        
        //Add Outsourced Parts
        //int id, String name, double price, int stock, int min, int max, String companyName
        Part testPartFour = new Outsourced(4, "high end motherboard", 500.00, 3, 1, 20, "ASUS");
        Part testPartFive = new Outsourced(5, "high end cpu", 700.00, 4, 1, 15, "AMD");
        Part testPartSix = new Outsourced(6, "high end graphics card", 1000.00, 2, 1, 10, "MSI");
        
        //Add Products
        //int id, String name, double price, int stock, int min, int max
        Product productOne = new Product(1001, "low end pc", 500.00, 3, 1, 20);
        Product productTwo = new Product(1002, "mid range pc", 700.00, 4, 1, 15);
        Product productThree = new Product(1003, "high end pc", 1000.00, 2, 1, 10);
        
        //Adding associated parts
        productOne.addAssociatedPart(testPartOne);
        productOne.addAssociatedPart(testPartTwo);
        productOne.addAssociatedPart(testPartThree);
        
        productTwo.addAssociatedPart(testPartOne);
        productTwo.addAssociatedPart(testPartFive);
        productTwo.addAssociatedPart(testPartSix);
        
        productThree.addAssociatedPart(testPartFour);
        productThree.addAssociatedPart(testPartFive);
        productThree.addAssociatedPart(testPartSix);
        
        //Add products to inventory
        inventory.addProduct(productOne);
        inventory.addProduct(productTwo);
        inventory.addProduct(productThree);
    }
    
}
