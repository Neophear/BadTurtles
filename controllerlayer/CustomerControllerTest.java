package controllerlayer;
import modellayer.*;


import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Testklassen CustomerControllerTest.
 *
 * @author (dit navn her)
 * @version (versionsnummer eller dato her)
 */
public class CustomerControllerTest
{
    private controllerlayer.CustomerController customerCtrl;
    private controllerlayer.CustomerGroupController customerGroupCtrl;
    
    /**
     * Standardkonstruktør for testklassen CustomerControllerTest
     */
    public CustomerControllerTest()
    {
        boolean thrown = false;
        try
        {
            customerCtrl = new controllerlayer.CustomerController();
            customerGroupCtrl = new controllerlayer.CustomerGroupController();
            customerGroupCtrl.createCustomerGroup("Pejse", 10);
        }
        catch(CustomerGroupController.CustomerGroupNameAlreadyExistsException e)
        {
            System.err.println(e.getMessage());
        }
        catch(CustomerGroup.DiscountDoesntMatchException e)
        {
            thrown = true;
        }
    }

    /**
     * Opsæt test-fixtures.
     *
     * Kaldt før hver testmetode.
     */
    @Before
    public void setUp()
    {
    }

    /**
     * River test-fixture ned.
     *
     * Kaldt efter hver testmetode.
     */
    @After
    public void tearDown()
    {
    }
    
    @Test
    public void testCreateCustomer()
    {   
        boolean thrown = false;
        Customer newCustomer;
        try
        {
            newCustomer = customerCtrl.createCustomer("Mikkel", "Rasmussen", "Stjernepladsen", 9000, "Aalborg",
                "+4528971744", "Mikkel4a@hotmail.com", customerGroupCtrl.createCustomerGroup("Pejse", 10));
            assertEquals("Mikkel", newCustomer.getFirstName());
            assertEquals("Rasmussen", newCustomer.getLastName());
            assertEquals("Stjernepladsen", newCustomer.getAddress());
            assertEquals(9000, newCustomer.getPostalCode());
            assertEquals("Aalborg", newCustomer.getCity());
            assertEquals("+4528971744", newCustomer.getPhoneNo());
            assertEquals("Mikkel4a@hotmail.com", newCustomer.getMail());
            assertEquals("Pejse", newCustomer.getCustomerGroup());
        }
        catch(MailAlreadyExistsException e)
        {
            thrown = true;
        }
        catch(CustomerGroupController.CustomerGroupNameAlreadyExistsException e)
        {
            thrown = true;
            
        }
        catch(CustomerGroup.DiscountDoesntMatchException e)
        {
            thrown = true; 
        }
        assertTrue(thrown);
    }
    @Test
    public void testFindCustomer()
    {
        boolean thrown = false;
        
        try
        {
            customerCtrl.createCustomer("Mikkel", "Mikkelsen", "Mikkelvej", 9000, "Mikkelby", 
                "+45Mikkel", "Mikkel@Mikkel.dk", customerGroupCtrl.findCustomerGroup("Pejse"));
            assertNotNull(customerCtrl.findCustomer(1));
        }
        catch(MailAlreadyExistsException e)
        {
            thrown = true;
        }

    }
    @Test
    public void testUpdateCustomer()
    {
        try{
            Customer customer = customerCtrl.findCustomer("Mikkel@Mikkel.dk");
            customerCtrl.updateCustomer(customer, "Mikel", "Mikelsen", "Mikelvej", 900, "Mikelby", "+45Mikel", 
                "Mikel@Mikel.dk", customerGroupCtrl.findCustomerGroup("Pejse"));
            assertEquals("Mikel", customer.getFirstName());
            assertEquals("Mikelsen", customer.getLastName());
            assertEquals("Mikelvej", customer.getAddress());
            assertEquals(900, customer.getPostalCode());
            assertEquals("Mikelby", customer.getCity());
            assertEquals("+45Mikel", customer.getPhoneNo());
            assertEquals("Mikel@Mikel.dk", customer.getMail());
            assertEquals("Pejse", customer.getCustomerGroup().getName());
        }
        catch(MailAlreadyExistsException e)
        {}
    }
}
