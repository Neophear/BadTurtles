package controllerlayer;
import modellayer.*;
import java.util.Date;
import java.util.Calendar;
import java.util.ArrayList;

/**
 * Class EmployeeController used to control classes in the modellayer.
 *
 * @Jacob
 * @24/05-2018
 */
public class EmployeeController
{
    private EmployeeContainer employeeContainer;

    /**
     * Constructor for EmployeeController.
     */
    public EmployeeController()
    {
       employeeContainer = employeeContainer.getInstance();
    }

    /**
     * Method used to create an employee.
     * @param firstName         The first name of the employee.
     * @param lastName          The last name of the employee.
     * @param address           The address of the employee.
     * @param postalCode        The postal code of the employee.
     * @param city              The city of the employee.
     * @param phoneNo           The phone number of the employee.
     * @param mail              The e-mail of the employee.
     * @param active            This boolean is used to set the employee as active or inactive in the system.
     * @param role              The role of the employee.
     * @param birthday          The birthday of the employee.
     * @return employee         Returns the employee created.
     */
    public Employee createEmployee(Employee loggedInEmployee, String firstName, String lastName, String address, int postalCode, String city, String phoneNo, String mail,
        boolean active, EmployeeRole role, Date birthday) throws IsNotAuthorizedException, MailAlreadyExistsException
    {
        if(findEmployee(mail) != null)
            throw new MailAlreadyExistsException(mail);
        
        if(employeeContainer.size() == 0 || loggedInEmployee.getRole() == EmployeeRole.OFFICEWORKER || loggedInEmployee.getRole() == EmployeeRole.BOSS)
        {
            Employee employee = new Employee(firstName, lastName, address, postalCode, city, phoneNo, mail, active, role, birthday);
            employeeContainer.add(employee);
            return employee;
        }
        else
            throw new IsNotAuthorizedException();
    }
    
    /**
     * Method used to find an employee using employeeId.
     * @param employeeId            The Id of the employee.
     * returns employee             Returns the employee found by the Id.
     */
    public Employee findEmployee(int employeeId)
    {
        return employeeContainer.findEmployee(employeeId);
    }

    /**
     * Method used to find an employee using mail.
     * @param mail              The mail of the employee.
     * @returns employee        Returns the employee found 
     */
    public Employee findEmployee(String mail)
    {
        return employeeContainer.findEmployee(mail);
    }
    
    /**
     * Method used to update an employee.
     * @param firstName         The first name of the employee.
     * @param lastName          The last name of the employee.
     * @param address           The address of the employee.
     * @param postalCode        The postal code of the employee.
     * @param city              The city of the employee.
     * @param phoneNo           The phone number of the employee.
     * @param mail              The e-mail of the employee.
     * @param active            This boolean is used to set the employee as active or inactive in the system.
     * @param role              The role of the employee.
     * @param birthday          The birthday of the employee.
     */
    public void updateEmployee(Employee employee, String firstName, String lastName, String address, int postalCode, String city, 
        String phoneNo, String mail, boolean active, EmployeeRole role, Date birthday) throws MailAlreadyExistsException
    {
        if(!mail.equalsIgnoreCase(employee.getMail()) && findEmployee(mail) != null)
            throw new MailAlreadyExistsException(mail);
        
        employee.updateAll(firstName, lastName, address, postalCode, city, phoneNo, mail, active, role, birthday);
    }
    
    /**
     * Class within the controller. It is used to create an exception to check if the authorization of the employee trying to create an employee is correct.
     */
    public class IsNotAuthorizedException extends Exception
    {
        public IsNotAuthorizedException()
        {
            super("Ingen adgang! Kontakt kontoret for hjælp.");
        }
    }
    
    /**
     * Method used to print a list of all employees in the container.
     * @return              Returns all of the employees in the container.
     */
    public ArrayList<Employee> getAll()
    {
        return employeeContainer.getList();
    }
    
    /**
     * Method used to find orders created by specific employees.
     * @param employee              Takes the employee as input.
     * @return                      Returns all of the orders connected with the specific employee.
     */
    public ArrayList<Order> findOrdersFromEmployee(Employee employee)
    {
        return new OrderController().findOrdersFromEmployee(employee);
    }
}


