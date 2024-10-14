/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package acp.project;
import java.util.*;
/**
 *
 * @author SAAD
 */
class InventoryItem {
    private String productId;
    private String productName;
    private int stock;
    private double price;
    private String status;
    private String date;

    
    public InventoryItem(String productId, String productName, int stock, double price, String status, String date) {
        this.productId = productId;
        this.productName = productName;
        this.stock = stock;
        this.price = price;
        this.status = status;
        this.date = date;
    }

    
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void reduceStock(int amount) {
        this.stock -= amount;
    }
}


class MenuItem {
    private String id;
    private String name;
    private double price;


    public MenuItem(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class Customer {
    private String customerId;
    private double totalSpent;
    private String date;

    public Customer(String customerId, double totalSpent, String date) {
        this.customerId = customerId;
        this.totalSpent = totalSpent;
        this.date = date;
    }

    public String getCustomerId() {
        return customerId;
    }

    public double getTotalSpent() {
        return totalSpent;
    }

    public void addSpending(double amount) {
        this.totalSpent += amount;
    }

    public String getDate() {
        return date;
    }
}

class InventoryManager {
    private List<InventoryItem> inventory = new ArrayList<>();

    public void addProduct(InventoryItem item) {
        inventory.add(item);
        System.out.println("Product added successfully!");
    }

    public void updateProduct(String productId) {
        for (InventoryItem item : inventory) {
            if (item.getProductId().equals(productId)) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter new Product Name: ");
                item.setProductName(scanner.nextLine());
                System.out.print("Enter new Stock: ");
                item.setStock(scanner.nextInt());
                System.out.print("Enter new Price: ");
                item.setPrice(scanner.nextDouble());
                scanner.nextLine();
                System.out.print("Enter new Status: ");
                item.setStatus(scanner.nextLine());
                System.out.print("Enter new Date (YYYY-MM-DD): ");
                item.setDate(scanner.nextLine());
                System.out.println("Product updated successfully!");
                return;
            }
        }
        System.out.println("Product not found!");
    }

    public void deleteProduct(String productId) {
        inventory.removeIf(item -> item.getProductId().equals(productId));
        System.out.println("Product deleted successfully (if it existed)!");
    }

    public void viewInventory() {
        System.out.printf("%-12s %-15s %-10s %-10s %-10s %-10s%n", 
                          "Product ID", "Product Name", "Stock", "Price", "Status", "Date");
        for (InventoryItem item : inventory) {
            System.out.printf("%-12s %-15s %-10d $%-9.2f %-10s %-10s%n", 
                              item.getProductId(), item.getProductName(), item.getStock(),
                              item.getPrice(), item.getStatus(), item.getDate());
        }
    }

    public List<InventoryItem> getAvailableItems() {
        List<InventoryItem> availableItems = new ArrayList<>();
        for (InventoryItem item : inventory) {
            if (item.getStock() > 0) {
                availableItems.add(item);
            }
        }
        return availableItems;
    }

    public void updateProductStockAfterOrder(String productId) {
        for (InventoryItem item : inventory) {
            if (item.getProductId().equals(productId)) {
                if (item.getStock() > 0) {
                    item.reduceStock(1); 
                    System.out.println("Stock updated for product: " + item.getProductName());
                } else {
                    System.out.println("Stock for " + item.getProductName() + " is empty!");
                }
                return;
            }
        }
        System.out.println("Product not found in inventory!");
    }
}

class MenuManager {
    private List<MenuItem> menu = new ArrayList<>();
    private InventoryManager inventoryManager;

    public MenuManager(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
        updateMenuFromInventory();
    }

    public void updateMenuFromInventory() {
        menu.clear();
        for (InventoryItem item : inventoryManager.getAvailableItems()) {
            menu.add(new MenuItem(item.getProductId(), item.getProductName(), item.getPrice()));
        }
    }

    public void viewMenu() {
        System.out.printf("%-10s %-15s %-10s%n", "ID", "Item Name", "Price");
        for (MenuItem item : menu) {
            System.out.printf("%-10s %-15s $%-9.2f%n", item.getId(), item.getName(), item.getPrice());
        }
    }

