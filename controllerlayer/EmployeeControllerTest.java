package controllerlayer;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import modellayer.*;
import java.util.Date;

/**
 * The test class EmployeeControllerTest.
 *
 * @Jacob
 * @24/05-2018
 */
public class EmployeeControllerTest
{
    private controllerlayer.EmployeeController employee1;

    /**
     * Default constructor for test class EmployeeControllerTest
     */
    public EmployeeControllerTest()
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
        employee1 = new controllerlayer.EmployeeController();
    }

    /**
     * Method used to test the method createEmployee in the controller, to make sure it gets added in the container.
     */
    @Test
    public void testCreateEmployee()
    {
        try
        {
            employee1.createEmployee(null, "Dyne Jr.", "Larsen", "Jyskvej 234", 8620, "Silkeborg", "42310633", "DyneJr@hotmail.dk", true, modellayer.EmployeeRole.SALESMAN, new Date(2018, 03, 14));
        }
        catch(EmployeeController.IsNotAuthorizedException e)
        {
            System.err.println(e.getMessage());
        }
        catch(modellayer.MailAlreadyExistsException m)
        {
            System.err.println(m.getMessage());
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
    
    /**
     * Method used to test if the method findEmployee in the controller works.
     */
    @Test
    public void testFindEmployee()
    {
        assertNotNull(employee1.findEmployee(1));
    }
    
    /**
     * Method used to test if the method findEmployee searches through the container and finds the right object.
     */
    @Test
    public void testEmployeeDoesNotExist()
    {
        assertNull(employee1.findEmployee(99));
    }
    
    /**
     * Method used to test if the method updateEmployee actually corrects all the information of the employee when updated.
     */
    @Test
    public void testUpdateEmployee() throws MailAlreadyExistsException
    {
        Employee employee = employee1.findEmployee(1);
        try
        {
            employee1.updateEmployee(employee, "Fru Dyne", "Jensen", "Jyskvej 321", 8610, "Resenbro", "42310632", "Frudynelarsen123@hotmail.dk", false, modellayer.EmployeeRole.MANAGEMENT, new Date(2018, 04, 20));
        }
        
        catch(modellayer.MailAlreadyExistsException m)
        {
            System.err.println(m.getMessage());
        }
        
        assertEquals("Fru Dyne", employee.getFirstName());
        assertEquals("Jensen", employee.getLastName());
        assertEquals("Jyskvej 321", employee.getAddress());
        assertEquals(8610, employee.getPostalCode());
        assertEquals("Resenbro", employee.getCity());
        assertEquals("42310632", employee.getPhoneNo());
        assertEquals("Frudynelarsen123@hotmail.dk", employee.getMail());
        assertEquals(false, employee.getActive());
        assertEquals(modellayer.EmployeeRole.MANAGEMENT, employee.getRole());
        assertEquals(new Date(2018, 04, 20), employee.getBirthday());
    }
}
