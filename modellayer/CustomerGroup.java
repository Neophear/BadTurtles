package modellayer;
import java.util.ArrayList;

/**
 * Lav en beskrivelse af klassen CustomerGroup her.
 * 
 * @author (Mikkel)
 * @version (24-05-2018)
 */
public class CustomerGroup
{
    private String name;
    private double discount;
    /**
     * Konstruktør for objekter af klassen CustomerGroup
     */
    public CustomerGroup(String name, double discount) throws DiscountDoesntMatchException
    {
        this.name = name;
        setDiscount(discount);
    }
    /**
     * Set and get methods for setting customerGroup names and discounts
     * and getting them returned
     */
    public void setName(String newName) { name = newName; }
    
    public void setDiscount(double newDiscount) throws DiscountDoesntMatchException
    {
        if(newDiscount < 0 || newDiscount > 20)
            throw new DiscountDoesntMatchException();
        else
            discount = newDiscount;
    }
    
    
    public String getName() { return name; }
    public double getDiscount() { return discount; }
    
    public String toString()
    {
        return name + " (" + discount + "%)";
    }
    public class DiscountDoesntMatchException extends Exception
    {
        public DiscountDoesntMatchException()
        {
            super("Discount skal være mellem 0 og 20");
        }
    }
}
