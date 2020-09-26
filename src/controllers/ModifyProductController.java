package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
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
 * FXML Controller class
 * @author alexhewitt
 */
public class ModifyProductController implements Initializable {

    // FXML instantiation
    @FXML private TextField productIdTextField;
    @FXML private TextField productNameTextField;
    @FXML private TextField productInvTextField;
    @FXML private TextField productPriceTextField;
    @FXML private TextField productMaxTextField;
    @FXML private TextField productMinTextField;
    @FXML private TextField productSearchTextField;
    @FXML private TableColumn<Part, Integer> productIdTopColumn;
    @FXML private TableColumn<Part, String> productNameTopColumn;
    @FXML private TableColumn<Part, Integer> productInventoryTopColumn;
    @FXML private TableColumn<Part, Double> productPriceTopColumn;
    @FXML private Button productAddButton;
    @FXML private TableColumn<Part, Integer> productIdBottomColumn;
    @FXML private TableColumn<Part, String> productNameBottomColumn;
    @FXML private TableColumn<Part, Integer> productInventoryBottomColumn;
    @FXML private TableColumn<Part, Double> productPriceBottomColumn;
    @FXML private Button productRemoveButton;
    @FXML private Button productSaveButton;
    @FXML private Button productCancelButton;
    @FXML private TableView<Part> modifyProductTopTableView;
    @FXML private TableView<Part> modifyProductBottomTableView;
    
    Inventory mainInventory;
    Product productSelected;
    Part selectedPart;
    int errorNumber;
    boolean errorThrown = false;
    
    ObservableList <Part> allParts = FXCollections.observableArrayList();
    ObservableList <Part> associatedParts = FXCollections.observableArrayList();
    ObservableList<Part> partInventorySearchList = FXCollections.observableArrayList();
    
    /**
     * This initializes our Modify Product Controller's constructor
     * @param inv Inventory that is passed from screen to screen
     * @param selectedProduct Product that is selected from the prior screen
     */
    public ModifyProductController(Inventory inv, Product selectedProduct){
        this.mainInventory = inv;
        this.productSelected = selectedProduct;
    }
    
    /**
     * Initializes the Modify Product screen
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Get current associated parts
        associatedParts.setAll(productSelected.getAllAssociatedParts());
        productSearchTextField.setText("");
        // Setup our tables
        setUpProductInfo();
        setAllPartsTable();
        setAssociatedPartsTable();
    }    
    
    /**
     * This method takes us back to the main screen if the cancel button is pushed.
     * It also prompts to make sure the user wants to cancel
     * Alert documentation https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.AlertType.html
     * @param event Event that is caught to detect cancel button pushed
     */
    @FXML
    public void cancelButtonPushed(ActionEvent event){
        try{
            // Set up an alert
            Alert cancelAlert = new Alert(Alert.AlertType.CONFIRMATION);
            cancelAlert.setTitle("CANCEL");
            cancelAlert.setHeaderText("Are you sure you want to cancel?");
            cancelAlert.setContentText("Click 'OK' to confirm.");
            Optional<ButtonType> decision = cancelAlert.showAndWait();
            
            // If the user's decision is 'OK'
            if(decision.get() == ButtonType.OK){

                // Set the scene for the Main Screen
                // Load .fxml and set controller
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MainScreen.fxml"));
                MainScreenController controller = new MainScreenController(mainInventory);
                loader.setController(controller);

                // Set parent and scene
                Parent mainScreenParent = loader.load();
                Scene mainScreenScene = new Scene(mainScreenParent);

                // This line gets the Stage information
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(mainScreenScene);
                window.show();
            }else{
                return;
            }     
        }catch(IOException e){
            
        }   
    }
    
    /**
     * This method checks for errors and saves the modified product we've created
     * @param event Event captured that detects if save button is pushed
     */
    @FXML
    public void saveButtonPushed(ActionEvent event){
        try{
            this.errorNumber = 0;
            checkForErrors();
            showAlert(errorNumber);
            
            if(errorNumber == 0){
                for(Part part: associatedParts){ // Add associated parts to product
                    productSelected.addAssociatedPart(part);
                }
                updateProduct();

                // Set the scene for the Main Screen
                // Load .fxml and set controller
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MainScreen.fxml"));
                MainScreenController controller = new MainScreenController(mainInventory);
                loader.setController(controller);

                // Set parent and scene
                Parent mainScreenParent = loader.load();
                Scene mainScreenScene = new Scene(mainScreenParent);

                // This line gets the Stage information
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(mainScreenScene);
                window.show();
            }
        }catch(IOException e){
            
        }     
    }
    
    /**
     * This method sets up the top table that shows all of the parts currently
     * in the inventory
     */
    public void setAllPartsTable(){
        // Add our parts to our table view and set it up
        allParts.setAll(Inventory.getAllParts());
        
        // Remove any associated parts from allowable parts to add
        for(Part part: associatedParts){
            if(allParts.contains(part)){
                allParts.remove(part);
            }
        }
        modifyProductTopTableView.setItems(allParts);
        
        // Set up columns values
        productIdTopColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        productInventoryTopColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        productNameTopColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        productPriceTopColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
    }
    
