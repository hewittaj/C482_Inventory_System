package models;

/**
 * This class represents an InHouse part in our inventory
 * @author alexhewitt
 */
public class InHouse extends Part{
    
    private int machineId;
    /**
     * This is the constructor for an InHouse Part with the following variables
     * @param id ID of the part number
     * @param name Name of the part
     * @param price Price of the part
     * @param stock Inventory level of the part
     * @param min Minimum allowable parts
     * @param max Maximum allowable parts
     * @param machineID Machine ID for part
     */
    public InHouse(int id, String name, double price, int stock, int min,
            int max, int machineID){
        super(id, name, price, stock, min, max);
        
        this.machineId = machineID;
    }
    
    /**
     * This method sets the machine Id passed from the constructor 
     * @param machineId The new machineId we want to change to for the part
     */
    public void setMachineID(int machineId){
        this.machineId = machineId;
    }
    
    /**
     * Returns the machine Id of the part
     * @return returns the machineId of the part we are working with.
     */
    public int getMachineId(){
        return this.machineId;
    }   
}
