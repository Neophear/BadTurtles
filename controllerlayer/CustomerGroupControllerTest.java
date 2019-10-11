 package controllerlayer;
import modellayer.*;


import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Testklassen CustomerGroupControllerTest.
 *
 * @author (dit navn her)
 * @version (versionsnummer eller dato her)
 */
public class CustomerGroupControllerTest
{
    private controllerlayer.CustomerGroupController customerGroupCtrl;

    /**
     * Standardkonstruktør for testklassen CustomerGroupControllerTest
     */
    public CustomerGroupControllerTest()
    {
    }

    /**
     * Opsæt test-fixtures.
     *
     * Kaldt før hver testmetode.
     */
    @Before
    public void setUp()
    {
        customerGroupCtrl = new controllerlayer.CustomerGroupController();
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
    public void testCreateCustomerGroup()
    {
        boolean thrown = false;
        try
        {
            customerGroupCtrl.createCustomerGroup("Erhverv", 10);
            customerGroupCtrl.createCustomerGroup("Erhverv", 10);
        }
        catch (CustomerGroupController.CustomerGroupNameAlreadyExistsException e)
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
    public void testFindCustomerGroup()
    {
        boolean thrown = false;
        try
        {
            customerGroupCtrl.createCustomerGroup("Håndværker", 10);
        }
        catch (CustomerGroupController.CustomerGroupNameAlreadyExistsException e)
        {
            thrown = true;
        }
        catch(CustomerGroup.DiscountDoesntMatchException e)
        {
            thrown = true;
        }

        CustomerGroup customerGroup = customerGroupCtrl.findCustomerGroup("Håndværker");
        assertNotNull(customerGroup);
    }

    @Test
    public void testUpdateCustomerGroup()
    {
        boolean thrown = false;
        CustomerGroup newCustomerGroup = null;
        try
        {
            newCustomerGroup = customerGroupCtrl.createCustomerGroup("Privat", 12);
            customerGroupCtrl.updateCustomerGroup(newCustomerGroup, "KundeGruppe", 10);
            
        }
        catch (CustomerGroupController.CustomerGroupNameAlreadyExistsException e)
        {
            thrown = true;
        }
        catch(CustomerGroup.DiscountDoesntMatchException e)
        {
            thrown = true;
        }
        assertEquals("KundeGruppe", newCustomerGroup.getName());
        assertEquals(10, newCustomerGroup.getDiscount(), 0.001);
    }
    
    @Test
    public void testDeleteCustomerGroup()
    {
        boolean thrown = false;
        CustomerController customerCtrl = new CustomerController();
        CustomerGroup newCustomerGroup;
        CustomerGroup newCustomerGroup2;
        Customer newCustomer;
        try
        {
            newCustomerGroup = customerGroupCtrl.createCustomerGroup("KøkkenHandlere", 15);
            newCustomerGroup2 = customerGroupCtrl.createCustomerGroup("Pejsehandlere", 13);
            newCustomer = customerCtrl.createCustomer("Mikkel", "Stiigsen", "Rasmusvej", 9292, "JacobBy", "88888888", "Mathias@Mathias.com", newCustomerGroup2);
            customerCtrl.updateCustomer(newCustomer, "Mikkel", "Stiigsen", "Rasmusvej", 9292, "JacobBy", "88888888", "Mathias@Mathias.com", newCustomerGroup);
            customerGroupCtrl.deleteCustomerGroup(newCustomerGroup);
            customerGroupCtrl.deleteCustomerGroup(newCustomerGroup2);
        }
        catch (CustomerGroupController.CustomerGroupNameAlreadyExistsException e)
        {
            thrown = true;
        }
        catch (MailAlreadyExistsException e)
        {
            thrown = true;
        }
        catch(CustomerGroupController.CustomerGroupIsNotEmptyException e)
        {
            thrown = true;
        }
        catch(CustomerGroup.DiscountDoesntMatchException e)
        {
            thrown = true;
        }

        
        // try
        // {
            // customerGroupCtrl.deleteCustomerGroup(newCustomerGroup);
        // }
        // catch(CustomerGroupController.CustomerGroupIsNotEmptyException e)
        // {
            // thrown = true;
        // }


        // try
        // {
            // customerGroupCtrl.deleteCustomerGroup(newCustomerGroup);
        // }
        // catch(CustomerGroupController.CustomerGroupIsNotEmptyException e)
        // {
            // thrown = true;
        // }

    }
}



