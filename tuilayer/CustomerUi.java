package tuilayer;
import java.util.Scanner;
import controllerlayer.*;
import modellayer.*;
import java.util.ArrayList;

/**
 * Lav en beskrivelse af klassen CustomerUI her.
 * 
 * @author (Mikkel)
 * @version (24-05-2018)
 */
public class CustomerUi extends BaseUi<Customer>
{
    private CustomerController customerCtrl;
    private CustomerGroupController customerGroupCtrl;
    
    /**
     * Konstruktør for objekter af klassen CustomerUI
     */
    public CustomerUi()
    {
        customerCtrl = new CustomerController();
        customerGroupCtrl = new CustomerGroupController();
    }

    /**
    * Opens a menu with different options
    */
    public void menu()
    {
        boolean running = true;
        String[] options = { "Opret kunde", "Find kunde", "Opret kundegruppe", "Find kundegruppe", "Liste over kunder" };
        while(running)
        {
            int chosenOption = printTitledMenu("Hovedmenu > Kunde Menu", options, "Tilbage");
            switch(chosenOption)
            {
                case 1:
                    createCustomer();
                    break;
                case 2:
                    clearScr();
                    findCustomer();
                    break;
                case 3:
                    clearScr();
                    createCustomerGroup();
                    break;
                case 4:
                    clearScr();
                    findCustomerGroup();
                    break;
                case 5:
                    clearScr();
                    printAllShort(customerCtrl.getAllCustomers());
                    break;
                case 0:
                    running = false;
                    break;
            }
        }
    }

    /**
     * Firstly checks if a CustomerGroup has been created, if it has then
     * it proceeds to create the customer and if not a message appears with
     * information
     */
    private void createCustomer()
    {
        CustomerController customerController = new CustomerController();
        if(customerGroupCtrl.getAllCustomerGroups().size() == 0)
        {
            println("Kundegruppe skal være oprettet for at oprettelse af kunde er mulig.");
            pressEnter();
        }
        else
        {  
            String mail = askForString("Mail");

            if(customerCtrl.findCustomer(mail) == null)
            {
                String firstName = askForString("fornavn");
                String lastName = askForString("efternavn");
                String address = askForString("adresse");
                int postalCode = askForInt("postnummer");
                String city = askForString("by");
                String phoneNo = askForString("telefonnummer");
                CustomerGroup customerGroup = askForListObject("kundegruppe", customerGroupCtrl.getAllCustomerGroups());

                try   
                {
                    Customer customer = customerController.createCustomer(firstName, lastName, address, postalCode, city, phoneNo, mail, customerGroup);
                    customerMenu(customer);
                }
                catch(MailAlreadyExistsException e)
                {
                    println("Mailen findes allerede");
                    pressEnter();
                }
            }
            else
            {
               println("Mailen findes allerede");
               pressEnter();
            }
            
        }
    }
    
    /**
     * Looks for a certain customer by using mail as search parameter
     * if the mail doesnt exist a message will appear, 
     * and if the mail does exist a customer will be returned
     */
    private void findCustomer()
    {
        Customer customer = null;
        
        println("Brug enten Id eller mail til at søge på en kunde");
        String input = getRequiredString();
        if (input.matches("\\d+"))
            customer = customerCtrl.findCustomer(Integer.parseInt(input));
        else
            customer = customerCtrl.findCustomer(input);
        
        if (customer == null)
        {
            println("kunden kan ikke findes");
            pressEnter();
        }    
        else
            customerMenu(customer);
    }
    
    /**
     * Opens a menu for Customer
     */
    private void customerMenu(Customer customer)
    {
        boolean running = true; 
        String[] options = { "Opdater", "Vis alle ordrer" };
        while(running)
        {
            int chosenOption = printTitledMenu(("Hovedmenu > Kunde Menu > Opdater kunde: " + customer), options, "Tilbage", customer);

            switch(chosenOption)
            {
                case 1:
                    updateCustomer(customer);
                    break;
                case 2:
                    listAllOrders(customer);
                    break;
                case 0:
                    running = false;
                    break;
            }
        }   
    }
    
    /**
     * Opens a menu for CustomerGroup
     */
    private void customerGroupMenu(CustomerGroup customerGroup)
    {
        boolean running = true; 
        String[] options = { "Opdatér", "Slet"};
        while(running)
        {
            int chosenOption = printTitledMenu(("Hovedmenu > Kunde Menu > '" + customerGroup + "'"), options, "Tilbage");

            switch(chosenOption)
            {
                case 1:
                    updateCustomerGroup(customerGroup);
                    break;
                case 2:
                    deleteCustomerGroup(customerGroup);
                    break;
                case 0:
                    running = false;
                    break;
            }
        }   
    }
    
    /**
     * Prints out a list with all customerGroups
     */
    private void listAllCustomerGroups()
    {
        for(CustomerGroup cg : new CustomerGroupController().getAllCustomerGroups())
            println(cg.getName());
    }
    
    /**
     * Prints out a list with all Orders
     */
    private void listAllOrders(Customer customer)
    {
        for(Order o : customerCtrl.findOrdersFromCustomer(customer))
            println(o.toString());
        
        if(customerCtrl.findOrdersFromCustomer(customer).isEmpty())
            println("Der er ingen ordrer");
        
        pressEnter();
    }

