package models;

/**
 * Logic for the InHouse Part Screen
 * @author alexhewitt
 */
public class InHouse extends Part{
    private int machineId; // 
    /**
     * //TO DO 
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineID 
     */
    public InHouse(int id, String name, double price, int stock, int min,
            int max, int machineID){
        super(id, name, price, stock, min, max);
        
        this.machineId = machineID; //TO DO
    }
    
    /**
     * TO DO
     * @param machineId The new machineId we want to change to for the part
     */
    public void setMachineID(int machineId){
        this.machineId = machineId;// TO DO
    }
    
    /**
     * TO DO
     * @return returns the machineId of the part we are working with.
     */
    public int getMachineId(){
        return this.machineId; //TO DO
    }
    
}
