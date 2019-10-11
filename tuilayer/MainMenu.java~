package tuilayer;
import tuilayer.*;
import controllerlayer.*;
import modellayer.*;
import java.util.Date;

/**
 * Class used to gather all of the various Ui's and connect them to one.
 *
 * @Jacob
 * @24/05-2018
 */
public class MainMenu extends BaseUi
{
    private EmployeeController employeeCtrl;
    private Employee loggedInEmployee;
    private EmployeeUi employeeUi;
    private CustomerUi customerUi;
    private OrderUi orderUi;
    private SalesProductUi salesProductUi;
    
    /**
     * Constructor for objects of class MainMenu
     */
    public MainMenu()
    {
        employeeCtrl = new EmployeeController();
        
        try
        {
            Employee loggedInEmployee = employeeCtrl.createEmployee(null, "Anders", "Olesen", "Handymanvej 123", 8900, "Aalborg", "12345678", "AndersOlesen@hotmail.dk", true, EmployeeRole.BOSS, new Date(2018,05,23));
        }
        catch (EmployeeController.IsNotAuthorizedException e)
        {
            
        }
        catch (MailAlreadyExistsException e)
        {
            
        }
    }

    /**
     * Text based menu used as a login screen
     */
    public void menu()
    {
        boolean running = true;
        String[] options = { "Login" };
        
        while(running)
        {
            int chosenOption = printTitledMenu("Vestbjerg Byggecenter A/S", options, "Afslut");
            switch (chosenOption)
            {
                case 1:
                    logIn();
                    break;
                case 0:
                    running = false;
                    println("Systemet lukker ned");
                    pressEnter();
                    break;
            }
        }
    }
    
    /**
     * Method used to login into the system.
     */
    private void logIn()
    {
        // println("Indtast medarbejder mail for at logge in");
        // String mail = askForString("mail");
        // loggedInEmployee = employeeCtrl.findEmployee(mail);
        loggedInEmployee = employeeCtrl.findEmployee("andersolesen@hotmail.dk");
        
        if (loggedInEmployee != null)    
            logInMenu();
        else
        {
            println("E-mail ikke fundet i systemet");
            pressEnter();
        }
    }
    
    /**
     * Text based menu used as a menu screen after logging in.
     */
    private void logInMenu()
    {
        employeeUi = new EmployeeUi(loggedInEmployee);
        customerUi = new CustomerUi();
        orderUi = new OrderUi(loggedInEmployee);
        salesProductUi = new SalesProductUi(loggedInEmployee);
        
        boolean running = true;
        String[] options = { "Medarbejder Menu", "Kunde Menu", "Ordre Menu", "Salgsprodukt Menu" };
        while(running)
        {
            int chosenOption = printTitledMenu("Hovedmenu", options, "Log ud");
            switch (chosenOption)
            {
                case 1:
                    employeeUi.menu();
                    break;
                case 2:
                    customerUi.menu();
                    break;
                case 3:
                    orderUi.menu();
                    break;
                case 4:
                    salesProductUi.menu();
                    break;
                case 0:
                    running = false;
                    break;
            }
        }
    }
    
    /**
     * Dummy that isn't used.
     */
    protected void printInfo(Object object)
    {
        
    }
}