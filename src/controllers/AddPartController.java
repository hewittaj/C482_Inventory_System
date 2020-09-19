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
import models.Inventory;
import static models.Inventory.lookupPart;
import models.Part;

/**
 * FXML Controller class
 *
 * @author alexhewitt
 */
public class AddPartController implements Initializable {
    
    //Initialize the FXML portions of the add part screen
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
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
     * Method that is ran when the save button is pushed
     * @param event Event that is caught to detect save button push
     */
    public void saveButtonPushed(ActionEvent event){
        
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
            partIdTextField.setText("1");
        }
        else{
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
}