    /**
     * This method sets up the bottom table that shows all of the associated parts
     * in the inventory
     */
    public void setAssociatedPartsTable(){
        // Add our associated parts to our table view and set up the table view
        modifyProductBottomTableView.setItems(associatedParts);
        
        // Set up column values
        productIdBottomColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        productInventoryBottomColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        productNameBottomColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        productPriceBottomColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
    }
    
    /**
     * This method sets up the bottom table that shows all of the parts currently
     * associated with the product.
     */
    @FXML
    public void addAssociatedPart(){
        // Parts aren't added to associated product until save button pushed
        this.errorNumber = 0;
        selectedPart = modifyProductTopTableView.getSelectionModel().getSelectedItem();
        if(selectedPart == null){
            Alert nullPart = new Alert(Alert.AlertType.ERROR);
            nullPart.setTitle("ERROR!");
            nullPart.setHeaderText("No part selected!");
            nullPart.setContentText("A part must be selected to add it!");
            nullPart.showAndWait();
            return;
        }
        
        if(errorNumber == 0){
            // Add our selected part to a list of selected parts
            // Add our parts selected to a list of associated parts
            associatedParts.add(selectedPart);
            modifyProductBottomTableView.setItems(associatedParts);
            // Remove selected part from the parts available and refresh
            allParts.remove(selectedPart);
            modifyProductTopTableView.refresh();
            // Set up columns
            productIdBottomColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
            productInventoryBottomColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
            productNameBottomColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
            productPriceBottomColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        }
    }
    
    /**
     * This method removes associated parts that we have added to a product
     */
    @FXML
    public void removeAssociatedPart(){
        // Set up an alert
        Alert removeAlert = new Alert(Alert.AlertType.CONFIRMATION);
        removeAlert.setTitle("REMOVE");
        removeAlert.setHeaderText("Are you sure you want to remove the associated part?");
        removeAlert.setContentText("Click 'OK' to confirm.");
        Optional<ButtonType> decision = removeAlert.showAndWait();
        
        // If user selects 'OK'
        if(decision.get() == ButtonType.OK){
            // Because parts aren't associated to product until save button pushed
            // there is no need to remove associated part from product
            this.errorNumber = 0;
            selectedPart = modifyProductBottomTableView.getSelectionModel().getSelectedItem();
            checkForErrors();
            showAlert(errorNumber);
            
            if(errorNumber == 0){
                // Add our selected part back to our allparts list
                // Remove our selected part from our associated parts list
                associatedParts.remove(selectedPart); // Removing from selected parts
                allParts.add(selectedPart); // Add part back to our main list

                modifyProductTopTableView.setItems(allParts);// Refresh our top table view
                modifyProductBottomTableView.setItems(associatedParts); //Refresh bottom table

                // Remove selected part from the parts available and refresh
                modifyProductTopTableView.refresh();
                modifyProductBottomTableView.refresh();
                // Set up columns
                productIdBottomColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
                productInventoryBottomColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
                productNameBottomColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
                productPriceBottomColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
             }
        }
    }
    
    /**
     * This method updates the product with the new information and associated 
     * parts
     */
    public void updateProduct(){
        mainInventory.updateProduct(
                productSelected.getId()-1, 
                new Product(
                    Integer.valueOf(productIdTextField.getText()),
                    productNameTextField.getText(),
                    Double.valueOf(productPriceTextField.getText()),
                    Integer.valueOf(productInvTextField.getText()),
                    Integer.valueOf(productMinTextField.getText()),
                    Integer.valueOf(productMaxTextField.getText()))
                    );
    }
    
    /**
     * This method sets up our text fields by pulling in the information from 
     * the current selected product from the prior screen
     */
    public void setUpProductInfo(){
        // Set our text fields to the current value
        this.productIdTextField.setText(String.valueOf(productSelected.getId()));
        this.productNameTextField.setText(productSelected.getName());
        this.productInvTextField.setText(String.valueOf(productSelected.getStock()));
        this.productPriceTextField.setText(String.valueOf(productSelected.getPrice()));
        this.productMaxTextField.setText(String.valueOf(productSelected.getMax()));
        this.productMinTextField.setText(String.valueOf(productSelected.getMin()));
    }
    
    /**
     * This method searches for the part specified in the text box 
     * (via id, or name)
     * @param event Event that is captured to detect if the search button is pushed
     */
    @FXML
    public void searchPart(ActionEvent event){
        // If there is no text in the search bar
        if(productSearchTextField.getText().isEmpty()){
                return;
        }
        
        // If search bar contains text (name)
        if(productSearchTextField.getText().matches("[a-zA-Z]+")){
            partInventorySearchList.clear(); // Remove elements from any previous search
            partInventorySearchList = mainInventory.lookupPart(productSearchTextField.getText().trim());
            modifyProductTopTableView.setItems(partInventorySearchList);
            modifyProductTopTableView.refresh(); 
        }
        
        // If search bar contains numbers (id)
        if(productSearchTextField.getText().matches("^[0-9]*$")){
            int id = Integer.valueOf(productSearchTextField.getText());
            Part returnedPart;
            partInventorySearchList.clear(); // Remove elements from any previous search
            returnedPart = mainInventory.lookupPart(id);
            partInventorySearchList.add(returnedPart);
            modifyProductTopTableView.setItems(partInventorySearchList);
            modifyProductTopTableView.refresh(); 
        }
    }
    
