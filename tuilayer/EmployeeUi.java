package tuilayer;
import controllerlayer.*;
import modellayer.*;
import java.util.Date;

/**
 * Class EmployeeUi used as a textbased ui for the Employee through the layers.
 *
 * @Jacob
 * @24/05-2018
 */
public class EmployeeUi extends BaseUi<Employee>
{
    private EmployeeController employeeCtrl;
    private Employee loggedInEmployee;

    /**
     * Constructor for objects of class EmployeeUI
     */
    public EmployeeUi(Employee loggedInEmployee)
    {
        employeeCtrl = new EmployeeController();
        this.loggedInEmployee = loggedInEmployee;
    }

    /**
     * Text based menu from where it's possible to create, find or print a list of all employees.
     */
    public void menu()
    {
        boolean running = true;
        String[] options = { "Tilføj medarbejder", "Find medarbejder", "Liste over medarbejdere" };
        while(running)
        {
            int chosenOption = printTitledMenu("Hovedmenu > Medarbejder Menu", options, "Tilbage");
            switch (chosenOption)
            {
                case 1:
                    createEmployee();
                    break;
                case 2:
                    clearScr();
                    findEmployee();
                    break;
                case 3:
                    clearScr();
                    printAllShort(employeeCtrl.getAll());
                    break;
                case 0:
                    running = false;
                    break;
            }
        }
    }

    /**
     * Method used to create a new employee and send the user to another menu.
     */
    private void createEmployee()
    {
        String mail = askForString("mail");
        if(employeeCtrl.findEmployee(mail) == null)
        {
            String firstName = askForString("fornavn");
            String lastName = askForString("efternavn");
            String address = askForString("address");
            int postalCode = askForInt("postnummer");
            String city = askForString("by");
            String phoneNo = askForString("telefonnummer");
            boolean active = askForBoolean("aktiv");
            EmployeeRole role = askForEnum("rolle", EmployeeRole.values());
            Date birthday = askForDate("fødselsdato");

            try 
            {
                Employee employee = employeeCtrl.createEmployee(loggedInEmployee, firstName, lastName, address, postalCode, city, phoneNo, mail, active, role, birthday);
                employeeMenu(employee);
            }
            catch(MailAlreadyExistsException e)
            {
                System.err.println(e.getMessage());
                pressEnter();
            }
            catch(EmployeeController.IsNotAuthorizedException e)
            {
                System.err.println(e.getMessage());
                pressEnter();
            }
        }
        else
        {
            println("Mail findes allerede i systemet");
            pressEnter();
        }
    }

    /**
     * Method used to find an employee in the system.
     * @return          Returns the employee searched for, if found.
     */
    private void findEmployee()
    {
        Employee employee = null;
        
        println("Brug enten Id eller mail til at søge på en medarbejder");
        String input = getRequiredString();
        if (input.matches("\\d+"))
            employee = employeeCtrl.findEmployee(Integer.parseInt(input));
        else
            employee = employeeCtrl.findEmployee(input);
        
        if (employee == null)
        {
            println("Medarbejder kan ikke findes");
            pressEnter();
        }    
        else
            employeeMenu(employee);
    }

    /**
     * Text based menu used to either update the employee and to list all of the orders of a specific employee.
     * @param employee          Takes the specific searched for employee as parameter.
     */
    private void employeeMenu(Employee employee)
    {
        boolean running = true;
        while(running)
        {
            String[] options = { "Opdater medarbejder", "Vis alle ordrer" };
            int option = printTitledMenu(("Hovedmenu > Medarbejder Menu > Opdater medarbejder: " + employee), options, "Tilbage", employee);
            switch (option)
            {
                case 1:
                    updateEmployee(employee);
                    break;
                case 2:
                    listAllOrders(employee);
                    break;
                case 0:
                default:
                    running = false;
                    break;
            }
        }
    }
    
    /**
     * Method used to update the employee searched for.
     * @param employee          Takes the employee that is to be updated as parameter.
     */
    private void updateEmployee(Employee employee)
    {
        String mail = askForStringWithDefault("e-mail", employee.getMail());
        if (mail.equalsIgnoreCase(employee.getMail()) || employeeCtrl.findEmployee(mail) == null)
        {
            String firstName = askForStringWithDefault("fornavn", employee.getFirstName());
            String lastName = askForStringWithDefault("efternavn", employee.getLastName());
            String address = askForStringWithDefault("adresse", employee.getAddress());
            int postalCode = askForIntWithDefault("postnummer", employee.getPostalCode());
            String city = askForStringWithDefault("by", employee.getCity());
            String phoneNo = askForStringWithDefault("telefonnummer", employee.getPhoneNo());
            boolean active = askForBooleanWithDefault("aktiv", employee.getActive());
            EmployeeRole role = askForEnumWithDefault("rolle", EmployeeRole.values(), employee.getRole());
            Date birthday = askForDateWithDefault("fødselsdato", employee.getBirthday());
            
            try
            {
                employeeCtrl.updateEmployee(employee, firstName, lastName, address, postalCode, city, phoneNo, mail, active, role, birthday);
            }   
            catch(MailAlreadyExistsException e)
            {
                System.err.println(e.getMessage()); 
                pressEnter();
            }
        }
        else
        {
            println("E-mailen eksisterer allerede!");
            pressEnter();
        }
    }
    
    /**
     * Method used to print all the orders of a specific employee.
     * @param employee          Takes the specific employee as parameter.
     * @returns                 Returns a list of the orders created by the specific employee.
     */
    private void listAllOrders(Employee employee)
    {
        for(Order o : employeeCtrl.findOrdersFromEmployee(employee))
            println(o.toString());
        
        if (employeeCtrl.findOrdersFromEmployee(employee).isEmpty())
            println("Der er ingen ordrer for denne medarbejder");
        
        pressEnter();
    }
    
    /**
     * Method used to print all information about a specific employee.
     * @param employee          Takes the specific employee as parameter to find the info printed.
     */
    protected void printInfo(Employee employee)
    {
        println("Id: " + employee.getEmployeeId());
        println("Rolle: " + employee.getRole());
        println("Fornavn: " + employee.getFirstName());
        println("Efternavn: " + employee.getLastName());
        println("Fødselsdato: " + employee.getBirthday());
        println("Adresse: " + employee.getAddress());
        println("Postnummer: " + employee.getPostalCode());
        println("By: " + employee.getCity());
        println("Telefon nummer: " + employee.getPhoneNo());
        println("E-mail: " + employee.getMail());
        println("Aktiv: " + (employee.getActive() ? "Ja" : "Nej"));
    }
}
