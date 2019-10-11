package modellayer;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Write a description of class SalesProductContainer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class SalesProductContainer extends BaseContainer<SalesProduct>
{
    private static SalesProductContainer instance;

    public SalesProductContainer()
    {

    }

    /*
    * getInstance gets the instance of our current SalesProductContainer else it creastes a new instance
    */
    public static SalesProductContainer getInstance()
    {
        if(instance == null)
            instance = new SalesProductContainer();
        return instance;
    }

    /*
    * findSalesProduct find a sales product in the ArrayList by using the salesProductId 
    */
    public SalesProduct findSalesProduct(int salesProductId)
    {
       boolean found = false; 
       SalesProduct salesProduct = null;
       SalesProduct temp = null;
       Iterator<SalesProduct> it = getList().iterator();
       
       while(!found && it.hasNext())
       {
            temp = it.next();
            if(temp.getSalesProductId() == salesProductId)
            {
                found = true;
                salesProduct = temp;
            }
       }
       
       return found ? salesProduct : null;
    }
}