    /**
     * Updates the attributes for a specific customer found by mail
     */
    private void updateCustomer(Customer customer)
    {
        String mail = askForStringWithDefault("mail", customer.getMail());
        
        if(mail.equalsIgnoreCase(customer.getMail()) || customerCtrl.findCustomer(mail) == null)
        {
            String firstName = askForStringWithDefault("fornavn", customer.getFirstName());
            String lastName = askForStringWithDefault("efternavn", customer.getLastName());
            String address = askForStringWithDefault("adresse", customer.getAddress());
            int postalCode = askForIntWithDefault("postnummer", customer.getPostalCode());
            String city = askForStringWithDefault("by", customer.getCity());
            String phoneNo = askForStringWithDefault("telefonnummer", customer.getPhoneNo());
            CustomerGroup newGroup = askForListObjectWithDefault("kundegruppe", customerGroupCtrl.getAllCustomerGroups(), customer.getCustomerGroup());
            
            try
            {
                customerCtrl.updateCustomer(customer, firstName, lastName, address, postalCode, city, phoneNo, mail, newGroup);
            }
            catch(MailAlreadyExistsException e)
            {
                println("Mail findes allerede!");
                pressEnter();
            }
        } 
        else
        {
            println("Mail findes allerede!");
            pressEnter();
        }
    }
    
    /**
     * Creates a customerGroup
     */
    private void createCustomerGroup() 
    {
        String name = askForString("navn");
        CustomerGroupController customerGroupCtrl = new CustomerGroupController();
        
        if(customerGroupCtrl.findCustomerGroup(name) == null)
        {
            boolean tryAgain = true;
            while(tryAgain)
            {
                double discount = askForDouble("discount");
                try
                {
                    CustomerGroup customerGroup = customerGroupCtrl.createCustomerGroup(name, discount);
                    tryAgain = false;
                    customerGroupMenu(customerGroup);
                }
                catch(CustomerGroupController.CustomerGroupNameAlreadyExistsException e)
                {
                    println(e.getMessage());
                    tryAgain = false;
                }
                catch(CustomerGroup.DiscountDoesntMatchException e)
                {
                    println(e.getMessage());
                    pressEnter();
                }
            }
        }
        else
        {
            println("Gruppenavnet findes allerede");
            pressEnter();
        }

    }
    
    /**
     * Finds a specific customerGroup by its name
     */
    public void findCustomerGroup()
    {
        String name = askForString("navn");
        CustomerGroup customerGroup = customerGroupCtrl.findCustomerGroup(name);
        
        if(customerGroup == null)
        {
            println("Kundegruppen kan ikke findes");
            pressEnter();
        }
        else
            customerGroupMenu(customerGroup);
    }
    
    /**
     * Updates customerGroup attributes
     */
    public void updateCustomerGroup(CustomerGroup customerGroup)
    {
        String name = askForStringWithDefault("navn", customerGroup.getName());
        
        if(name.equalsIgnoreCase(customerGroup.getName()) || customerGroupCtrl.findCustomerGroup(name) == null)
        {
            double discount = askForDoubleWithDefault("discount", customerGroup.getDiscount());
            try
            {
                customerGroupCtrl.updateCustomerGroup(customerGroup, name, discount);
            }
            catch(CustomerGroupController.CustomerGroupNameAlreadyExistsException e)
            {
                println("Navnet findes allerede");
                pressEnter();
            }
            catch(CustomerGroup.DiscountDoesntMatchException e)
            {
                println("Discount skal være mellem 0 og 20");
                pressEnter();
            }
        }
        else
        {
            println("Navnet findes allerede");
            pressEnter();
        }
    }
    
    /**
     * Deletes customerGroup if it is empty. If it contains customers a message
     * will appear
     */
    public void deleteCustomerGroup(CustomerGroup customerGroup)
    {
        if(customerCtrl.findCustomersInCustomerGroup(customerGroup).isEmpty())
            println("Kundegruppen er ikke tom og kan derfor ikke slettes");
        else
        {
            println("Er du sikker på at du vil slette denne gruppe?");
            
            if(askForBoolean("svar"))
            {
                try
                { 
                    customerGroupCtrl.deleteCustomerGroup(customerGroup);
                    println("Kundegruppen blev slettet");
                }
                catch(CustomerGroupController.CustomerGroupIsNotEmptyException e)
                {
                    println("Kundegruppen er ikke tom");
                }
                pressEnter();
            }
        }
    }
    
    /**
     * Prints info about customers
     */
    protected void printInfo(Customer customer)
    {
        println("Id: " + customer.getCustomerId());
        println("Fornavn: " + customer.getFirstName());
        println("Efternavn: " + customer.getLastName());
        println("Adresse: " + customer.getAddress());
        println("Postnummer: " + customer.getPostalCode());
        println("City: " + customer.getCity());
        println("Telefonnummer: " + customer.getPhoneNo());
        println("Mail: " + customer.getMail());
        println("Kundegruppe: " + customer.getCustomerGroup());
    }
}