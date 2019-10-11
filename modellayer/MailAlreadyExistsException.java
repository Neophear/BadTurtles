package modellayer;


/**
 * Lav en beskrivelse af klassen MailAlreadyExistsException her.
 * 
 * @author (dit navn her)
 * @version (versions nummer eller dato her)
 */
public class MailAlreadyExistsException extends Exception
{

    /**
     * Konstruktør for objekter af klassen MailAlreadyExistsException
     */
    public MailAlreadyExistsException(String mail)
    {
        super("Mailen " + mail + " findes allerede"); 
    }

}
