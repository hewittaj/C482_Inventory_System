package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 * This is the class that helps to construct an inventory for our Inventory
 * System
 * @author alexhewitt
 */
public class Inventory {
    
    // Initialize our Observable Lists
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
    /**
     * This method adds a part to our inventory
     * @param newPart Part that we would like to add to the list
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }
    
    /**
     * This method adds a product to our inventory
     * @param newProduct Product that we would like to add to the list
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }
    
    /**
     * This method looks up a part in our list of parts
     * @param partId Id that is passed to method to find specific part that matches
     * @return Returns either the part with designated id, or null if none found
     */
    public static Part lookupPart(int partId){
        for(int i = 0; i < allParts.size(); i++){
            if(allParts.get(i).getId() == partId){
                return allParts.get(i);
            }
        }
        Alert notFound = new Alert(Alert.AlertType.ERROR);
        notFound.setTitle("ERROR!");
        notFound.setHeaderText("Not found!");
        notFound.setContentText("No part found!");
        notFound.showAndWait();
        return null;
    }
    
    /**
     * This method looks up a product in our list of products
     * @param productId Id that is passed to the method to find a specific product that it matches
     * @return Returns either the product with designated id, or null if none found
     */
    public static Product lookupProduct(int productId){
        for(int i = 0; i < allProducts.size(); i++){
            if(allProducts.get(i).getId() == productId){
                return allProducts.get(i);
            }
        }
        Alert notFound = new Alert(Alert.AlertType.ERROR);
        notFound.setTitle("ERROR!");
        notFound.setHeaderText("Not found!");
        notFound.setContentText("No product found!");
        notFound.showAndWait();
        return null;
    }
    
    /**
     * Looks up a part based on the string that it is passed and returns a list
     * @param partName String of the part name that we want to lookup
     * @return Returns the list of parts that match the string
     */
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> returnedParts = FXCollections.observableArrayList();
        
        for(Part part: allParts){
            if(part.getName().contains(partName)){
                returnedParts.add(part); 
            }
        }
        
        if(returnedParts.isEmpty()){
            Alert notFound = new Alert(Alert.AlertType.ERROR);
            notFound.setTitle("ERROR!");
            notFound.setHeaderText("Not found!");
            notFound.setContentText("No part found!");
            notFound.showAndWait();
            return null;
        }
        return returnedParts;
    }
    
    /**
     * Looks up a product based on the string that it is passed and returns a list
     * @param productName String of the product name that we want to lookup
     * @return Returns the list of parts that match the string
     */
    public static ObservableList<Product> lookupProduct(String productName){
        if(allProducts.isEmpty()){
            return null;
        }
        
        ObservableList<Product> returnedProducts = FXCollections.observableArrayList();
        for(Product product: allProducts){
            if(product.getName().contains(productName)){
                returnedProducts.add(product); 
            }
        }
        if(returnedProducts.isEmpty()){
            Alert notFound = new Alert(Alert.AlertType.ERROR);
            notFound.setTitle("ERROR!");
            notFound.setHeaderText("Not found!");
            notFound.setContentText("No product found!");
            notFound.showAndWait();
        }
        return returnedProducts;
    }
    
    /**
     * This method updates the part at the index specified with the part it is passed
     * @param index Index of the part we want to update
     * @param selectedPart Part that was selected that we want to update
     */
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }
    
    /**
     * This method updates the product at the index specified with the part it is passed
     * @param index Index of the product we want to update
     * @param newProduct Product that we pass to be updated
     */
    public static void updateProduct(int index, Product newProduct){
        allProducts.set(index, newProduct);
    }
    
    /**
     * This method deletes a part from the list of parts
     * @param selectedPart Part that we want to delete
     * @return Boolean returned, based on UML we were given
     */
    public static boolean deletePart(Part selectedPart){
        allParts.remove(selectedPart);
        return true;
    }
    
    /**
     * This method deletes a product from the list of products
     * @param selectedProduct Product that we want to delete
     * @return Boolean returned, based on UML we were given
     */
    public static boolean deleteProduct(Product selectedProduct){
        //probably need an if statement to determine if deleted properly
        allProducts.remove(selectedProduct);
        return true;
    }
    
    /**
     * This method returns a list of all parts in our inventory
     * @return List of all the parts
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    
    /**
     * This method returns a list of all products in inventory
     * @return List of all the products
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
    
    /**
     * Gets the last parts Id number for adding a part functionality
     * @return The part ID for the last part in the list
     */
    public static int getLastPartId(){
        int maxId = 0;
        for(Part part: allParts){
            if(part.getId() > maxId){
                maxId = part.getId();
            }
        }
        return maxId;
    }
    
    /**
     * Gets the last product Id number for adding a part functionality
     * @return The product ID for the last product in the list
     */
    public static int getLastProductId(){
        int maxId = 0;
        for(Product product: allProducts){
            if(product.getId() > maxId){
                maxId = product.getId();
            }
        }
        return maxId;
    }
}
