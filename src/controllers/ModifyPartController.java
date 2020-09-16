package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
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
    
}
