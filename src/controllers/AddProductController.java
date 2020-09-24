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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Inventory;
import static models.Inventory.lookupPart;
import models.Part;
import models.Product;

/**
 * FXML Controller class
 *
 * @author alexhewitt
 */
public class AddProductController implements Initializable {

    //Initialize FXML id's
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
    @FXML private Button productRemoveButton;
    @FXML private Button productSaveButton;
    @FXML private Button productCancelButton;
    @FXML private TableColumn<Part, Integer> productIdBottomColumn;
    @FXML private TableColumn<Part, String> productNameBottomColumn;
    @FXML private TableColumn<Part, Integer> productInventoryBottomColumn;
    @FXML private TableColumn<Part, Double> productPriceBottomColumn;
    @FXML private TableView<Part> addProductTopTableView;
    @FXML private TableView<Part> addProductBottomTableView;
    Inventory mainInventory;
    Part selectedPart;
    int errorNumber;
    boolean errorThrown = false;
    Product newProduct;
    
    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();
    private ObservableList<Part> partsSelectedList = FXCollections.observableArrayList();
    
    /**
     * This method is the constructor for 
     * @param inv 
     */
    public AddProductController(Inventory inv){
        this.mainInventory = inv;
    }
    
    
    /**
     * Initializes the controller class
     * @param url N/a
     * @param rb N/a
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getNextIdNumber();
        setAllPartsTable();
    }    
    
    /**
     * This method sets up the top table that shows all of the parts currently
     * in the inventory
     */
    public void setAllPartsTable(){
        // Add our parts to our table view and set it up
        allParts.setAll(Inventory.getAllParts());
        addProductTopTableView.setItems(allParts);
        // Set up columns values
        productIdTopColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        productInventoryTopColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        productNameTopColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        productPriceTopColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
    }
    
    /**
     * This method sets up the bottom table that shows all of the parts currently
     * associated with the product.
     */
    @FXML
    public void addAssociatedPart(){
        // Parts aren't added to associated product until save button pushed
        this.errorNumber = 0;
        selectedPart = addProductTopTableView.getSelectionModel().getSelectedItem();
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
            partsSelectedList.add(selectedPart);
            associatedPartsList.setAll(partsSelectedList);
            addProductBottomTableView.setItems(associatedPartsList);
            // Remove selected part from the parts available and refresh
            allParts.remove(selectedPart);
            addProductTopTableView.refresh();
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
        if(decision.get() == ButtonType.OK){
            // Because parts aren't associated to product until save button pushed
            // there is no need to remove associated part from product
            this.errorNumber = 0;
            selectedPart = addProductBottomTableView.getSelectionModel().getSelectedItem();
            checkForErrors();
            showAlert(errorNumber);
            if(errorNumber == 0){
                // Add our selected part back to our allparts list
                // Remove our selected part from our associated parts list

                partsSelectedList.remove(selectedPart); // Removing from selected parts
                allParts.add(selectedPart); // Add part back to our main list

                addProductTopTableView.setItems(allParts);// Refresh our top table view
                associatedPartsList.setAll(partsSelectedList);// Refresh associated part list
                addProductBottomTableView.setItems(associatedPartsList); //Refresh bottom table

                // Remove selected part from the parts available and refresh
                addProductTopTableView.refresh();
                addProductBottomTableView.refresh();
                // Set up columns
                productIdBottomColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
                productInventoryBottomColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
                productNameBottomColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
                productPriceBottomColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

             }
        }
        
    }
    
    /**
     * This method takes us back to the main screen if the cancel button is pushed.
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
            if(decision.get() == ButtonType.OK){
                // For the .fxml in this one as well I deleted the controller fx:id 
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
     * This method checks for errors and saves the new product we've created
     * @param event Event captured that detects if save button is pushed
     */
    @FXML
    public void saveButtonPushed(ActionEvent event){
        try{
            this.errorNumber = 0;
            checkForErrors();
            showAlert(errorNumber);
            if(errorNumber == 0){
                newProduct = new Product( // Initialize the new product with text fields
                                Integer.valueOf(productIdTextField.getText()),
                                productNameTextField.getText(),
                                Double.valueOf(productPriceTextField.getText()),
                                Integer.valueOf(productInvTextField.getText()),
                                Integer.valueOf(productMinTextField.getText()),
                                Integer.valueOf(productMaxTextField.getText())

                );
                for(Part part: associatedPartsList){ // Add associated parts to product
                    newProduct.addAssociatedPart(part);
                }
                
                mainInventory.addProduct(newProduct);

                // For the .fxml in this one as well I deleted the controller fx:id 
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
        if(associatedPartsList.isEmpty()){
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
    }
    
    /**
     * This method shows alerts based on checkForErrors() result
     * @param errorCode Number that determines which alert we show
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
        error.showAndWait();
        return;
    }
    /**
     * Gets the size of allParts
     * @return 
     */
    public int getSizeOfAllParts(){
        return allParts.size();
    }
    
    /**
     * Generates the next Id Number for a part we want to add
     */
    public void getNextIdNumber(){
        int size = getSizeOfAllParts(); // Set the size
        
        if(size == 0){
            productIdTextField.setText("1");
        }else{
            for(int i = 0; i <=size; i++){ // Loop through the list
                if(i == 0){ // Skip 0 as we don't want an ID of zero
                    continue;
                }
                if(lookupPart(i) == null){ // If part returned is null
                    productIdTextField.setText(String.valueOf(i)); // We can use the id as our number
                    break;// Break from loop
                }else if(lookupPart(i) != null){ // If lookup part matches we continue in the loop  --- lookupPart(i).getId() == i
                    if(i == size){ // If we get to the last index and its not null we assign the value to the last part id + 1
                        // Otherwise we set the id to the last parts id + 1
                        productIdTextField.setText(String.valueOf(Inventory.getLastPartId() + 1));
                    }
                    continue; // If i does not equal the size of the list then continue in the loop
                }    
            }  
        } 
    }
}
