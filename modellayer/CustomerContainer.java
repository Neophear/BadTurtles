package modellayer;
import java.util.ArrayList;
import java.util.Iterator;
/**
 * Lav en beskrivelse af klassen CustomerContainer her.
 * 
 * @author (Mikkel)
 * @version (24-05-2018)
 */
public class CustomerContainer extends BaseContainer<Customer>
{
    private static CustomerContainer instance;
    /**
     * Konstruktør for objekter af klassen CustomerContainer
     */
    public CustomerContainer()
    {
        
    }
    
    public static CustomerContainer getInstance()
    {
        if(instance == null)
            instance = new CustomerContainer();
        return instance; 
    }
    
    /**
     * Finds a customer by customerId
     */
    public Customer findCustomer(int customerId)
    {
        return findCustomer(customerId, null);
    }
    
    /**
     * Finds a customer by mail
     */
    public Customer findCustomer(String mail) 
    {
        return findCustomer(0, mail);
    }
    
    /**
     * Finds a customer from either mail or customerId
     */
    private Customer findCustomer(int customerId, String mail)
    {
       boolean found = false; 
       Customer customer = null;
       Customer temp = null;
       Iterator<Customer> it = getList().iterator();
       while(!found && it.hasNext())
       {
            temp = it.next();
        if((customerId != 0 && temp.getCustomerId() == customerId)
                || mail != null && temp.getMail().equalsIgnoreCase(mail))
            {
                found = true;
                customer = temp;
            }
       }
       return found ? customer : null;
    }
    
    /**
     * Returns customers from a specific group
     */
    public ArrayList<Customer> getCustomersInCustomerGroup(CustomerGroup customerGroup)
    {
        ArrayList<Customer> customers = new ArrayList<>();
        for(Customer customer : getList())
            if(customer.getCustomerGroup() == customerGroup)
                customers.add(customer);
        return customers;
    }
    
}
