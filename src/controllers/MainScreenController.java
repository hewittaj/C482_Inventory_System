package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Inventory;
import models.Part;
import models.Product;

/**
 * Controller class for the main screen of the Inventory System
 * @author alexhewitt
 */
public class MainScreenController implements Initializable {
    
    // FXML Instantiation
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
    @FXML private TextField partSearchBar = new TextField();
    @FXML private TableColumn<Product, Integer> productIdColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private TableColumn<Product, Integer> productInventoryLevelColumn;
    @FXML private TableColumn<Product, Double> productPriceColumn;
    @FXML private TableView<Part> partTableView;
    @FXML private TableView<Product> productTableView;
    @FXML private TextField productSearchBar = new TextField();
    Inventory mainInventory;
    int errorNumber;
    boolean errorThrown;
    
    private ObservableList<Part> partInventoryList = FXCollections.observableArrayList();
    private ObservableList<Product> productInventoryList = FXCollections.observableArrayList();
    private ObservableList<Part> partInventorySearchList = FXCollections.observableArrayList();
    private ObservableList<Product> productInventorySearchList = FXCollections.observableArrayList();

    /**
     * This method sets up our constructor for the Main Screen Controller
     * @param inventory Takes in an inventory instance and initializes it for the controller
     */
    public MainScreenController(Inventory inventory) {
        this.mainInventory = inventory;
    }
    
    /**
     * This method detects if the add part button is pushed and loads the screen
     * @param event Catches events that happen in order to capture button push
     * @throws java.io.IOException Exception that is thrown if an IO exception is found
     */
    @FXML
    public void addPartButtonPushed(ActionEvent event) throws IOException{
        // Loader that loads up our AddPart fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddPart.fxml"));
        AddPartController controller = new AddPartController(mainInventory);
        loader.setController(controller);

        // Set parent and scene
        Parent addPartParent = loader.load();
        Scene addPartScene = new Scene(addPartParent);

        // This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartScene);
        window.show();
    }
    
    /**
     * This method detects when the add product button is pushed and loads the 
     * AddProduct screen
     * @param event Event that is captured to detect the button push
     * @throws IOException Exception that is caught if errors with screen loading
     */
    @FXML
    public void addProductButtonPushed(ActionEvent event) throws IOException{
        // Loader that loads our Add Product fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddProduct.fxml"));
        AddProductController controller = new AddProductController(mainInventory);
        loader.setController(controller);

        // Set parent and scene
        Parent addProductParent = loader.load();
        Scene addProductScene = new Scene(addProductParent);

        // This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addProductScene);
        window.show();
    }
    
    /**
     * This method detects if the modify product button is pushed and loads the screen
     * @param event Event that is captured to detect if the modify button is pushed
     * @throws IOException Exception that is thrown if an IO exception is found
     */
    @FXML
    public void modifyProductButtonPushed(ActionEvent event) throws IOException{
        try{
            // Get selected product
            Product productSelected = productTableView.getSelectionModel().getSelectedItem();
            
            // If no product is selected
            if(productSelected == null){
                Alert noPartSelected = new Alert(Alert.AlertType.ERROR);
                noPartSelected.setTitle("ERROR!");
                noPartSelected.setHeaderText("NO PRODUCT SELECTED!");
                noPartSelected.setContentText("A product needs to be selected to modify it!");
                noPartSelected.showAndWait();
                return;
            }
            // Loader setup for loading our modify product fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ModifyProduct.fxml"));
            ModifyProductController controller = new ModifyProductController(mainInventory, productSelected);
            loader.setController(controller);

            //Set parent and scene
            Parent modifyProductParent = loader.load();
            Scene modifyProductScene = new Scene(modifyProductParent);

            //This line gets the Stage information
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(modifyProductScene);
            window.show();
        }catch(IOException e){
            
        }
    }
    
    /**
     * This method detects if the modify part button is pushed and loads the screen
     * @param event Event that is captured to detect if the button is pushed
     * @throws IOException Exception caught that detects any IO exceptions
     */
    @FXML
    public void modifyPartButtonPushed(ActionEvent event) throws IOException{
        try{

            //Set the scene for the ModifyPart Screen
            Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
            
            // If no part is selected
            if(selectedPart == null){
                Alert noPartSelected = new Alert(Alert.AlertType.ERROR);
                noPartSelected.setTitle("ERROR!");
                noPartSelected.setHeaderText("NO PART SELECTED!");
                noPartSelected.setContentText("A part needs to be selected to modify it!");
                noPartSelected.showAndWait();
                return;
            }
            // Load .fxml and set controller
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ModifyPart.fxml"));
            ModifyPartController controller = new ModifyPartController(mainInventory, selectedPart);
            loader.setController(controller);
            
            // Set parent and scene
            Parent modifyPartParent = loader.load();
            Scene modifyPartScene = new Scene(modifyPartParent);
            
            // This line gets the Stage information
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(modifyPartScene);
            window.show();
        }catch(IOException e){
            
        }   
    }

    /**
     * Initializes the controller class.
     * @param url N/a
     * @param rb N/a
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Create a new inventory that contains our parts and products and add test data
        
        // Add our parts to our table view and set it up
        partInventoryList.setAll(Inventory.getAllParts());
        partTableView.setItems(partInventoryList);

        // Set the table columns up
        partIdColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        
        
        // Add our products to our table view and set it up
        productInventoryList.setAll(Inventory.getAllProducts());
        productTableView.setItems(productInventoryList);

        // Set the table columns up
        productIdColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        productInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
    }
    
    /**
     * This method detects if the exit button is pushed and will exit the program 
     * @param event Event that is caught if exit button is pushed
     */
    @FXML
    public void exitButtonPushed(ActionEvent event){
        Platform.exit();
    }
    
