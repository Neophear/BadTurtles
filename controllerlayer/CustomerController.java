package controllerlayer;
import modellayer.*;
import java.util.ArrayList;
/**
 * 
 * 
 * @author (Mikkel)
 * @version (24-05-2018)
 */
public class CustomerController
{
    // instansvariabler - erstat eksemplet herunder med dine egne variabler
    private CustomerContainer customerContainer;
    
    public CustomerController()
    {
        customerContainer = CustomerContainer.getInstance();
    }
    
    /**
     * Creates a customer
     */
    public Customer createCustomer(String firstName, String lastName, String address, int postalCode,
        String city, String phoneNo, String mail, CustomerGroup customerGroup) throws MailAlreadyExistsException
    {   
        if(findCustomer(mail)!= null)
            throw new MailAlreadyExistsException(mail);
        
        Customer customer = new Customer(firstName, lastName, address, postalCode,
            city, phoneNo, mail, customerGroup);
        customerContainer.add(customer);
        
        return customer;
    }
    /**
     * Finds a customer by customerId
     */
    public Customer findCustomer(int customerId)
    {
        return customerContainer.findCustomer(customerId); 
    }
     
    /**
     * Finds customer by mail
     */
    public Customer findCustomer(String mail)
    {
        return customerContainer.findCustomer(mail);
    }
    
    /**
     * Updates a specific customer attributes
     */
    public void updateCustomer(Customer customer, String firstName, String lastName, String address, int postalCode,
        String city, String phoneNo, String mail, CustomerGroup customerGroup) throws MailAlreadyExistsException
    {
        if(!mail.equalsIgnoreCase(mail) && findCustomer(mail)!= null)
            throw new MailAlreadyExistsException(mail);
        customer.updateAll(firstName, lastName, address, postalCode, city, 
            phoneNo, mail, customerGroup);
    }
    
    /**
     * Returns a list containing all customers
     */
    public ArrayList<Customer> getAllCustomers()
    {
        return customerContainer.getList();
    }
    
    /**
     * Returns orders from a specific customer
     */
    public ArrayList<Order> findOrdersFromCustomer(Customer customer)
    {
        return new OrderController().findOrdersFromCustomer(customer);
    }
    
    /**
     * Returns all customers from a specific group
     */
    public ArrayList<Customer> findCustomersInCustomerGroup(CustomerGroup customerGroup)
    {   
        return customerContainer.getCustomersInCustomerGroup(customerGroup);
    }
}