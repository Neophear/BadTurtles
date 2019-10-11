package tuilayer;
import controllerlayer.OrderController;
import modellayer.*;
import java.util.*;

/**
 * Going through order menu's
 *
 * @author (Rasmus)
 * @version (24-5-2018)
 */
public class OrderUi extends BaseUi<Order>
{
    private OrderController orderCtrl;
    private Employee loggedInEmployee;

    /**
     * Instantiating the different controllers
     * and accepts loggedInEmployee 
     */
    public OrderUi(Employee loggedInEmployee)
    {
        orderCtrl = new OrderController();

        this.loggedInEmployee = loggedInEmployee;
    }

    /**
     * order menu
     */
    public void menu()
    {
        boolean running = true;
        String[] options = { "Opret ordre", "Find Ordre" };
        while(running)
        {
            int chosenOption = printTitledMenu("Hovedmenu > Ordre menu", options, "Tilbage");
            switch(chosenOption)
            {
                case 1:
                createOrder();
                break;

                case 2:
                findOrder();
                break;

                case 0:
                running = false;
                break;
            }
        }
    }

    /**
     * creates an order whether the customer is created or not
     * an order can have one or several products at once
     */
    private void createOrder()
    {
        int customerId = -1;
        Customer customer = null;
        if(orderCtrl.getAllSalesProducts().size() != 0)
        {

            while(customerId != 0 && customer == null)
            {
                customerId = askForInt("kunde id (skriv 0 hvis du ikke er medlem)");
                if(customerId > 0)
                {
                    customer = orderCtrl.findCustomer(customerId);
                    if(customer == null)
                    {
                        println("kunde findes ikke");
                        pressEnter();
                    }
                }
            }
            Order createOrder = orderCtrl.createOrder(customer, loggedInEmployee);
            boolean addProduct = true;
            while(addProduct)
            {
                int productId = askForInt("vare nr");
                SalesProduct salesProduct = orderCtrl.findSalesProduct(productId);
                if(salesProduct != null)
                {
                    createOrder.addSalesProduct(salesProduct);
                    println(salesProduct + " er tilføjet");
                    addProduct = askForBoolean("Tilføj flere vare?");
                }
                else
                {                    
                    println("Produktet findes ikke");
                    pressEnter();
                }

            }
            orderCtrl.saveOrder(createOrder);
        }
        else
        {
            println("Der findes ingen produkter, tilføj nyt produkt først");
            pressEnter();
        }
    }

    /**
     * finds an order and continues to update menu
     */
    private void findOrder()
    {
        int orderId = askForInt("ordre id");
        Order order = orderCtrl.findOrder(orderId);
        if(order == null)
        {
            println("Ordren kan ikke findes");
            pressEnter();
        }
        else
            orderMenu(order);
    }

    /**
     * update order menu
     */
    private void orderMenu(Order order)
    {
        boolean running = true;
        String[] options = { "Hovedmenu > Ordre menu > Opdater ordre status menu" };
        while(running)
        {
            int chosenOption = printTitledMenu("Opdater status", options, "Tilbage", order);
            switch(chosenOption)
            {
                case 1:
                updateOrderStatus(order);
                break;

                case 0:
                running = false;
                break;
            }
        }
    }

    /**
     * updates status on the order
     */
    private void updateOrderStatus(Order order)
    {
        StatusEnum newStatus = askForEnum("status", StatusEnum.values());
        orderCtrl.setStatus(order, newStatus);
    }
    
    private double getOrderTotal(Order order)
    {
        double sum = 0;
        LinkedList<SalesProduct> salesProducts = order.getSalesProducts();
        
        for(SalesProduct salesProduct : salesProducts)
        {
            double discount = 0;
            Discount d = salesProduct.getDiscount(order.getCreatedTime());
            
            if(d != null)
                discount = d.getPercentage();
                
            if (order.getCustomer() != null)
                discount += order.getCustomer().getCustomerGroup().getDiscount();
                
            if(discount > 20)
                discount = 20;
            
            double price = salesProduct.getPrice(order.getCreatedTime());
                
            sum += price * ((100 - discount) / 100);
        }
        
        return sum;
    }

    /**
     * prints an orders info
     */
    protected void printInfo(Order order)
    {
        println("ID: " + order.getOrderId());
        println("Ansat: " + order.getEmployee());
        println("Kunde: " + order.getCustomer());
        println("Produkter: " + order.getSalesProducts());
        println("Status: " + order.getCurrentStatus());
        println("Oprettet: " + getDateFormat().format(order.getCreatedTime()));
        println("Summen for ordren: " + getOrderTotal(order));
    }
}