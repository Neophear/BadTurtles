package modellayer;
import java.util.ArrayList;

/**
 * Abstract class BaseContainer - class used to contain all of the other containers.
 *
 * @author (Stiig)
 * @version (17/05-2018)
 */
public abstract class BaseContainer<T>
{
    private ArrayList<T> list;
    
    /**
     * Constructor for the class BaseContainer.
     */
    protected BaseContainer()
    {
        list = new ArrayList<>();
    }
    
    /**
     * Get methods.
     */
    public ArrayList<T> getList() { return list; }
    public T get(int index) { return list.get(index); }
    
    /**
     * Method used to add objects on the list.
     */
    public void add(T object)
    {
        list.add(object);
    }
    
    /**
     * Method used to remove objects from the list.
     */
    public void remove(T object)
    {
        list.remove(object);
    }
    
    /**
     * Method used to return the size of the list.
     */
    public int size()
    {
        return list.size();
    }
    
    /**
     * Method used to clear the list.
     */
    public void clear()
    {
        list.clear();
    }
    
    /**
     * Method used to search for a specific object on the list.
     */
    public boolean contains(T object)
    {
        return list.contains(object);
    }
}