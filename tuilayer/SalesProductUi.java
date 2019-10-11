package tuilayer;
import controllerlayer.*;
import modellayer.*;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Write a description of class SalesProductUI here.
 *
 * @author (Mathias)
 * @version (25-05-2018)
 */
public class SalesProductUi extends BaseUi<SalesProduct>
{
    private SalesProductController salesProductCtrl;
    private Employee loggedInEmployee;
    private NumberFormat formatter;

    /**
     * @param loggedInEmployee     The Employee that is logged in.
     * Constructor for objects of class SalesProductUI
     */
    public SalesProductUi(Employee loggedInEmployee)
    {
        this.loggedInEmployee =  loggedInEmployee;
        this.salesProductCtrl = new SalesProductController();
        this.formatter = new DecimalFormat("#0.00 kr");
    }

    /**
     * the main menu that runs everything
     */

    public void menu()
    {
        boolean running = true;
        String[] options = { "Opret produkt", "Find produkt", "Liste over produkter" };
        while(running)
        {
            int chosenOption = printTitledMenu("Hovedmenu > Produkt Menu", options, "Tilbage");
            switch(chosenOption)
            {
                case 1:  
                    createSalesProduct();
                    break;
                case 2:
                    clearScr();
                    findSalesProduct();
                    break;
                case 3: 
                    printAllShort(salesProductCtrl.getAll());
                    break;
                case 0:
                    running = false;
                    clearScr();
                    break;
            }

        }
    }

    // /**
     // * Finds all salesProducts all loops around them
     // */

    // private void findAllSalesProducts()
    // {
        // ArrayList<SalesProduct> products = salesProductController.getAll();

        // for(SalesProduct salesproduct : products)
        // {
            // println("Produkt Id : " + salesproduct.getSalesProductId());
            // println("Produktnavn : " + salesproduct.getName());
            // println("----------------------------------------------------------------");
        // }

        // pressEnter();
    // }

    /**
     * createSalesProduct asks for infomation from the user and sends infomation to the salesProductController 
     */

    private void createSalesProduct()
    {
        clearScr();
        String name = askForString("navn");
        String location = askForString("hyldenummer på lager"); 
        int countInStock = askForInt("antal på lager");
        int mininmumStock = askForInt("minimum antal på lager");
        int maximumStock = askForInt("maximum antal på lager");
        while(mininmumStock > maximumStock)
        {
            mininmumStock = askForInt("minimum antal på lager(skal være mindre end maximum)");
            maximumStock = askForInt("maximum antal på lager(skal være større end minimum)");
            if(mininmumStock > maximumStock)
                println("minimum skal være mindre end maximum");

        }

        int reorderAmount = askForInt("genbestillings kvote");

        try
        {
            SalesProduct newSalesProduct = salesProductCtrl.createSalesProduct(name, location, countInStock, mininmumStock, maximumStock, reorderAmount);
            salesProductMenu(newSalesProduct);
        }
        catch(SalesProductController.maxMinStockDoesNotMatchException e)
        {
            println(e.getMessage());
            pressEnter();
        }
    }

    /**
     * findSalesProduct takes a productId and then sends infomation back the the salesProductController and the modellayer where it checks if the salesProduct exits
     * If the salesProduct is not found you will get options to go back or to try again
     */
    private void findSalesProduct()
    {
        boolean running = true;
        while(running)
        {
            int salesProductId = askForInt("produkt id");
            SalesProduct newSalesProduct = salesProductCtrl.findSalesProduct(salesProductId);
            if(newSalesProduct == null)
            {
                String[] options = {"Prøv igen"};
                int option = printTitledMenu("Produktet blev ikke fundet!" , options, "Tilbage til hovedmenuen");
                switch(option)
                {
                    case 1:
                    break;
                    case 0: 
                    running = false;
                    break;
                }
            }
            else
            {
                salesProductMenu(newSalesProduct);
                running = false;
            }
        }
    }

