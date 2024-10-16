package acp.project;

import java.io.FileWriter;
import java.io.IOException;
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

    public void updateProductStockAfterOrder(String productId, int quantity) {
        for (InventoryItem item : inventory) {
            if (item.getProductId().equals(productId)) {
                if (item.getStock() >= quantity) {
                    item.reduceStock(quantity);
                    System.out.println("Stock updated for product: " + item.getProductName());
                } else {
                    System.out.println("Not enough stock for " + item.getProductName() + ". Available: " + item.getStock());
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

    public double orderItems(CustomerManager customerManager) {
        Scanner scanner = new Scanner(System.in);
        double totalBill = 0.0;
        List<MenuItem> orderedItems = new ArrayList<>();

        while (true) {
            System.out.print("Enter Menu Item ID to order (or 'done' to finish): ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("done")) {
                break;
            }

            MenuItem orderedItem = null;
            for (MenuItem item : menu) {
                if (item.getId().equals(input)) {
                    orderedItem = item;
                    break;
                }
            }

            if (orderedItem == null) {
                System.out.println("Invalid item ID.");
                continue;
            }

            System.out.print("Enter quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            inventoryManager.updateProductStockAfterOrder(orderedItem.getId(), quantity);
            orderedItems.add(orderedItem);
            totalBill += orderedItem.getPrice() * quantity;

            System.out.println("Added " + quantity + " x " + orderedItem.getName() + " to your order.");
        }

        System.out.printf("Total Bill: $%.2f%n", totalBill);

        System.out.print("Enter Customer ID: ");
        String customerId = scanner.nextLine();
        Customer customer = customerManager.findCustomer(customerId);

        if (customer == null) {
            System.out.println("New customer! Creating account...");
            customer = new Customer(customerId, totalBill, java.time.LocalDate.now().toString());
            customerManager.addCustomer(customer);
        } else {
            customer.addSpending(totalBill);
        }

        FileManager.writeCustomerData(java.time.LocalDate.now().toString(), customerId, totalBill, orderedItems, inventoryManager.getAvailableItems());

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

class FileManager {
    private static final String FILE_NAME = System.getProperty("user.home") + "/Desktop/customer_sales_data.txt";  

    public static void writeCustomerData(String date, String customerId, double totalSpent, List<MenuItem> orderedItems, List<InventoryItem> inventoryAfterOrder) {
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {  
            // Header for the table
            writer.write("=====================================================================\n");
            writer.write(String.format("%-15s %-15s %-20s %-15s%n", "Date", "Customer ID", "Ordered Item(s)", "Total Spent"));
            writer.write("=====================================================================\n");

            // Write customer order details in table form
            writer.write(String.format("%-15s %-15s %-20s $%-14.2f%n", date, customerId, formatOrderedItems(orderedItems), totalSpent));

            // Header for the inventory table after order
            writer.write("\nInventory Status After Order:\n");
            writer.write("---------------------------------------------------------------------\n");
            writer.write(String.format("%-20s %-15s%n", "Product Name", "Remaining Stock"));
            writer.write("---------------------------------------------------------------------\n");

            // Write remaining stock for each item
            for (InventoryItem item : inventoryAfterOrder) {
                writer.write(String.format("%-20s %-15d%n", item.getProductName(), item.getStock()));
            }

            writer.write("=====================================================================\n\n");
            System.out.println("Customer order details written to file successfully!");

        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    private static String formatOrderedItems(List<MenuItem> orderedItems) {
        StringBuilder sb = new StringBuilder();
        for (MenuItem item : orderedItems) {
            sb.append(item.getName()).append(", ");
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 2);
        }
        return sb.toString();
    }
}



public class CafeManagementSystem {

    private static final InventoryManager inventoryManager = new InventoryManager();
    private static final CustomerManager customerManager = new CustomerManager();

    public static void main(String[] args) {
        MenuManager menuManager = new MenuManager(inventoryManager);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. View Inventory");
            System.out.println("2. Add Product to Inventory");
            System.out.println("3. Update Product in Inventory");
            System.out.println("4. Delete Product from Inventory");
            System.out.println("5. View Menu");
            System.out.println("6. Order Items");
            System.out.println("7. View Customers");
            System.out.println("0. Exit");

            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    inventoryManager.viewInventory();
                    break;
                case 2:
                    addNewProductToInventory(scanner);
                    break;
                case 3:
                    updateProductInInventory(scanner);
                    break;
                case 4:
                    deleteProductFromInventory(scanner);
                    break;
                case 5:
                    menuManager.updateMenuFromInventory();
                    menuManager.viewMenu();
                    break;
                case 6:
                    menuManager.orderItems(customerManager);
                    break;
                case 7:
                    customerManager.viewCustomers();
                    break;
                case 0:
                    System.out.println("Exiting the program...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addNewProductToInventory(Scanner scanner) {
        System.out.print("Enter Product ID: ");
        String productId = scanner.nextLine();
        System.out.print("Enter Product Name: ");
        String productName = scanner.nextLine();
        System.out.print("Enter Stock: ");
        int stock = scanner.nextInt();
        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();  // Consume newline
        System.out.print("Enter Status: ");
        String status = scanner.nextLine();
        System.out.print("Enter Date (YYYY-MM-DD): ");
        String date = scanner.nextLine();

        InventoryItem newItem = new InventoryItem(productId, productName, stock, price, status, date);
        inventoryManager.addProduct(newItem);
    }

    private static void updateProductInInventory(Scanner scanner) {
        System.out.print("Enter Product ID to update: ");
        String productId = scanner.nextLine();
        inventoryManager.updateProduct(productId);
    }

    private static void deleteProductFromInventory(Scanner scanner) {
        System.out.print("Enter Product ID to delete: ");
        String productId = scanner.nextLine();
        inventoryManager.deleteProduct(productId);
    }
}
