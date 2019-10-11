package modellayer;
import java.util.Iterator;
import java.util.ArrayList;
/**
 * Lav en beskrivelse af klassen CustomerGroupContainer her.
 * 
 * @author (Mikkel)
 * @version (24-05-2018)
 */
public class CustomerGroupContainer extends BaseContainer<CustomerGroup>
{
    // instansvariabler - erstat eksemplet herunder med dine egne variabler
    
    private static CustomerGroupContainer instance;
    /**
     * Konstruktør for objekter af klassen CustomerGroupContainer
     */
    private CustomerGroupContainer()
    {
        
    }

    public static CustomerGroupContainer getInstance()
    {
        if(instance == null)
            instance = new CustomerGroupContainer();
        
        return instance; 
    }
    
    /**
     * Finds a customerGroup by its name
     */
    public CustomerGroup findCustomerGroup(String name)
    {
        boolean found = false; 
        CustomerGroup customerGroup = null;
        CustomerGroup temp = null;
        Iterator<CustomerGroup> it = getList().iterator();
        while(!found && it.hasNext())
        {
            temp = it.next();
            if(temp.getName().equals(name))
            {
                found = true;
                customerGroup = temp;
            }
        }
        return found ? customerGroup : null;
    }
}