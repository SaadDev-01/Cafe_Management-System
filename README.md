# Cafe Management System

The Cafe Management System is a command-line Java application designed to manage cafe operations efficiently. It covers key functionalities including inventory management, menu management, and customer management, allowing cafe administrators to handle various tasks seamlessly.

## Overview

This system is structured into several key classes, each responsible for specific functionalities:

### Key Classes

1. **InventoryItem**
   - Represents an item in the inventory with properties such as product ID, name, stock quantity, price, status, and date.
   - Methods include adding, updating, and deleting inventory items, as well as reducing stock when an order is placed.

2. **MenuItem**
   - Represents an item in the cafe menu, containing the item's ID, name, and price.

3. **Customer**
   - Contains customer details, including customer ID, total spent, and date of their last transaction.
   - Allows tracking of customer spending and managing new customers.

4. **InventoryManager**
   - Manages inventory operations:
     - Adding new products
     - Updating existing product information
     - Deleting products
     - Viewing current inventory

5. **MenuManager**
   - Manages menu operations based on available inventory:
     - Updates the menu with available items
     - Processes customer orders and calculates total bills

6. **CustomerManager**
   - Handles customer-related functionalities:
     - Adding new customers
     - Finding customers based on their ID
     - Viewing customer details

7. **CafeManagementSystem**
   - The main class that runs the application.
   - Handles user authentication and displays the main menu with options for inventory, menu, and customer management.

## How It Works

1. **Login**: The application starts by prompting the user to enter their username and password. Only the admin user can access the system using the predefined credentials.

2. **Main Menu**: Once logged in, users can select options from the main menu:
   - **Inventory Management**: View, add, update, or delete products from the inventory.
   - **Menu Management**: View available menu items and place orders, which will update inventory stock.
   - **Customer Management**: View customer spending history and manage customer records.

3. **Inventory Operations**: 
   - Users can add new products by entering their details.
   - Existing products can be updated by selecting them and modifying their attributes.
   - Products can be deleted if they are no longer needed.

4. **Menu Operations**: 
   - The menu reflects the current inventory. Only items in stock will be available for ordering.
   - When an order is placed, the system updates the total bill and the stock of the ordered items.

5. **Customer Management**: 
   - Users can view existing customers or add new ones. 
   - Spending is tracked, and customers can be updated with their new spending information.

## Conclusion

The Cafe Management System is a comprehensive solution for managing a cafe's operations. Its structure allows for easy modification and expansion, making it suitable for various business needs.
