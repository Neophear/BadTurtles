package modellayer;


/**
 * Enumeration class EmployeeRole - class used to contain the roles for employees.
 *
 * @Jacob
 * @17/05-2018
 */
public enum EmployeeRole
{
    CASHIER("Ekspedient"), SALESMAN("Sælger"), BOSS("Chef"), MANAGEMENT("Leder"), OFFICEWORKER("Kontorarbejder"), EMPLOYEE("Medarbejder");
    
    private String name;
    
    /**
     * Method used to set the name of the role.
     * @param name        Takes the name as parameter to set it.
     */
    private EmployeeRole(String name)
    {
        this.name = name;
    }
    
    /**
     * Method used to return the name of all roles.
     * @return          Returns all of the names created.
     */
    public String toString()
    {
        return name;
    }
}
