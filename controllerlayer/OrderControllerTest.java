package controllerlayer;


import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import modellayer.*;
import java.util.Date;

/**
 * The test class OrderControllerTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class OrderControllerTest
{
    private controllerlayer.CustomerController customerCtrl;
    private controllerlayer.OrderController orderCtrl;
    private controllerlayer.SalesProductController salesProductCtrl;
    private controllerlayer.CustomerGroupController customerGroupCtrl;
    private controllerlayer.EmployeeController employeeCtrl;

    /**
     * Default constructor for test class OrderControllerTest
     */
    public OrderControllerTest()
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
        customerCtrl = new controllerlayer.CustomerController();
        customerGroupCtrl = new controllerlayer.CustomerGroupController();
        orderCtrl = new controllerlayer.OrderController();
        salesProductCtrl = new controllerlayer.SalesProductController();
        employeeCtrl = new controllerlayer.EmployeeController();
        Employee loggedInEmployee = new Employee("bo", "hansen", "østvej", 9000, "aalborg", "898", "bomail", true, EmployeeRole.OFFICEWORKER, new Date(1999, 02, 02));

        boolean thrown = false;
        try
        {
            Employee employee = employeeCtrl.createEmployee( loggedInEmployee, "Anders", "olesen", "Nordgade", 9000, "Aalborg", "999", "andersMail", true, EmployeeRole.EMPLOYEE, new Date(1990, 05, 02));
            customerGroupCtrl.createCustomerGroup("Smede", 10);
            Customer customer = customerCtrl.createCustomer("Hans", "Jørgen","Vejgårdsvej", 9000, "Aalborg", "888", "mail", customerGroupCtrl.findCustomerGroup("Smede"));
            SalesProduct salesProduct = salesProductCtrl.createSalesProduct("Søm", "Nord", 200, 50, 500, 300);
            salesProductCtrl.addSalesProductPrice(loggedInEmployee, salesProduct,  new Date(2018, 05, 18), 100.0);
        }
        catch (EmployeeController.IsNotAuthorizedException e)
        {
            thrown = true;
        }
        catch (CustomerGroupController.CustomerGroupNameAlreadyExistsException e)
        {
            thrown = true;
        }
        catch (MailAlreadyExistsException e)
        {
            thrown = true;
        }
        catch (SalesProductController.IsNotAuthorizedException e)
        {}
        catch (SalesProductController.maxMinStockDoesNotMatchException e)
        {}
        catch (CustomerGroup.DiscountDoesntMatchException e)
        {}
    }

    @Test
    public void createOrder()
    {
        Order newOrder = orderCtrl.createOrder(customerCtrl.findCustomer(1), employeeCtrl.findEmployee(1));
        orderCtrl.addSalesProduct(newOrder, 1);
        orderCtrl.saveOrder(newOrder);
        assertEquals(customerCtrl.findCustomer(1), newOrder.getCustomer());
        assertEquals(employeeCtrl.findEmployee(1), newOrder.getEmployee());
        assertEquals(newOrder.getSalesProducts().get(0), salesProductCtrl.findSalesProduct(1));
        assertEquals(newOrder.getCurrentStatus().getStatusEnum(), StatusEnum.PAID);
    }

    @Test
    public void findOrder()
    {
        Order newOrder = orderCtrl.createOrder(customerCtrl.findCustomer(1), employeeCtrl.findEmployee(1));
        orderCtrl.addSalesProduct(newOrder, 1);
        orderCtrl.saveOrder(newOrder);
        orderCtrl.findOrder(1);
        assertEquals(newOrder.getOrderId(), orderCtrl.findOrder(1).getOrderId());
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
}
