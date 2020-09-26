package models;

/**
 * This class represents an Outsourced Part in our inventory
 * @author alexhewitt
 */
public class Outsourced extends Part{
    
    private String companyName; // Name of company that we are outsourcing from
    
    /**
     * This method sets up our constructor for an Outsourced Part
     * @param id Id of the part
     * @param name Name of the part
     * @param price Price of the part
     * @param stock Inventory level of the part
     * @param min Minimum allowable parts
     * @param max Maximum allowable parts
     * @param companyName Name of company who we outsourced the part from
     */
    public Outsourced(int id, String name, double price, int stock,
            int min, int max, String companyName){
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
    
    /**
     * This method sets the company name of the outsourced part
     * @param companyName Name of the company that we want to set for the part
     */
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }
    
    /**
     * This method gets the company name of the outsourced part
     * @return Name of the company that is being outsourced from
     */
    public String getCompanyName(){
        return this.companyName;
    }
}