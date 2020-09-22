package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

/**
 * TO DO
 * @author alexhewitt
 */
public class Product {
    
    private ObservableList<Part> associatedParts; // TO DO
    private int id;// TO DO
    private String name;// TO DO
    private double price;// TO DO
    private int stock;// TO DO
    private int min;// TO DO
    private int max; // TO DO
    
    /**
     * 
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max 
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
     * TO DO
     * @param id 
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * TO DO
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * TO DO
     * @param price 
     */
    public void setPrice(double price) {
        this.price = price;
    }
    
    /**
     * TODO
     * @param stock 
     */
    public void setStock(int stock) {
        this.stock = stock;
    }
    
    /**
     * TODO
     * @param min 
     */
    public void setMin(int min) {
        this.min = min;
    }
    
    /**
     * TO DO
     * @param max 
     */
    public void setMax(int max) {
        this.max = max;
    }
    
    /**
     * TO DO
     * @return 
     */
    public int getId() {
        return id;
    }
    
    /**
     * TO DO
     * @return 
     */
    public String getName() {
        return name;
    }
    
    /**
     * TO DO
     * @return 
     */
    public double getPrice() {
        return price;
    }
    
    /**
     * TO DO
     * @return 
     */
    public int getStock() {
        return stock;
    }

    /**
     * TO DO
     * @return 
     */
    public int getMin() {
        return min;
    }

    /**
     * TO DO
     * @return 
     */
    public int getMax() {
        return max;
    }
    
    /**
     * TO DO 
     * @param part 
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }
    
    /**
     * TO DO
     * @param selectedAssociatedPart
     * @return 
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        //probably need an if statement to say if product deleted properly
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }
    
    /**
     * TO DO
     * @return 
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
}
