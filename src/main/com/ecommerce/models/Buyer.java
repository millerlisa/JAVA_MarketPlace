package main.com.ecommerce.models;


import main.com.ecommerce.services.ProductService;
// import main.com.ecommerce.models.Product;

import java.util.List;
import java.util.Scanner;

public class Buyer extends User {
    private ProductService productService;

    // Constructors
    public Buyer() {
        super();
        this.productService = new ProductService();
    }

    public Buyer(String username, String password, String email) {
        super(username, password, email, "buyer");
        this.productService = new ProductService();
    }

    // Method to browse all products
    public void browseAllProducts() {
        List<Product> products = productService.getAllProducts();
        System.out.println("Available Products:");
        for (Product product : products) {
            System.out.println("ID: " + product.getId() +
                               ", Name: " + product.getName() +
                               ", Price: $" + product.getPrice() +
                               ", Quantity: " + product.getQuantity() +
                               ", Seller ID: " + product.getSellerId());
        }
    }

    // Method to search for products by name
    public void searchProductsByName(String productName) {
        List<Product> products = productService.searchProductsByName(productName);
        if (products.isEmpty()) {
            System.out.println("No products found with the name: " + productName);
        } else {
            System.out.println("Search Results:");
            for (Product product : products) {
                System.out.println("ID: " + product.getId() +
                                   ", Name: " + product.getName() +
                                   ", Price: $" + product.getPrice() +
                                   ", Quantity: " + product.getQuantity() +
                                   ", Seller ID: " + product.getSellerId());
            }
        }
    }

    // Method to logout
    public void logout() {
        System.out.println("You have successfully logged out.");
    }

    // Menu for buyer
    public void buyerMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nBuyer Menu:");
            System.out.println("1. Browse All Products");
            System.out.println("2. Search Products by Name");
            System.out.println("3. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    browseAllProducts();
                    break;
                case 2:
                    System.out.print("Enter product name to search: ");
                    String productName = scanner.nextLine();
                    searchProductsByName(productName);
                    break;
                case 3:
                    logout();
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}
