package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class is used to represent a product in our inventory
 * @author alexhewitt
 */
public class Product {
    
    private ObservableList<Part> associatedParts;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    
    /**
     * This method sets up the constructor for our Product
     * @param id Id of the product
     * @param name Name of the product
     * @param price Price of the product
     * @param stock Inventory level of the product
     * @param min Minimum allowable of the product
     * @param max Maximum allowable of the product
     */
    public Product(int id, String name, double price, int stock, int min,
            int max){
        this.associatedParts = FXCollections.observableArrayList();
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    /**
     * This method sets up the constructor for our Product if no inventory level is provided
     * @param id Id of the product
     * @param name Name of the product
     * @param price Price of the product
     * @param min Minimum allowable of the product
     * @param max Maximum allowable of the product
     */
    public Product(int id, String name, double price, int min, int max){
        this.associatedParts = FXCollections.observableArrayList();
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = 0;
        this.min = min;
        this.max = max;
    }
    
    /**
     * This method sets the Id of the Product
     * @param id Id we want to set for the product
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * This method sets the name of the product
     * @param name Name we want to set for the product
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * This method sets the price of the product
     * @param price Price we want to set for the product
     */
    public void setPrice(double price) {
        this.price = price;
    }
    
    /**
     * This method sets the inventory level of the product
     * @param stock Inventory level we want to set for the product
     */
    public void setStock(int stock) {
        this.stock = stock;
    }
    
    /**
     * This method sets the minimum allowable of the product
     * @param min Minimum level we want to set for the product
     */
    public void setMin(int min) {
        this.min = min;
    }
    
    /**
     * This method sets the maximum allowable of the product
     * @param max Maximum level we want to set for the product
     */
    public void setMax(int max) {
        this.max = max;
    }
    
    /**
     * This method gets the Id of the product
     * @return Id of product
     */
    public int getId() {
        return id;
    }
    
    /**
     * This method gets the Name of the product
     * @return Name of product
     */
    public String getName() {
        return name;
    }
    
    /**
     * This method gets the Price of the product
     * @return Price of product
     */
    public double getPrice() {
        return price;
    }
    
    /**
     * This method gets the Inventory level of the product
     * @return Inventory level of product
     */
    public int getStock() {
        return stock;
    }

    /**
     * This method gets the Minimum allowable of the product
     * @return Minimum allowable of the product
     */
    public int getMin() {
        return min;
    }

    /**
     * This method gets the Maximum allowable of the product
     * @return Maximum allowable of the product
     */
    public int getMax() {
        return max;
    }
    
    /**
     * This method adds an associated part to the product
     * @param part Part we want to associate with the product
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }
    
    /**
     * This method deletes an associated part of the product
     * @param selectedAssociatedPart Selected part that we want to remove association with
     * @return Boolean returned based of UML
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        //probably need an if statement to say if product deleted properly
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }
    
    /**
     * This method gets all the associated parts of the product
     * @return List returned of associated parts of product
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
}