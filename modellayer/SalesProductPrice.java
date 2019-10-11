package modellayer;
import java.util.*;

/**
 * Write a description of class SalesProductPrice here.
 *
 * @author (Mathias)
 * @version (25-05-2018)
 */
public class SalesProductPrice
{
    private Date timeFrom;
    private double price;

    /**
     * Constructor for objects of class SalesProductPrice
     */
    public SalesProductPrice(Date timeFrom, double price)
    {
        this.timeFrom = timeFrom;
        this.price = price;
    }
    
    /**
    * Setters
    */
    public void setTimeFrom(Date timeFrom) { this.timeFrom = timeFrom; }

    public void setPrice(double price) { this.price = price; }

    /**
    * Getters
    */
    public Date getDate() { return timeFrom; }

    public double getPrice() { return price; }
}