    /**
     * @param salesProduct     The SalesProduct that is parse through.
     * Finds all discounts on a SalesProduct
     */
    private void findAllDiscounts(SalesProduct salesProduct)
    {
        LinkedList<Discount> discounts = salesProductCtrl.findAllDiscounts(salesProduct);
        for(Discount discount : discounts)
        {
            println("Start dato : " + discount.getTimeFrom());
            println("Slut dato : " + discount.getTimeTo());
            println("Tilbud: " + discount.getPercentage() + "%");
            println("----------------------------------------------------------------");
        }
        pressEnter();
    }

    /**
     * @param salesProduct     The SalesProduct that is parse through.
     * Finds all prices on a product
     */
    private void findAllPrices(SalesProduct salesProduct)
    {
        LinkedList<SalesProductPrice> prices = salesProductCtrl.findAllSalesProductPrices(salesProduct);
        for(SalesProductPrice salesProductPrice : prices)
        {
            println("Start dato for prisen : " + salesProductPrice.getDate());
            println("Pris: " + formatter.format(salesProductPrice.getPrice()));
            println("----------------------------------------------------------------");
        }
        pressEnter();
    }

    /**
     * @param salesProduct     The SalesProduct that is parse through.
     * updateSalesProduct by taking infomation about the salesProduct and sends it to the SalesProductController that sends the infomation to SalesProduct
     * If the user just presses enter just takes the default value
     */
    private void updateSalesProduct(SalesProduct salesProduct)
    {
        SalesProduct partOf = null;
        String name = askForStringWithDefault("nyt produkt navn", salesProduct.getName());
        String location = askForStringWithDefault("hyldenummer på lager", salesProduct.getLocation());
        int countInStock = askForIntWithDefault("antal på lager", salesProduct.getCountInStock());
        int mininmumStock = askForIntWithDefault("minimum antal på lager", salesProduct.getMininmumStock());
        int maximumStock = askForIntWithDefault("maximum antal på lager", salesProduct.getMaximumStock());
        
        while(mininmumStock > maximumStock)
        {
            mininmumStock = askForIntWithDefault("minimum antal på lager(skal være mindre end maximum)", salesProduct.getMininmumStock());
            maximumStock = askForIntWithDefault("maximum antal på lager(skal være større end minimum)", salesProduct.getMaximumStock());
            if(mininmumStock > maximumStock)
                println("minimum skal være mindre end maximum");

        }
        
        int reorderAmount = askForIntWithDefault("genbestillings kvote", salesProduct.getReorderAmount());
        boolean isPartOf = askForConfirmation("Er dette produkt en del af et andet produkt?");

        if(isPartOf)
        {
            int salesProductId = askForInt("produkt id på det produkt det er en del af");
            partOf = salesProductCtrl.findSalesProduct(salesProductId);
            
            if(partOf == null)
            {
                println("Produktet findes ikke og bliver derfor ikke sat");
                pressEnter();
            }
        }
        
        salesProductCtrl.updateSalesProduct(salesProduct, name, location, countInStock, mininmumStock, maximumStock, reorderAmount, partOf);
    }

    /**
     * @param loggedInEmployee     The Employee that is logged in.
     * @param salesProduct     The SalesProduct that is parse through.
     * addPrice adds a price to a product by taking date and and a double price
     */
    private void addPrice(SalesProduct salesProduct)
    {
        Date timeFrom = askForDate("start dato for prisen");
        int price = askForInt("pris");
        
        try
        {
            salesProductCtrl.addSalesProductPrice(loggedInEmployee, salesProduct, timeFrom, price);
        }
        catch(SalesProductController.IsNotAuthorizedException e)
        {
            println(e.getMessage());
            pressEnter();
        }

    }

    /**
     * @param salesProduct     The SalesProduct that is parse through.
     * findPrice finds the price that is closest to the date the user enteres
     */
    private void findPrice(SalesProduct salesProduct)
    {
        Date date = askForDate("dato for prisen");
        double price = salesProductCtrl.findSalesProductPrice(salesProduct, date);
        println("Den aktive pris er for denne dato er: " + price);
        findDiscountForPriceAndDiscount(salesProduct, date);
    }

