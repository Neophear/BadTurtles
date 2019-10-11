package modellayer;
import java.util.LinkedList;
import java.util.Date;
import java.util.Calendar;

public class Order
{
    private static int orderCounter = 0;
    private int orderId;
    private Date createdTime;
    private Customer customer;
    private Employee employee;
    private LinkedList<Status> status;
    private LinkedList<SalesProduct> salesProducts;
    
    public Order(Customer customer, Employee employee)
    {
        orderCounter++;
        orderId = orderCounter;
        this.createdTime = Calendar.getInstance().getTime();
        this.customer = customer;
        this.employee = employee;
        status = new LinkedList<>();
        salesProducts = new LinkedList<>();
    }
    
    public int getOrderId() { return orderId; }
    public Date getCreatedTime() { return createdTime; }
    public Customer getCustomer() { return customer; }
    public Employee getEmployee() { return employee; }
    public LinkedList<SalesProduct> getSalesProducts() { return salesProducts; }
    public Status getCurrentStatus() { return status.getLast(); }
    
    public Status addStatus(StatusEnum status)
    {
        Status newStatus = new Status(status);
        this.status.add(newStatus);
        
        return newStatus;
    }
    
    public void addSalesProduct(SalesProduct salesProduct)
    {
        salesProducts.add(salesProduct);
    }

    public String toString()
    {
        return "Ordre " + orderId + " - " + status.getLast();
    }
}
