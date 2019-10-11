package modellayer;


/**
 * Lav en beskrivelse af klassen Customer her.
 * 
 * @author (Mikkel)
 * @version (24-05-2018)
 */
public class Customer extends Person
{
    // instansvariabler - erstat eksemplet herunder med dine egne variabler
    private static int customerCounter = 0;
    private int customerId;
    private CustomerGroup customerGroup;
    /**
     * Konstruktør for objekter af klassen Customer
     */
    public Customer(String firstName, String lastName, String address, int postalCode,
        String city, String phoneNo, String mail, CustomerGroup customerGroup)
    {
        super(firstName, lastName, address, postalCode, city, phoneNo, mail);
        customerCounter++;
        customerId = customerCounter;
        this.customerGroup = customerGroup;
    }
    
    /**
     * Set method for setting a customerGroup to a customer
     */
    public void setCustomerGroup(CustomerGroup newCustomerGroup){ customerGroup = newCustomerGroup; }
    
    /**
     * Get methods for returning customerId and for returning customerGroup
     */
    public int getCustomerId() {return customerId;}
    public CustomerGroup getCustomerGroup() { return customerGroup; }
    /**
     * Updates all attributes on a customer
     */
    public void updateAll(String firstName, String lastName, String address, int postalCode,
        String city, String phoneNo, String mail, CustomerGroup customerGroup)
        
    {
        setFirstName(firstName);
        setLastName(lastName);
        setAddress(address);
        setPostalCode(postalCode);
        setCity(city);
        setPhoneNo(phoneNo);
        setMail(mail);
        this.customerGroup = customerGroup;
    }
    
    public String toString()
    {
        return "[Id: " + customerId + "] "+ getFirstName() + " " + getLastName(); 
    }
}
