package modellayer;

/**
 * Abstract class Person - Used as a Super class for Employee and Customer.
 *
 * @Jacob
 * @17/05-2018
 */
public abstract class Person
{
    private String firstName;
    private String lastName;
    private String address;
    private int postalCode;
    private String city;
    private String phoneNo;
    private String mail;

    /**
     * Constructor for the class Person.
     */
    public Person(String firstName, String lastName, String address, int postalCode, String city, String phoneNo, String mail)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.phoneNo = phoneNo;
        this.mail = mail;
    }
    
    /**
     * Set methods.
     */
    public void setFirstName(String newFirstName) { firstName = newFirstName; }
    public void setLastName(String newLastName) { lastName = newLastName; }
    public void setAddress(String newAddress) { address = newAddress; }
    public void setPostalCode(int newPostalCode) { postalCode = newPostalCode; }
    public void setCity(String newCity) { city = newCity; }
    public void setPhoneNo(String newPhoneNo) { phoneNo = newPhoneNo; }
    public void setMail(String newMail) { mail = newMail; }
    
    /**
     * Get methods.
     */
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getAddress() { return address; }
    public int getPostalCode() { return postalCode; }
    public String getCity() { return city; }
    public String getPhoneNo() { return phoneNo; }
    public String getMail() { return mail; }

}
