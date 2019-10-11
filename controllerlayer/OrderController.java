package controllerlayer;
import java.util.Date;
import modellayer.*;
import java.util.ArrayList;

public class OrderController
{
    private CustomerController customerCtrl;
    private EmployeeController employeeCtrl;
    private SalesProductController salesProductCtrl;

    private OrderContainer orderContainer;

    /**
     * 
     */
    public OrderController()
    {
        orderContainer = OrderContainer.getInstance();

        employeeCtrl = new EmployeeController();
        customerCtrl = new CustomerController();
        salesProductCtrl = new SalesProductController();
    }

    public Customer findCustomer(int customerId)
    {
        return new CustomerController().findCustomer(customerId);
    }

    public Order createOrder(Customer customer, Employee employee)
    {
        Order order = new Order(customer, employee);

        return order;
    }
    
    public ArrayList<SalesProduct> getAllSalesProducts()
    {
        return salesProductCtrl.getAll();
    }
    
    public SalesProduct findSalesProduct(int productId)
    {
        return new SalesProductController().findSalesProduct(productId);
    }

    public void addSalesProduct(Order order, int salesProductId)
    {
        SalesProduct salesProduct = new SalesProductController().findSalesProduct(salesProductId);
        order.addSalesProduct(salesProduct);
    }

    public void saveOrder(Order order)
    {
        order.addStatus(StatusEnum.PAID);
        orderContainer.add(order);
    }

    public Order findOrder(int orderId)
    {
        return orderContainer.findOrder(orderId);
    }

    public ArrayList<Order> findOrdersFromCustomer(Customer customer)
    {
        return orderContainer.findOrdersFromCustomer(customer);
    }

    public ArrayList<Order> findOrdersFromEmployee(Employee employee)
    {
        return orderContainer.findOrdersFromEmployee(employee);
    }

    public Status setStatus(Order order, StatusEnum statusEnum)
    {
        return order.addStatus(statusEnum);
    }

    public class OrderControllerCreateOrderException extends Exception
    {
        public OrderControllerCreateOrderException(Customer customer, Employee employee)
        {
            super("der mangler en " + customer + " eller en " + employee);
        }
    }
}