    public double orderItems() {
        Scanner scanner = new Scanner(System.in);
        double totalBill = 0.0;
        while (true) {
            System.out.print("Enter Menu Item ID to order (or 'done' to finish): ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("done")) {
                break;
            }
            for (MenuItem item : menu) {
                if (item.getId().equals(input)) {
                    totalBill += item.getPrice();
                    System.out.println("Added " + item.getName() + " to your order.");
                    inventoryManager.updateProductStockAfterOrder(item.getId());
                    break;
                }
            }
        }
        System.out.printf("Total Bill: $%.2f%n", totalBill);
        return totalBill;
    }
}


class CustomerManager {
    private List<Customer> customers = new ArrayList<>();

    public void addCustomer(Customer customer) {
        customers.add(customer);
        System.out.println("Customer added successfully!");
    }

    public Customer findCustomer(String customerId) {
        for (Customer customer : customers) {
            if (customer.getCustomerId().equals(customerId)) {
                return customer;
            }
        }
        return null;
    }

    public void viewCustomers() {
        System.out.printf("%-12s %-10s %-10s%n", "Customer ID", "Total Spent", "Date");
        for (Customer customer : customers) {
            System.out.printf("%-12s $%-9.2f %-10s%n", customer.getCustomerId(), customer.getTotalSpent(), customer.getDate());
        }
    }
}


public class CafeManagementSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "password";

    private static InventoryManager inventoryManager = new InventoryManager();
    private static MenuManager menuManager = new MenuManager(inventoryManager);
    private static CustomerManager customerManager = new CustomerManager();
    private static double totalSales = 0.0;

    public static void main(String[] args) {
        login();
        mainMenu();
    }

    private static void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (!username.equals(ADMIN_USERNAME) || !password.equals(ADMIN_PASSWORD)) {
            System.out.println("Invalid credentials. Exiting...");
            System.exit(0);
        }
    }

    private static void mainMenu() {
        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Inventory Management");
            System.out.println("2. Menu Management");
            System.out.println("3. Customer Management");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    inventoryManagement();
                    break;
                case 2:
                    menuManagement();
                    break;
                case 3:
                    customerManagement();
                    break;
                case 4:
                    System.out.printf("Total sales: $%.2f%n", totalSales);
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void inventoryManagement() {
        while (true) {
            System.out.println("\nInventory Management:");
            System.out.println("1. View Inventory");
            System.out.println("2. Add Product");
            System.out.println("3. Update Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Back to Main Menu");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    inventoryManager.viewInventory();
                    break;
                case 2:
                    addProduct();
                    break;
                case 3:
                    System.out.print("Enter Product ID to update: ");
                    String productId = scanner.nextLine();
                    inventoryManager.updateProduct(productId);
                    break;
                case 4:
                    System.out.print("Enter Product ID to delete: ");
                    String productIdToDelete = scanner.nextLine();
                    inventoryManager.deleteProduct(productIdToDelete);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addProduct() {
        System.out.print("Enter Product ID: ");
        String productId = scanner.nextLine();
        System.out.print("Enter Product Name: ");
        String productName = scanner.nextLine();
        System.out.print("Enter Stock: ");
        int stock = scanner.nextInt();
        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter Status: ");
        String status = scanner.nextLine();
        System.out.print("Enter Date (YYYY-MM-DD): ");
        String date = scanner.nextLine();

        inventoryManager.addProduct(new InventoryItem(productId, productName, stock, price, status, date));
        menuManager.updateMenuFromInventory();
    }

    private static void menuManagement() {
        while (true) {
            System.out.println("\nMenu Management:");
            System.out.println("1. View Menu");
            System.out.println("2. Order Items");
            System.out.println("3. Back to Main Menu");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    menuManager.viewMenu();
                    break;
                case 2:
                    double bill = menuManager.orderItems();
                    totalSales += bill;
                    System.out.print("Enter Customer ID: ");
                    String customerId = scanner.nextLine();
                    Customer customer = customerManager.findCustomer(customerId);
                    if (customer == null) {
                        System.out.println("New customer! Creating account...");
                        customerManager.addCustomer(new Customer(customerId, bill, java.time.LocalDate.now().toString()));
                    } else {
                        customer.addSpending(bill);
                    }
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void customerManagement() {
        while (true) {
            System.out.println("\nCustomer Management:");
            System.out.println("1. View Customers");
            System.out.println("2. Back to Main Menu");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    customerManager.viewCustomers();
                    break;
                case 2:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
