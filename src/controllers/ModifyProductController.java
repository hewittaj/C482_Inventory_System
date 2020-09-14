package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author alexhewitt
 */
public class ModifyProductController implements Initializable {

    //FXML instantiation of id's
    @FXML private TextField productIdTextField;
    @FXML private TextField productNameTextField;
    @FXML private TextField productInvTextField;
    @FXML private TextField productPriceTextField;
    @FXML private TextField productMaxTextField;
    @FXML private TextField productMinTextField;
    @FXML private TextField productSearchTextField;
    @FXML private TableColumn<?, ?> productIdTopColumn;
    @FXML private TableColumn<?, ?> productNameTopColumn;
    @FXML private TableColumn<?, ?> productInventoryTopColumn;
    @FXML private TableColumn<?, ?> productPriceTopColumn;
    @FXML private Button productAddButton;
    @FXML private TableColumn<?, ?> productIdBottomColumn;
    @FXML private TableColumn<?, ?> productNameBottomColumn;
    @FXML private TableColumn<?, ?> productInventoryBottomColumn;
    @FXML private TableColumn<?, ?> productPriceBottomColumn;
    @FXML private Button productRemoveButton;
    @FXML private Button productSaveButton;
    @FXML private Button productCancelButton;
    @FXML private TableView<?> modifyProductTopTableView;
    @FXML private TableView<?> modifyProductBottomTableView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
