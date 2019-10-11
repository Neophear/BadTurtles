package controllerlayer;
import modellayer.*;
import java.util.*;

/**
 * Write a description of class SalesProductController here.
 *
 * @author (Mathias)
 * @version (25-05-2018)
 */
public class SalesProductController
{
    private SalesProductContainer salesProductContainer;

    /**
     * Constructor for objects of class SalesProductController
     */
    public SalesProductController()
    {
        salesProductContainer = SalesProductContainer.getInstance();
    }   

    /**
     * @param name              The name needed for the product
     * @param location          The current location of the product
     * @param countInStock      The current amount in stock
     * @param mininmumStock     The mininmum that should be in stock
     * @param maximumStock      the maximum amount that can be in stock
     * @param reorderAmount     At what amount a reorder of the product should happen
     * @return                  Returns the created SalesProduct
     * Creates a new SalesProduct by sending infomation to the modellayer
     */
    public SalesProduct createSalesProduct (String name , String location , int countInStock, int mininmumStock, int maximumStock, int reorderAmount) throws maxMinStockDoesNotMatchException
    {
        if(maximumStock > mininmumStock)
        {
            SalesProduct newSalesProduct = new SalesProduct(name, location, countInStock, mininmumStock, maximumStock, reorderAmount);
            salesProductContainer.add(newSalesProduct);
            return newSalesProduct;
        }
        else
            throw new maxMinStockDoesNotMatchException();
    }
    
    /**
     * @param salesProductId     The ID needed to find a SalesProduct
     * @return                   Returns a SalesProduct
     * Finds a SalesProduct by going into the SalesProductController and using the findSalesProduct method
     */
    public SalesProduct findSalesProduct(int salesProductId)
    {
        return salesProductContainer.findSalesProduct(salesProductId);
    }

    /**
     * @param salesProduct      The SalesProduct that needs to be updated
     * @param name              The name needed for the product
     * @param location          The current location of the product
     * @param countInStock      The current amount in stock
     * @param mininmumStock     The mininmum that should be in stock
     * @param maximumStock      the maximum amount that can be in stock
     * @param reorderAmount     At what amount a reorder of the product should happen
     * updateSalesProduct updates the SalesProduct
     */
    public void updateSalesProduct(SalesProduct salesProduct, String name , String location , int countInStock, int mininmumStock, int maximumStock, int reorderAmount, SalesProduct partOf)
    {
        salesProduct.updateAll(name, location, countInStock, mininmumStock, maximumStock, reorderAmount, partOf);
    }
    
    /**
     * @param loggedInEmployee     The Employee that is logged in.
     * @param salesProduct          The SalesProduct that is parse through.
     * @param timeFrom              The date that the price starts
     * @param price                 The price the products needs
     * addSalesProductPrice adds a price to a SalesProduct
     */
    public void addSalesProductPrice(Employee loggedInEmployee, SalesProduct salesProduct, Date timeFrom, double price) throws IsNotAuthorizedException
    {
        if(loggedInEmployee.getRole() == EmployeeRole.MANAGEMENT || loggedInEmployee.getRole() == EmployeeRole.BOSS)
            salesProduct.addPrice(timeFrom, price);
        else
            throw new IsNotAuthorizedException();
    }
    
    /**
     * @param salesProduct          The salesproduct that needs a discount added
     * @param timeFrom              The date that the discount starts
     * @param timeTo                The date that the discount ends
     * @param percentage            The discount percentage that the product will be given
     * addDiscount adds a Discount to the given SalesProduct
     */
    public void addDiscount(SalesProduct salesProduct, Date timeFrom, Date timeTo, double percentage) throws SalesProduct.datesOverlapException
    {
        salesProduct.addDiscount(timeFrom, timeTo, percentage);
    }
    
    /**
     * @param salesProduct          The SalesProduct that is parse through.
     * @return                      Returns a LinkedList with all discounts on a SalesProduct
     * finds all discounts on a given product
     */
    public LinkedList<Discount> findAllDiscounts(SalesProduct salesProduct)
    {
        return salesProduct.getAllDiscounts();
    }
    
    /**
     * @param salesProduct          The SalesProduct that is parse through.
     * @param date                  The date looking up if there is a discount
     * @return                      Returns a discount
     * Finds a discount by the date given
     */
    public Discount findDiscount(SalesProduct salesProduct, Date date)
    {
        return salesProduct.getDiscount(date);
    }

    /**
     * @param salesProduct          The SalesProduct that is parse through.
     * @return                      Returns a LinkedList with SalesProductsPrices
     * Returns an array with all SalesProductPrices on a given product
     */
    public LinkedList<SalesProductPrice> findAllSalesProductPrices(SalesProduct salesProduct)
    {
        return salesProduct.getAllSalesProductPrices();
    }
    
    /**
     * @param salesProduct          The SalesProduct that is parse through.
     * @param date                  The date where an active price should be found
     * @return                      Returns a double with a price
     * findSalesProductPrice returns the price of the salesProduct
     */
    public double findSalesProductPrice(SalesProduct salesProduct, Date date)
    {
        return salesProduct.getPrice(date);
    }

    /**
     * @return                      Returns an arraylist with all products
     * returns an arraylist with all products
     */
    public ArrayList<SalesProduct> getAll()
    {
        return salesProductContainer.getList();
    }

    /**
     *Exeptions
     */
    public class IsNotAuthorizedException extends Exception
    {
        public IsNotAuthorizedException()
        {
            super("Ingen adgang! Kontakt en leder for hjælp");
        }
    }

    public class maxMinStockDoesNotMatchException extends Exception
    {
        public maxMinStockDoesNotMatchException()
        {
            super("MininmumStock skal være mindre end maxinmumStock");
        }
    }
}