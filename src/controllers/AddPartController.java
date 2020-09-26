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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.InHouse;
import models.Inventory;
import static models.Inventory.lookupPart;
import models.Outsourced;
import models.Part;

/**
 * FXML Controller class for the Add Part Screen
 * @author alexhewitt
 */
public class AddPartController implements Initializable {
    
    // Initialize the FXML portions of the add part screen
    @FXML private RadioButton inHouseRadioButton;
    @FXML private RadioButton outsourcedRadioButton;
    @FXML private Label partSwappableLabel;
    @FXML private TextField partIdTextField;
    @FXML private TextField partNameTextField;
    @FXML private TextField partInvTextField;
    @FXML private TextField partPriceTextField;
    @FXML private TextField partMaxTextField;
    @FXML private TextField partSwappableTextField;
    @FXML private TextField partMinTextField;
    @FXML private Button partSaveButton;
    @FXML private Button partCancelButton;
    Inventory mainInventory;
    ObservableList<Part> allParts = FXCollections.observableArrayList();
    boolean errorThrown;
    int errorCode;
    
    /**
     * Initializes the AddPartController
     * @param inv The inventory that we pass from screen to screen
     */
    public AddPartController(Inventory inv){
        this.mainInventory = inv;
    }

    /**
     * This will deselect the outsourced radio button when the in house is clicked
     * @param event Event that is caught to detect radio button selected
     */
    @FXML
    public void inHouseRadioButtonSelected(ActionEvent event){
        outsourcedRadioButton.setSelected(false);
        partSwappableLabel.setText("Machine ID");
    }
    
    /**
     * This will deselect the in house radio button when the outsourced button is 
     * clicked
     * @param event Event that is caught to detect radio button selected
     */
    @FXML
    public void outsourcedRadioButtonSelected(ActionEvent event){
        inHouseRadioButton.setSelected(false);
        partSwappableLabel.setText("Company Name");
    }
    
