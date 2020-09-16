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
//import javafx.scene.control.TableCell;
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
    Inventory mainInventory;
    
    private ObservableList<Part> partInventoryList = FXCollections.observableArrayList();
    private ObservableList<Product> productInventoryList = FXCollections.observableArrayList();
    private ObservableList<Part> partInventorySearchList = FXCollections.observableArrayList();
    private ObservableList<Product> productInventorySearchList = FXCollections.observableArrayList();
    //FXML Method Instantiation

    public MainScreenController(Inventory inventory) {
        this.mainInventory = inventory;
    }
    
    /**
     *
     * @param event
     * @throws java.io.IOException
     */
    @FXML
    public void addPartButtonPushed(ActionEvent event) throws IOException{
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/views/AddPart.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getTarget()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    
    /**
     * TO DO
     * @param event
     * @throws IOException 
     */
    @FXML
    public void modifyPartButtonPushed(ActionEvent event) throws IOException{
        try{
            //TO DO SET UP ERRORS IF NO PART SELECTED
            //For the .fxml in this one as well I deleted the controller fx:id 
            //Set the scene for the ModifyPart Screen
            Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
            //Load .fxml and set controller
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ModifyPart.fxml"));
            ModifyPartController controller = new ModifyPartController(mainInventory, selectedPart);
            loader.setController(controller);
            
            //Set parent and scene
            Parent modifyPartParent = loader.load();
            Scene modifyPartScene = new Scene(modifyPartParent);
            
            //This line gets the Stage information
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(modifyPartScene);
            window.show();
        }catch(IOException e){
            
        }
        
        
    }
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Create a new inventory that contains our parts and products and add test data
        Inventory allInventory = new Inventory();
        
        //Add our parts to our table view and set it up
        partInventoryList.setAll(Inventory.getAllParts());
        partTableView.setItems(partInventoryList);
        //partTableView.refresh(); possibly unecessary?

        partIdColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        
        
        //Add our products to our table view and set it up
        productInventoryList.setAll(Inventory.getAllProducts());
        productTableView.setItems(productInventoryList);

        productIdColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        productInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
    }
    
//    private <T> TableColumn<T, Double> formatPriceColumn(){
//        TableColumn<T, Double> priceColumn= new TableColumn("Price");
//        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
//    
//        priceColumn.setCellFactory((TableColumn<T, Double> column) -> {
//            return new TableCell<T, Double>() {
//                @Override
//                protected void updateItem(Double item, boolean empty) {
//                    if (!empty) {
//                        setText("$" + String.format("%10.2f", item));
//                    }
//                    else{
//                        setText("");
//                    }
//                }
//            };
//        });
//        return priceColumn;
//    }
    
    
}
