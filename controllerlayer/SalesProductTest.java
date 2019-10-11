package controllerlayer;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.*;
import modellayer.*;

/**
 * The test class SalesProductTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class SalesProductTest
{
    private controllerlayer.SalesProductController salesPro1;

    /**
     * Default constructor for test class SalesProductTest
     */
    public SalesProductTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        salesPro1 = new controllerlayer.SalesProductController();
        try{
            salesPro1.createSalesProduct("Træ", "Lager 2", 20, 4, 35, 9);
        }
        catch(SalesProductController.maxMinStockDoesNotMatchException e){
            System.err.println(e.getMessage());

        }

    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }

    /*
     * testCreateSalesProduct tests if we can create a salesProduct
     */

    @Test
    public void testCreateSalesProduct(){
        try{
            salesPro1.createSalesProduct("sten", "Lager 1", 35, 6, 99, 11);
        }
        catch(SalesProductController.maxMinStockDoesNotMatchException e){
            System.err.println(e.getMessage());

        }


    }

    /*
     * testFindSalesProduct test if we can find a sales product
     */
    @Test
    public void testFindSalesProduct(){

        assertNotNull(salesPro1.findSalesProduct(2));

    }

    /*
     * testThatSalesProductDoesNotExist test that the number 99 is not created
     */

    @Test
    public void testThatSalesProductDoesNotExist(){

        assertNull(salesPro1.findSalesProduct(99));

    }

    /*
     * testAddDiscount test if we can add a discount to the salesProduct
     */

    @Test
    public void testAddDiscount(){
        boolean thrown = false;
        SalesProduct salesProduct = salesPro1.findSalesProduct(1);
        try{
            salesPro1.addDiscount(salesProduct, new Date(2018, 05, 10), new Date(2019, 06, 15), 10);
        }
        catch(SalesProduct.datesOverlapException e){

            thrown = true;

        }
        assertFalse(thrown);
    }

    /*
     * testOverlappingDiscount test if dates are overlapping by adding the same date twice
     */

    @Test
    public void testOverlappingDiscount(){
        boolean thrown = false;
        SalesProduct salesProduct = salesPro1.findSalesProduct(1);

        try
        {   
            salesPro1.addDiscount(salesProduct, new Date(2018, 05, 10), new Date(2018, 10, 10), 10);
            salesPro1.addDiscount(salesProduct, new Date(2018, 06, 10), new Date(2018, 06, 15), 10);
        }
        catch(SalesProduct.datesOverlapException e){

            thrown = true;

        }

        assertTrue(thrown);

    }

    /*
     * testAddSalesProductPrice test if we can add a price to a SalesProduct
     */

    @Test
    public void testAddSalesProductPrice(){
        boolean thrown = false;

        Employee loggedInEmployee = new Employee("Dyne", "Larsen", "Jyskvej 123", 8600, "Silkeborg", "42310631", "Dynelarsen123@hotmail.dk", true, modellayer.EmployeeRole.BOSS, new Date(2018, 05, 18));

        SalesProduct salesProduct = salesPro1.findSalesProduct(1);
        try{
            salesPro1.addSalesProductPrice(loggedInEmployee, salesProduct,new Date(2018, 05, 10), 10);
            salesPro1.addSalesProductPrice(loggedInEmployee, salesProduct,new Date(1996, 05, 10), 20);
        }
        catch(SalesProductController.IsNotAuthorizedException e){
            System.err.println(e.getMessage());
            thrown = true;
            assertTrue(thrown);
        }

        assertFalse(thrown);

    }

    /*
     * testUpdateSalesProduct tests if we can update a sales product
     */

    @Test
    public void testUpdateSalesProduct(){

        SalesProduct salesProduct = salesPro1.findSalesProduct(1);

        salesPro1.updateSalesProduct(salesProduct, "Maple", "lager 1", 99, 98, 99, 99, null);

        assertEquals("Maple", salesProduct.getName());
        assertEquals("lager 1", salesProduct.getLocation());
        assertEquals(99, salesProduct.getCountInStock());
        assertEquals(98, salesProduct.getMininmumStock());
        assertEquals(99, salesProduct.getMaximumStock());
        assertEquals(99, salesProduct.getReorderAmount());

    }
    /*
     * testFindPrice test if we can find a price on a SalesProduct
     */

    @Test
    public void testFindPrice(){

        SalesProduct salesProduct = salesPro1.findSalesProduct(1);
        
        assertEquals(10, salesPro1.findSalesProductPrice(salesProduct, Calendar.getInstance().getTime()), 00.1);

    }
    
    
    @Test
    public void testFindPriceDateBefore(){

        SalesProduct salesProduct = salesPro1.findSalesProduct(1);
        double price = salesPro1.findSalesProductPrice(salesProduct, new Date(2018, 05, 9));
        assertEquals(10, price, 00.1);

    }

    /*
     * testFindDiscount test if we can find a discount on a salesProduct
     */

    @Test
    public void testFindDiscount(){

        SalesProduct salesProduct = salesPro1.findSalesProduct(1);
        Discount discount = salesPro1.findDiscount(salesProduct, new Date(2018, 05, 16));
        assertEquals(10, discount.getPercentage(), 00.1);

    }

    
    

}