    /**
     * This method is ran when the part search bar is clicked to restore it
     * @param event Event that is captured to detect if mouse is clicked
     */
    @FXML
    public void resetPartTableAfterSearch(MouseEvent event){
        productSearchTextField.setText("");
        modifyProductTopTableView.setItems(allParts);
        modifyProductTopTableView.refresh();
    }
    
    /**
     * This method checks for errors in adding a product/part
     */
    public void checkForErrors(){
        /**
         * If part selected is empty an error is thrown
         */
        if(selectedPart == null){
            this.errorNumber = 1;
            errorThrown = true;
            return;
        }
        
        /**
         * If any of the text fields are empty an error is thrown
         */
        if(     productNameTextField.getText().isEmpty() ||
                productInvTextField.getText().isEmpty() ||
                productPriceTextField.getText().isEmpty() ||
                productMaxTextField.getText().isEmpty() ||
                productMinTextField.getText().isEmpty()){
            this.errorNumber = 2;
            errorThrown = true;
            return;
        }
        
        /**
         * If product doesn't have one associated part an error is thrown
         */
        if(associatedParts.isEmpty()){
            this.errorNumber = 3;
            errorThrown = true;
            return;
        }
        
        /**
         * If inv, price, max or min contain letters an error is thrown
         */
        if(
                productInvTextField.getText().matches("[a-zA-Z]+") ||
                productPriceTextField.getText().matches("[a-zA-Z]+") ||
                productMaxTextField.getText().matches("[a-zA-Z]+") ||
                productMinTextField.getText().matches("[a-zA-Z]+")){
            
            errorThrown = true;
            this.errorNumber = 5;
            return;
        }
        
        /**
         * If the product name field contains numbers an error is thrown
         */
        if(productNameTextField.getText().matches("^[0-9]*$")){
            errorThrown = true;
            this.errorNumber = 6;
            return;
        }
        
        /**
         * If any fields are less than zero an error is thrown
         */
        if(     Integer.valueOf(productInvTextField.getText()) < 0 ||
                Double.valueOf(productPriceTextField.getText()) < 0 ||
                Integer.valueOf(productMaxTextField.getText()) < 0 ||
                Integer.valueOf(productMinTextField.getText()) < 0){
            this.errorNumber = 4;
            errorThrown = true;
            return;
        }  
        
        /**
         * If the inventory value is greater than the max value an error is thrown
         */
        if(Integer.valueOf(productInvTextField.getText()) > Integer.valueOf(productMaxTextField.getText())){
            this.errorNumber = 7;
            errorThrown = true;
            return;
        }
        
        /**
         * If the inventory value is less than the min value an error is thrown
         */
        if(Integer.valueOf(productInvTextField.getText()) < Integer.valueOf(productMinTextField.getText())){
            this.errorNumber = 8;
            errorThrown = true;
            return;
        }
        /**
         * If the min field is greater than the max field an error is thrown
         */
        if(Integer.valueOf(productMinTextField.getText()) > Integer.valueOf(productMaxTextField.getText())){
            this.errorNumber = 9;
            errorThrown = true;
            return;
        }
    }
    
    /**
     * This method shows alerts based on checkForErrors() result
     * @param errorCode Error number that determines which alert we show
     */
    public void showAlert(int errorCode){
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("ERROR!");
        error.setHeaderText("Error adding product!");
        if(this.errorNumber == 0){
            return;
        }
        if(this.errorNumber == 1){
            error.setHeaderText("Error adding associated part!");
            error.setContentText("A part must be selected or a part must be added!");           
        }
        if(this.errorNumber == 2){
            error.setContentText("All fields must have content!");   
        }
        if(this.errorNumber == 3){
            error.setContentText("A product must have at least one associated part!");
        }
        if(this.errorNumber == 4){
            error.setContentText("Values of inv, price, max, and min must not be less than zero!");
        }
        if(this.errorNumber == 5){
            error.setContentText("Values of inv, price, max, and min must not contain letters");
        }
        if(this.errorNumber == 6){
            error.setContentText("Name of product can't contain numbers!");
        }
        if(this.errorNumber == 7){
            error.setContentText("Inventory cannot be greater than max!");
        }
        if(this.errorNumber == 8){
            error.setContentText("Inventory cannot be less than min!");
        }
        if(this.errorNumber == 9){
            error.setContentText("Min cannot be greater than max!");
        }
        error.showAndWait();
        return;
    }  
}