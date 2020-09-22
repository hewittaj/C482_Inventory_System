package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * TO DO
 * @author alexhewitt
 */
public class Inventory {
    
    //Initialize our Observable Lists
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
    /**
     * TO DO
     * @param newPart 
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }
    
    /**
     * TO DO
     * @param newProduct 
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }
    
    /**
     * TO DO
     * @param partId
     * @return 
     */
    public static Part lookupPart(int partId){
        for(int i = 0; i < allParts.size(); i++){
            if(allParts.get(i).getId() == partId){
                return allParts.get(i);
            }
        }
        return null;
    }
    
    /**
     * TO DO
     * @param productId
     * @return 
     */
    public static Product lookupProduct(int productId){
        for(int i = 0; i < allProducts.size(); i++){
            if(allProducts.get(i).getId() == productId){
                return allProducts.get(i);
            }
        }
        return null;
    }
    
    /**
     * TO DO
     * @param partName
     * @return 
     */
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> returnedParts = FXCollections.observableArrayList();
        for(Part part: allParts){
            if(part.getName().equals(partName)){
                returnedParts.add(part); 
            }
        }
        
        return returnedParts;
    }
    
    /**
     * TO DO
     * @param productName
     * @return 
     */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> returnedProducts = FXCollections.observableArrayList();
        for(Product product: allProducts){
            if(product.getName().equals(product)){
                returnedProducts.add(product); 
            }
        }
        return returnedProducts;
    }
    
    /**
     * TO DO
     * @param index
     * @param selectedPart 
     */
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }
    
    /**
     * TO DO
     * @param index
     * @param newProduct 
     */
    public static void updateProduct(int index, Product newProduct){
        allProducts.set(index, newProduct);
    }
    
    /**
     * TO DO
     * @param selectedPart
     * @return 
     */
    public static boolean deletePart(Part selectedPart){
        allParts.remove(selectedPart);
        //probably need an if statement to determine if deleted properly
        return true;
    }
    
    /**
     * TO DO
     * @param selectedProduct
     * @return 
     */
    public static boolean deleteProduct(Product selectedProduct){
        //probably need an if statement to determine if deleted properly
        allProducts.remove(selectedProduct);
        return true;
    }
    
    /**
     * TO DO
     * @return 
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    
    /**
     * TO DO
     * @return 
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
