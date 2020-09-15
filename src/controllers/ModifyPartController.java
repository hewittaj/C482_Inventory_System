package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import models.Inventory;
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

    public ModifyPartController(Inventory inventory, Part partSelected){
        this.mainInventory = inventory;
        this.partSelected = partSelected;
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.partIdTextField = new TextField();
    }    
    
    public void getSelectedPart(Part part){
        this.partIdTextField.setText(String.valueOf(part.getId()));
        
    }
    
}
