package models;

/**
 *
 * @author alexhewitt
 */
public class Outsourced extends Part{
    
    private String companyName; // Name of company that we are outsourcing from
    
    /**
     * 
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName 
     */
    public Outsourced(int id, String name, double price, int stock,
            int min, int max, String companyName){
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
    
    /**
     * 
     * @param companyName User passes company name that they are changing to
     */
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }
    
    /**
     * 
     * @return Returns the company name of the outsourced part
     */
    public String getCompanyName(){
        return this.companyName;
    }
}