    /**
     * Initializes the controller class.
     * @param url N/a
     * @param rb N/a
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize an observable list to be used for other functionality
        allParts.setAll(Inventory.getAllParts());
        
        // Generate an ID number based off the current size of allParts
        getNextIdNumber();
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
            
            // If the user's decision is OK the following code is ran
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
     * Method that is ran when the save button is pushed to save the part we
     * would like to add
     * @param event Event that is caught to detect save button push
     */
    @FXML
    public void saveButtonPushed(ActionEvent event){
        try{
            // Error code is reset and then we check for errors and show any found
            this.errorCode = 0;
            checkForErrors();
            showAlert(errorCode);
            
            if(this.errorCode == 0){
                if(outsourcedRadioButton.isSelected()){
                    addOutsourcedPart();
                }
                if(inHouseRadioButton.isSelected()){
                    addInHousePart();
                }

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
     * Gets the size of allParts
     * @return Returns the size of how many parts are in our inventory
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
            partIdTextField.setText("1");
        }else{
            for(int i = 0; i <=size; i++){ // Loop through the list
                if(i == 0){ // Skip 0 as we don't want an ID of zero
                    continue;
                }
                if(lookupPart(i) == null){ // If part returned is null
                    partIdTextField.setText(String.valueOf(i)); // We can use the id as our number
                    break;// Break from loop
                }else if(lookupPart(i) != null){ // If lookup part matches we continue in the loop  --- lookupPart(i).getId() == i
                    if(i == size){ // If we get to the last index and its not null we assign the value to the last part id + 1
                        // Otherwise we set the id to the last parts id + 1
                        partIdTextField.setText(String.valueOf(Inventory.getLastPartId() + 1));
                    }
                    continue; // If i does not equal the size of the list then continue in the loop
                }    
            }  
        } 
    }
    
    /**
     * This method takes in an errorNumber and outputs a certain alert based on the number
     */
    public void checkForErrors(){
        
        /*
         *If the inhouse radio button is selected and the textfield contains letters
         *we throw an error as it can only contain numbers
         */ 
        if(inHouseRadioButton.isSelected() && partSwappableTextField.getText().matches("[a-zA-Z]+")){
            errorThrown = true;
            this.errorCode = 1;
            return;
        }
        
        /*
         *If the outsourced radio button is selected and the textfield contains numbers
         *we throw an error as it can only contain letters
         */ 
        if(outsourcedRadioButton.isSelected() && partSwappableTextField.getText().matches("^[0-9]*$")){
            errorThrown = true;
            this.errorCode = 2;
            return;
        }
        
        /*
         *If the content of any text field is empty an error is thrown
         */
        if(partNameTextField.getText().isEmpty() || 
                partInvTextField.getText().isEmpty() ||
                partPriceTextField.getText().isEmpty() ||
                partMaxTextField.getText().isEmpty() ||
                partMinTextField.getText().isEmpty() ||
                partSwappableTextField.getText().isEmpty()
                ){
            errorThrown = true;
            this.errorCode = 3;
            return;
        }
        
        /*
         *If inventory, max, or min field are less than zero an error is thrown
         */
        if(Integer.valueOf(partInvTextField.getText()) < 0 ||
                Integer.valueOf(partMaxTextField.getText()) < 0 ||
                Integer.valueOf(partMinTextField.getText()) < 0){
            errorThrown = true;
            this.errorCode = 4;
            return;
        }
        
        /*
         * If the value of our inventory is greater than max allowable throw an error
         */
        else if(Integer.valueOf(partInvTextField.getText()) > 
                Integer.valueOf(partMaxTextField.getText())){
            errorThrown = true;
            this.errorCode = 5;
            return;
        }
        
        /*
         * If the value of our inventory is less than min allowable throw an error
         */
        else if(Integer.valueOf(partInvTextField.getText()) < 
                Integer.valueOf(partMinTextField.getText())){
            errorThrown = true;
            this.errorCode = 6;
            return;
        }
        /*
         * If the value of our min field is greater than our max field
         */
        else if(Integer.valueOf(partMinTextField.getText()) > 
                Integer.valueOf(partMaxTextField.getText())){
            errorThrown = true;
            this.errorCode = 7;
            return;
        }
        /*
         *If both the Outsourced or InHouse radio button are not selected
         */
        if(!outsourcedRadioButton.isSelected() && !inHouseRadioButton.isSelected()){
            errorThrown = true;
            this.errorCode = 8;
            return;
        }
    }
    
    /**
     * This method takes in an error number and creates an alert based on if it
     * errorNumber matching an if statement
     * @param errorNumber 
     */
    public void showAlert(int errorNumber){
        //Initialize an alert instance
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("ERROR");
        error.setHeaderText("CANNOT SAVE");
        if(errorNumber == 0){
            return;
        }
        if(errorNumber == 1){
            error.setContentText("Machine ID must be a number!");
        }
        if(errorNumber == 2){
            error.setContentText("Company name must not include text, nut just numbers!");
        }
        if(errorNumber == 3){
            error.setContentText("All fields must have content!");
        }
        if(errorNumber == 4){
            error.setContentText("Number cannot be negative!");
        }
        if(errorNumber == 5){
            error.setContentText("Inventory cannot be greater than max field!");
        }
        if(errorNumber == 6){
            error.setContentText("Inventory cannot be less than min field!");
        }
        if(errorNumber == 7){
            error.setContentText("Min cannot be greater than max!");
        }
        if(errorNumber == 8){
            error.setContentText("InHouse or Outsource button must be selected!");
        }
        error.showAndWait();
        return;
    }
    /**
     * This method adds an InHouse part to our inventory
     */
    public void addInHousePart(){
        mainInventory.addPart(
                new InHouse(
                    Integer.valueOf(partIdTextField.getText()),
                    partNameTextField.getText(),
                    Double.valueOf(partPriceTextField.getText()),
                    Integer.valueOf(partInvTextField.getText()),
                    Integer.valueOf(partMinTextField.getText()),
                    Integer.valueOf(partMaxTextField.getText()),
                    Integer.valueOf(partSwappableTextField.getText())
        ));
    }
    
    /**
     * This method adds an Outsourced part to our inventory
     */
    public void addOutsourcedPart(){
        mainInventory.addPart(
                new Outsourced(
                    Integer.valueOf(partIdTextField.getText()),
                    partNameTextField.getText(),
                    Double.valueOf(partPriceTextField.getText()),
                    Integer.valueOf(partInvTextField.getText()),
                    Integer.valueOf(partMinTextField.getText()),
                    Integer.valueOf(partMaxTextField.getText()),
                    partSwappableTextField.getText()
        ));
    }
}
