package modellayer;
import java.util.ArrayList;
import java.util.Iterator;

public class OrderContainer extends BaseContainer<Order>
{
    private static OrderContainer instance;
    private OrderContainer ()
    {
        
    }
    
    public static OrderContainer getInstance()
    {
        if(instance == null)
            instance = new OrderContainer();
        return instance;
    }
    
    public ArrayList<Order> findOrdersFromEmployee(Employee employee)
    {
        ArrayList<Order> orders = new ArrayList<>();
        
        for(Order o : getList())
            if(o.getEmployee() == employee)
                orders.add(o);
        return orders;
    }
    
    public ArrayList<Order> findOrdersFromCustomer(Customer customer)
    {
        ArrayList<Order> orders = new ArrayList<>();
        
        for(Order o : getList())
            if(o.getCustomer() == customer)
                orders.add(o);
        return orders;
    }
    
    public Order findOrder(int orderId)
    {
        boolean found = false;
        Order order = null;
        Order temp = null;
        Iterator<Order> it = getList().iterator();
        
        while(!found && it.hasNext())
        {
            temp = it.next();
            if(temp.getOrderId() == orderId)
            {
                found = true;
                order = temp;
            }
        }
        return order;
    }
    
}
