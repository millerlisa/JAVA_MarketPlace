package main.com.ecommerce;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static Connection connection;
    private static UserService userService;
    private static ProductService productService;

    public static void main(String[] args) {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ecommerce", "username", "password");
            userService = new UserService(new UserDAO(connection));
            productService = new ProductService(new ProductDAO(connection));

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        register(scanner);
                        break;
                    case 2:
                        login(scanner);
                        break;
                    case 3:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void register(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter role (buyer, seller, admin): ");
        String role = scanner.nextLine();

        User user;
        switch (role) {
            case "buyer":
                user = new Buyer();
                break;
            case "seller":
                user = new Seller();
                break;
            case "admin":
                user = new Admin();
                break;
            default:
                System.out.println("Invalid role");
                return;
        }
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        userService.registerUser(user);
        System.out.println("User registered successfully");
    }

    private static void login(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = userService.authenticateUser(username, password);
        if (user != null) {
            System.out.println("Login successful");
            switch (user.getRole()) {
                case "buyer":
                    showBuyerMenu(scanner, (Buyer) user);
                    break;
                case "seller":
                    showSellerMenu(scanner, (Seller) user);
                    break;
                case "admin":
                    showAdminMenu(scanner, (Admin) user);
                    break;
            }
        } else {
            System.out.println("Invalid username or password");
        }
    }

    private static void showBuyerMenu(Scanner scanner, Buyer buyer) {
        System.out.println("Buyer Menu:");
        System.out.println("1. Browse products");
        // Add more buyer-specific options
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        // Handle buyer menu options
    }

    private static void showSellerMenu(Scanner scanner, Seller seller) {
        System.out.println("Seller Menu:");
        System.out.println("1. Add product");
        System.out.println("2. View my products");
        // Add more seller-specific options
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        switch (choice) {
            case 1:
                addProduct(scanner, seller);
                break;
            case 2:
                viewMyProducts(seller);
                break;
        }
        // Handle seller menu options
    }

    private static void addProduct(Scanner scanner, Seller seller) {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter product price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter product quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setSellerId(seller.getId());

        productService.addProduct(product);
        System.out.println("Product added successfully");
    }

    private static void viewMyProducts(Seller seller) {
        System.out.println("My Products:");
        for (Product product : productService.getProductsBySeller(seller.getId())) {
            System.out.println(product.getName() + " - " + product.getPrice());
        }
    }

    private static void showAdminMenu(Scanner scanner, Admin admin) {
        System.out.println("Admin Menu:");
        System.out.println("1. View all users");
        System.out.println("2. View all products");
        // Add more admin-specific options
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        // Handle admin menu options
    }
}
