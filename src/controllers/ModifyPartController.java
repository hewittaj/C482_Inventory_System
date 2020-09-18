package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
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
import models.Outsourced;
import models.Part;

/**
 * FXML Controller class
 *
 * @author alexhewitt
 */
public class ModifyPartController implements Initializable {

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
    Part partSelected;
    int errorCode = 0;
    boolean errorThrown = false;

    public ModifyPartController(Inventory inventory, Part partSelectedByUser){
        this.mainInventory = inventory;
        this.partSelected = partSelectedByUser;
    }
    //CREATE TWO METHODS FOR DETECTING WHEN SWITCHING FROM OUTSOURCED TO INHOUSE AND UPDATE THEIR TYPE
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize our values of the selected part        
        // If our partSelected is an outsourced part or inhouse, we do different things
        if(partSelected instanceof Outsourced){
            //We must create an casted Outsourced Part object otherwise we can't call getCompanyName()
            Outsourced outsourced = (Outsourced) this.partSelected;
            this.outsourcedRadioButton.setSelected(true);
            this.partIdTextField.setText(String.valueOf(outsourced.getId()));
            this.partNameTextField.setText(outsourced.getName());
            this.partInvTextField.setText(String.valueOf(outsourced.getStock()));
            this.partPriceTextField.setText(String.valueOf(outsourced.getPrice()));
            this.partMaxTextField.setText(String.valueOf(outsourced.getMax()));
            this.partMinTextField.setText(String.valueOf(outsourced.getMin()));
            this.partSwappableLabel.setText("Company Name");
            this.partSwappableTextField.setText(outsourced.getCompanyName());
                    
        }
        //If our part is an instance of InHouse...
        if(partSelected instanceof InHouse){
            InHouse inHouse = (InHouse) this.partSelected;
            this.inHouseRadioButton.setSelected(true);
            this.partIdTextField.setText(String.valueOf(inHouse.getId()));
            this.partNameTextField.setText(inHouse.getName());
            this.partInvTextField.setText(String.valueOf(inHouse.getStock()));
            this.partPriceTextField.setText(String.valueOf(inHouse.getPrice()));
            this.partMaxTextField.setText(String.valueOf(inHouse.getMax()));
            this.partMinTextField.setText(String.valueOf(inHouse.getMin()));
            this.partSwappableLabel.setText("Machine ID");
            this.partSwappableTextField.setText(String.valueOf(inHouse.getMachineId()));
        }
        
    }
    
    /**
     * This will deselect the outsourced radio button when the in house is clicked
     * @param event 
     */
    @FXML
    public void inHouseRadioButtonSelected(ActionEvent event){
        outsourcedRadioButton.setSelected(false);
        partSwappableLabel.setText("Machine ID");
    }
    
    /**
     * This will deselect the in house radio button when the outsourced button is 
     * clicked
     * @param event 
     */
    @FXML
    public void outsourcedRadioButtonSelected(ActionEvent event){
        inHouseRadioButton.setSelected(false);
        partSwappableLabel.setText("Company Name");
    }
    
    /**
     * This method takes us back to the main screen if the cancel button is pushed.
     * TO DO CONFIRM USER WANTS TO CANCEL look at https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.AlertType.html
     * @param event 
     */
    @FXML
    public void cancelButtonPushed(ActionEvent event){
        try{
            boolean wantsToCancel;
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
     * This method takes the updated information that has been modified and passes
     * it to the main screen/updates it.
     * @param event 
     */
    @FXML
    public void saveButtonPushed(ActionEvent event){
        try{
            // If the inhouse radio button is selected and the part is currently an outsourced part
            // Will check for errors first and if an error is thrown it will show the alert
            this.errorCode = 0; //reset to zero for no error thrown
            checkForErrors();
            showAlert(errorCode);
            if(errorCode == 0){
                if(inHouseRadioButton.isSelected() && (partSelected instanceof Outsourced)){
                    updateToInHouse();
                }

                // If the inhouse radio button is selected and part is currently inhouse part
                // Will check for errors first and if an error is thrown it will show the alert
                if(inHouseRadioButton.isSelected() && (partSelected instanceof InHouse)){
                    updateToInHouse();
                }

                // If the outsourced radio button is selected and the part is currently an inhouse part
                // Will check for errors first and if an error is thrown it will show the alert
                if(outsourcedRadioButton.isSelected() && (partSelected instanceof InHouse)){
                    updateToOutsourced();
                }

                // If the outsourced radio button is selected and the part is currently outsourced
                // Will check for errors first and if an error is thrown it will show the alert
                if(outsourcedRadioButton.isSelected() && (partSelected instanceof Outsourced)){
                    updateToOutsourced();
                }

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
     * Update the part to an InHouse Part or reinitialize part as InHouse
     */
    public void updateToInHouse(){
        mainInventory.updatePart(
                partSelected.getId()-1, 
                new InHouse(Integer.valueOf(
                partIdTextField.getText()),
                partNameTextField.getText(),
                Double.valueOf(partPriceTextField.getText()),
                Integer.valueOf(partInvTextField.getText()),
                Integer.valueOf(partMinTextField.getText()),
                Integer.valueOf(partMaxTextField.getText()),
                Integer.valueOf(partSwappableTextField.getText()))
        );
    }
    
    /**
     * Update the part to an Outsourced Part or reinitialize part as Outsourced
     */
    public void updateToOutsourced(){
        mainInventory.updatePart(
                partSelected.getId()-1, 
                new Outsourced(
                Integer.valueOf(partIdTextField.getText()),
                partNameTextField.getText(),
                Double.valueOf(partPriceTextField.getText()),
                Integer.valueOf(partInvTextField.getText()),
                Integer.valueOf(partMinTextField.getText()),
                Integer.valueOf(partMaxTextField.getText()),
                partSwappableTextField.getText())
        );
    }
    
    /**
     * This method takes in an errorNumber and outputs a certain alert based on the number
     * @param errorNumber 
     */
    public void checkForErrors(){
        
        /*
        If the inhouse radio button is selected and the textfield contains letters
        we throw an error as it can only contain numbers
        */ 
        if(inHouseRadioButton.isSelected() && partSwappableTextField.getText().matches("[a-zA-Z]+")){
            errorThrown = true;
            this.errorCode = 1;
            //errorThrown = true;
        }
        /*
        If the outsourced radio button is selected and the textfield contains numbers
        we throw an error as it can only contain letters
        */ 
        if(outsourcedRadioButton.isSelected() && partSwappableTextField.getText().matches("^[0-9]*$")){
            errorThrown = true;
            this.errorCode = 2;
            //errorThrown = true;
        }
        
        /*
        If the content of any text field is empty an error is thrown
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
        }
        
        /*
        If inventory, max, or min field are less than zero an error is thrown
        */
        if(Integer.valueOf(partInvTextField.getText()) < 0 ||
                Integer.valueOf(partMaxTextField.getText()) < 0 ||
                Integer.valueOf(partMinTextField.getText()) < 0){
            errorThrown = true;
            this.errorCode = 4;
            return;
        }
        
        /**
         * If the value of our inventory is greater than max allowable throw an error
         */
        else if(Integer.valueOf(partInvTextField.getText()) > 
                Integer.valueOf(partMaxTextField.getText())){
            errorThrown = true;
            this.errorCode = 5;
            return;
        }
        
        /**
         * If the value of our inventory is less than min allowable throw an error
         */
        else if(Integer.valueOf(partInvTextField.getText()) < 
                Integer.valueOf(partMinTextField.getText())){
            errorThrown = true;
            this.errorCode = 6;
            return;
        }
        
        else if(Integer.valueOf(partMinTextField.getText()) > 
                Integer.valueOf(partMaxTextField.getText())){
            errorThrown = true;
            this.errorCode = 7;
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
            error.setContentText("Company name must not include text, nut just numbers");
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
        error.showAndWait();
        return;
    }
}
