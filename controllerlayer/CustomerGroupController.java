package controllerlayer;
import modellayer.*;
import java.util.ArrayList;

/**
 * Lav en beskrivelse af klassen CustomerGroupController her.
 * 
 * @author (dit navn her)
 * @version (versions nummer eller dato her)
 */
public class CustomerGroupController
{
    // instansvariabler - erstat eksemplet herunder med dine egne variabler
    private CustomerGroupContainer customerGroupContainer;

    /**
     * Konstruktør for objekter af klassen CustomerGroupController
     */
    public CustomerGroupController()
    {
        customerGroupContainer = CustomerGroupContainer.getInstance();
    }
    
    /**
     * Creates a customerGroup
     */
    public CustomerGroup createCustomerGroup(String name, double discount)
        throws CustomerGroupNameAlreadyExistsException, CustomerGroup.DiscountDoesntMatchException
    {
        if(findCustomerGroup(name) != null)
            throw new CustomerGroupNameAlreadyExistsException(name);
        
        CustomerGroup customerGroup = new CustomerGroup(name, discount);
        customerGroupContainer.add(customerGroup);
        return customerGroup;
    }
    
    /**
     * Finds a customerGroup by its name
     */
    public CustomerGroup findCustomerGroup(String name)
    {
        return customerGroupContainer.findCustomerGroup(name);
    }
   
    /**
     * Updates a customerGroups attributes
     */
    public void updateCustomerGroup(CustomerGroup customerGroup, String name, double discount)
        throws CustomerGroupNameAlreadyExistsException, CustomerGroup.DiscountDoesntMatchException
    {
        customerGroup.setName(name);
        customerGroup.setDiscount(discount);
        if(!name.equalsIgnoreCase(name) && findCustomerGroup(name) != null)
            throw new CustomerGroupNameAlreadyExistsException(name);
    }
    
    /**
     * Deletes a customerGroup
     */
    public void deleteCustomerGroup(CustomerGroup customerGroup) throws CustomerGroupIsNotEmptyException
    {
        ArrayList<Customer> customers = new CustomerController().findCustomersInCustomerGroup(customerGroup);
        
        if(customers.size() == 0)
            customerGroupContainer.remove(customerGroup);
        else
            throw new CustomerGroupIsNotEmptyException(customerGroup);
    }
    
    /**
     * Returns a list containing all customerGroups
     */
    public ArrayList<CustomerGroup> getAllCustomerGroups()
    {
        return customerGroupContainer.getList();
    }
    
    /**
     * Exception class
     */
    public class CustomerGroupIsNotEmptyException extends Exception
    {
        public CustomerGroupIsNotEmptyException(CustomerGroup customerGroup)
        {
            super("Gruppen " + customerGroup.getName() + " er ikke tom");
        }
    }
    
    /**
     * Exception class
     */
    public class CustomerGroupNameAlreadyExistsException extends Exception
    {
        public CustomerGroupNameAlreadyExistsException(String name)
        {
            super("Gruppen " + name + " findes allerede");
        }
    }
}
