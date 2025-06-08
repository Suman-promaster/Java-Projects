import java.util.ArrayList;
import java.util.Scanner;

// Class representing a Menu Item
class MenuItem {
    private int id;
    private String name;
    private double price;

    public MenuItem(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return id + ". " + name + " - $" + price;
    }
}

// Class representing an Order
class Order {
    private ArrayList<MenuItem> items;
    private double totalAmount;

    public Order() {
        items = new ArrayList<>();
        totalAmount = 0.0;
    }

    public void addItem(MenuItem item) {
        items.add(item);
        totalAmount += item.getPrice();
    }

    public void displayOrder() {
        System.out.println("Your Order:");
        for (MenuItem item : items) {
            System.out.println(item.getName() + " - $" + item.getPrice());
        }
        System.out.printf("Total: $%.2f%n", totalAmount);
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    // Apply discount logic via helper method
    public void applyDiscount() {
        totalAmount = RestaurantManagementSystem.applyDiscount(totalAmount);
    }

    // Method to generate a detailed receipt
    public void generateReceipt() {
        System.out.println("===== Receipt =====");
        for (MenuItem item : items) {
            System.out.printf("%-20s $%.2f%n", item.getName(), item.getPrice());
        }
        System.out.printf("Total: $%.2f%n", totalAmount);
        System.out.println("===================");
    }
}

// Main Class for Restaurant Management System
public class RestaurantManagementSystem {

    private static ArrayList<MenuItem> menu = new ArrayList<>();
    private static ArrayList<Order> orders = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeMenu();

        int choice;
        do {
            System.out.println("\n===== Restaurant Management System =====");
            System.out.println("1. Admin Login");
            System.out.println("2. Customer Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = validateIntegerInput();

            switch (choice) {
                case 1:
                    adminMenu();
                    break;
                case 2:
                    customerMenu();
                    break;
                case 3:
                    System.out.println("Thank you for using the Restaurant Management System!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 3);
    }

    // Admin Menu
    private static void adminMenu() {
        int adminChoice;
        do {
            System.out.println("\n===== Admin Menu =====");
            System.out.println("1. Add Menu Item");
            System.out.println("2. Remove Menu Item");
            System.out.println("3. Update Menu Item");
            System.out.println("4. Display Menu");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");
            adminChoice = validateIntegerInput();

            switch (adminChoice) {
                case 1:
                    addMenuItem();
                    break;
                case 2:
                    removeMenuItem();
                    break;
                case 3:
                    updateMenuItem();
                    break;
                case 4:
                    displayMenu();
                    break;
                case 5:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (adminChoice != 5);
    }

    // Customer Menu
    private static void customerMenu() {
        int customerChoice;
        do {
            System.out.println("\n===== Customer Menu =====");
            System.out.println("1. Display Menu");
            System.out.println("2. Place Order");
            System.out.println("3. View All Orders");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");
            customerChoice = validateIntegerInput();

            switch (customerChoice) {
                case 1:
                    displayMenu();
                    break;
                case 2:
                    takeOrder();
                    break;
                case 3:
                    viewOrders();
                    break;
                case 4:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (customerChoice != 4);
    }

    // Initialize the menu with some default items
    private static void initializeMenu() {
        menu.add(new MenuItem(1, "Pizza", 12.99));
        menu.add(new MenuItem(2, "Burger", 8.99));
        menu.add(new MenuItem(3, "Pasta", 10.99));
        menu.add(new MenuItem(4, "Salad", 6.99));
        menu.add(new MenuItem(5, "Soda", 2.99));
    }

    // Add a new menu item
    private static void addMenuItem() {
        System.out.print("Enter item name: ");
        String name = scanner.next();
        System.out.print("Enter item price: ");
        double price = scanner.nextDouble();
        int id = menu.size() + 1;
        menu.add(new MenuItem(id, name, price));
        System.out.println("Item added successfully!");
    }

    // Remove a menu item
    private static void removeMenuItem() {
        displayMenu();
        System.out.print("Enter the ID of the item to remove: ");
        int id = validateIntegerInput();
        if (id > 0 && id <= menu.size()) {
            menu.remove(id - 1);
            System.out.println("Item removed successfully!");
        } else {
            System.out.println("Invalid item ID.");
        }
    }

    // Update a menu item
    private static void updateMenuItem() {
        displayMenu();
        System.out.print("Enter the ID of the item to update: ");
        int id = validateIntegerInput();
        if (id > 0 && id <= menu.size()) {
            MenuItem item = menu.get(id - 1);
            System.out.print("Enter new name (current: " + item.getName() + "): ");
            String name = scanner.next();
            System.out.print("Enter new price (current: $" + item.getPrice() + "): ");
            double price = scanner.nextDouble();
            menu.set(id - 1, new MenuItem(id, name, price));
            System.out.println("Item updated successfully!");
        } else {
            System.out.println("Invalid item ID.");
        }
    }

    // Display the menu to the user
    private static void displayMenu() {
        System.out.println("\n===== Menu =====");
        for (MenuItem item : menu) {
            System.out.println(item);
        }
    }

    // Take an order from the user
    private static void takeOrder() {
        Order order = new Order();
        int itemId;
        do {
            displayMenu();
            System.out.print("Enter the ID of the item to add to your order (0 to finish): ");
            itemId = validateIntegerInput();

            if (itemId > 0 && itemId <= menu.size()) {
                MenuItem selectedItem = menu.get(itemId - 1);
                order.addItem(selectedItem);
                System.out.println(selectedItem.getName() + " added to your order.");
            } else if (itemId != 0) {
                System.out.println("Invalid item ID. Please try again.");
            }
        } while (itemId != 0);

        if (order.getTotalAmount() > 0) {
            order.applyDiscount();
            order.displayOrder();
            orders.add(order);
            System.out.println("Order placed successfully!");
        } else {
            System.out.println("No items in the order. Order cancelled.");
        }
    }

    // View all orders and their details
    private static void viewOrders() {
        if (orders.isEmpty()) {
            System.out.println("\nNo orders have been placed yet.");
        } else {
            System.out.println("\n===== All Orders =====");
            int orderCount = 1;
            for (Order order : orders) {
                System.out.println("Order #" + orderCount);
                order.generateReceipt();
                System.out.println("----------------------");
                orderCount++;
            }
        }
    }

    // Method to apply discount logic
    public static double applyDiscount(double totalAmount) {
        if (totalAmount > 50) {
            System.out.println("Discount applied: 10%");
            return totalAmount * 0.9;
        }
        return totalAmount;
    }

    // Validate integer input
    private static int validateIntegerInput() {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }
}