    /**
     * @param salesProduct     The SalesProduct that is parse through.
     * findDiscount finds the discount that is closest to the date the users enters
     */
    private void findDiscount(SalesProduct salesProduct)
    {
        Date date = askForDate("Start dato for tiluddet");
        findDiscountForPriceAndDiscount(salesProduct, date);
    }

    /**
     * @param salesProduct     The SalesProduct that is parse through.
     * @param date             The date that is needed to find discounts
     * findDiscount finds the discount that is closest to the date the users enters
     */
    private Discount findDiscountForPriceAndDiscount(SalesProduct salesProduct, Date date)
    {
        Discount discount = salesProductCtrl.findDiscount(salesProduct, date);
        if(discount != null)
        {
            println("Start dato for tilbud: " + discount.getTimeFrom());
            println("Slut dato for tilbud: " + discount.getTimeTo());
            println("Slut dato for tilbud: " + discount.getPercentage() + "%");
        }
        pressEnter();

        return discount; 
    }

    /**
     * @param salesProduct     The SalesProduct that is parse through.
     * addDiscount adds a discount to the salesProduct 
     */
    private void addDiscount(SalesProduct salesProduct)
    {
        Date timeFrom = askForDate("start dato");
        Date timeTo = askForDate("slut dato");
        double percentage = askForInt("rabat (procent)");
        
        try
        {
            salesProductCtrl.addDiscount(salesProduct, timeFrom, timeTo, percentage);
        }
        catch(SalesProduct.datesOverlapException e)
        {
            println(e.getMessage());
            pressEnter();
        }
    }

    /**
     * @param salesProduct     The SalesProduct that is parse through.
     * SalesProductMenu is the menu for the salesProducts 
     */
    private void salesProductMenu(SalesProduct salesProduct)
    {
        boolean exit = false;

        while(!exit)
        {
            String[] options = {"Opdater produkt", "Tilføj pris", "Find pris på given dato",  "Pris historik på vare", "Tilføj tilbud", "Find tilbud på given dato", "Tilbuds historik"};
            int option = printTitledMenu(("Hovedmenu > Produkt Menu > Opdater produkt: " + salesProduct), options, "Tilbage", salesProduct);
            
            switch(option)
            {
                case 1:
                    updateSalesProduct(salesProduct);
                    break;
                case 2: 
                    addPrice(salesProduct);
                    break;
                case 3: 
                    findPrice(salesProduct);
                    break;
                case 4: 
                    findAllPrices(salesProduct);
                    break;
                case 5:
                    addDiscount(salesProduct);
                    break;
                case 6:
                    findDiscount(salesProduct);
                    break;
                case 7:
                    findAllDiscounts(salesProduct);
                    break;
                case 0: 
                    exit = true;
                    break;
            }
        }
    }
    
    /**
     * @param salesProduct     The SalesProduct that gets printed.
     * printInfo findes infomation salesProduct infomation
     */
    protected void printInfo(SalesProduct salesProduct)
    {
        println("Produkt Id: " + salesProduct.getSalesProductId());
        println("Produkt navn: " + salesProduct.getName());
        println("Produktet findes på lager: " + salesProduct.getLocation());
        println("Hyldenummer på lager: " + salesProduct.getCountInStock());
        println("Minimum antal på lager: " + salesProduct.getMininmumStock());
        println("Maximum antal på lager: " + salesProduct.getMaximumStock());
        println("Genbestillings kvote: " + salesProduct.getReorderAmount());

        if(salesProduct.getPrice(Calendar.getInstance().getTime()) == 0)
            println("Nuværende pris: Der er ingen nuværende pris");
        else
            println("Nuværende pris: " + formatter.format(salesProduct.getPrice(Calendar.getInstance().getTime())));

        if(salesProduct.getPartOf() != null)
        {
            println("-----------------");
            println("Dette produkt er en del af produktet: " + salesProduct.getPartOf().getName() + "\r"  +"Med produkt ID: " + salesProduct.getPartOf().getSalesProductId());
        }
    }
}