    /**
     * This method detects if the delete part button is pushed and will delete a part
     * @param event Event that is caught to detect button push
     */
    @FXML
    public void deletePartButtonPushed(ActionEvent event){
        this.errorNumber = 0;
        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
        // If part list is empty
        if(partInventoryList.isEmpty()){
            errorThrown = true;
            this.errorNumber = 1;
            showAlert(errorNumber);
        }
        
        // If part list is not empty, but our selected part is null
        if(selectedPart == null && !partInventoryList.isEmpty()){
            errorThrown = true;
            this.errorNumber = 2;
            showAlert(errorNumber);
        }
        // If no errors thrown
        if(this.errorNumber == 0){
            Alert confirmDelete = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDelete.setTitle("CONFIRM DELETE");
            confirmDelete.setHeaderText("Are you sure you would like to delete this part?");
            Optional<ButtonType> confirm = confirmDelete.showAndWait();
            if(confirm.get() == ButtonType.OK){
                partInventoryList.remove(selectedPart);
                mainInventory.deletePart(selectedPart);
            }else{
                return;
            }
        }  
    }
    
    /**
     * This method detects if the delete product button is pushed
     * @param event Event that is caught to detect button push
     */
    @FXML
    public void deleteProductButtonPushed(ActionEvent event){
        this.errorNumber = 0;
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        // If part list is empty
        if(productInventoryList.isEmpty()){
            errorThrown = true;
            this.errorNumber = 1;
            showAlert(errorNumber);
        }
        
        // If part list is not empty, but our selected part is null
        if(selectedProduct == null && !productInventoryList.isEmpty()){
            errorThrown = true;
            this.errorNumber = 2;
            showAlert(errorNumber);
        }
        
        // If no error is thrown
        if(this.errorNumber == 0){
            Alert confirmDelete = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDelete.setTitle("CONFIRM DELETE");
            confirmDelete.setHeaderText("Are you sure you would like to delete this part?");
            Optional<ButtonType> confirm = confirmDelete.showAndWait();
            if(confirm.get() == ButtonType.OK){
                productInventoryList.remove(selectedProduct);
                mainInventory.deleteProduct(selectedProduct);
            }else{
                return;
            }
        }  
    }
    
    /**
     * This method searches for the product specified in the text box
     * (via id, or name)
     * @param event Event that is caught to detect if search button is pushed
     */
    @FXML
    public void searchProduct(ActionEvent event){
        // If no text is in the search bar
        if(productSearchBar.getText().isEmpty()){
                return;
        }
        
        // If search bar contains text (name)
        if(productSearchBar.getText().matches("[a-zA-Z]+")){
            productInventorySearchList.clear(); // Remove elements from any previous search
            productInventorySearchList = mainInventory.lookupProduct(productSearchBar.getText().trim());
            productTableView.setItems(productInventorySearchList);
            productTableView.refresh(); 
        }
        
        // If search bar contains numbers (id)
        if(productSearchBar.getText().matches("^[0-9]*$")){
            int id = Integer.valueOf(productSearchBar.getText());
            Product returnedProduct;
            productInventorySearchList.clear(); // Remove elements from any previous search
            returnedProduct = mainInventory.lookupProduct(id);
            productInventorySearchList.add(returnedProduct);
            productTableView.setItems(productInventorySearchList);
            productTableView.refresh(); 
        }
    }
    
    /**
     * This method searches for the part specified in the text box 
     * (via id, or name)
     * @param event Event that is caught to detect if the search button is pushed
     */
    @FXML
    public void searchPart(ActionEvent event){
        // If no text is in the search bar
        if(partSearchBar.getText().isEmpty()){
                return;
        }
        
        // If search bar contains text (name)
        if(partSearchBar.getText().matches("[a-zA-Z]+")){
            partInventorySearchList.clear(); // Remove elements from any previous search
            partInventorySearchList = mainInventory.lookupPart(partSearchBar.getText().trim());
            partTableView.setItems(partInventorySearchList);
            partTableView.refresh(); 
        }
        
        // If search bar contains numbers (id)
        if(partSearchBar.getText().matches("^[0-9]*$")){
            int id = Integer.valueOf(partSearchBar.getText());
            Part returnedPart;
            partInventorySearchList.clear(); // Remove elements from any previous search
            returnedPart = mainInventory.lookupPart(id);
            partInventorySearchList.add(returnedPart);
            partTableView.setItems(partInventorySearchList);
            partTableView.refresh(); 
        }
    }
    
    /**
     * This method is ran when the product search bar is clicked to reset the 
     * table view
     * @param event Event that is caught to detect mouse click
     */
    @FXML
    public void resetProductTableAfterSearch(MouseEvent event){
        productSearchBar.setText("");
        productTableView.setItems(productInventoryList);
        productTableView.refresh();
    }
    
    /**
     * This method is ran when the part search bar is clicked to reset the table
     * view
     * @param event Event that is caught to detect mouse click
     */
    @FXML
    public void resetPartTableAfterSearch(MouseEvent event){
        partSearchBar.setText("");
        partTableView.setItems(partInventoryList);
        partTableView.refresh();
    }
    
    /**
     * This method is the logic for checking if an error was thrown
     */
    public void checkForError(){
        
    }
    
    /**
     * This method shows an alert based on the error number
     * @param errorNumber Number that specifies which error occurred
     */
    public void showAlert(int errorNumber){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText("Error has occured");
        
        if(errorNumber == 0){
            return;
        }
        if(errorNumber == 1){
            alert.setContentText("List is empty!");
        }
        if(errorNumber == 2){
            alert.setContentText("Nothing is selected!");
        }
        alert.showAndWait();
    }
}
