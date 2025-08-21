// OnlineFoodOrdering.java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class OnlineFoodOrdering {
    private static HashMap<Integer, FoodItem> menu = new HashMap<>();
    private static ArrayList<CartItem> cart = new ArrayList<>();
    private static final double TAX_PERCENT = 5.0; // 5% tax

    public static void main(String[] args) {
        populateMenu();
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        System.out.println("=== Welcome to Simple Online Food Ordering System ===");

        while (running) {
            printMainMenu();
            int choice = readInt(sc, "Enter choice: ");

            switch (choice) {
                case 1:
                    displayMenu();
                    break;
                case 2:
                    addToCart(sc);
                    break;
                case 3:
                    removeFromCart(sc);
                    break;
                case 4:
                    viewCart();
                    break;
                case 5:
                    placeOrder(sc);
                    break;
                case 6:
                    running = false;
                    System.out.println("Thank you for visiting. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        sc.close();
    }

    private static void populateMenu() {
        menu.put(1, new FoodItem(1, "Margherita Pizza", 199.00));
        menu.put(2, new FoodItem(2, "Cheese Burger", 149.00));
        menu.put(3, new FoodItem(3, "French Fries", 79.00));
        menu.put(4, new FoodItem(4, "Veg Wrap", 129.00));
        menu.put(5, new FoodItem(5, "Cold Drink (500ml)", 49.00));
        menu.put(6, new FoodItem(6, "Chicken Biryani", 249.00));
        menu.put(7, new FoodItem(7, "Paneer Tikka", 189.00));
    }

    private static void printMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Show Menu");
        System.out.println("2. Add Item to Cart");
        System.out.println("3. Remove Item from Cart");
        System.out.println("4. View Cart");
        System.out.println("5. Place Order");
        System.out.println("6. Exit");
    }

    private static void displayMenu() {
        System.out.println("\n--- Food Menu ---");
        for (FoodItem fi : menu.values()) {
            System.out.println(fi.toString());
        }
    }

    private static void addToCart(Scanner sc) {
        displayMenu();
        int id = readInt(sc, "Enter item ID to add: ");
        if (!menu.containsKey(id)) {
            System.out.println("Invalid item ID.");
            return;
        }
        int qty = readInt(sc, "Enter quantity: ");
        if (qty <= 0) {
            System.out.println("Quantity must be at least 1.");
            return;
        }

        // If item already in cart, increase quantity
        for (CartItem ci : cart) {
            if (ci.getItem().getId() == id) {
                ci.setQuantity(ci.getQuantity() + qty);
                System.out.println("Updated quantity in cart.");
                return;
            }
        }

        cart.add(new CartItem(menu.get(id), qty));
        System.out.println("Item added to cart.");
    }

    private static void removeFromCart(Scanner sc) {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }
        viewCart();
        int id = readInt(sc, "Enter item ID to remove (whole item): ");
        boolean removed = false;
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getItem().getId() == id) {
                cart.remove(i);
                removed = true;
                System.out.println("Item removed from cart.");
                break;
            }
        }
        if (!removed) System.out.println("Item not found in cart.");
    }

    private static void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        System.out.println("\n--- Your Cart ---");
        double subtotal = 0.0;
        for (CartItem ci : cart) {
            System.out.println(ci.toString());
            subtotal += ci.getSubtotal();
        }
        System.out.printf("Subtotal: ₹%.2f%n", subtotal);
        double tax = subtotal * TAX_PERCENT / 100.0;
        System.out.printf("Tax (%.1f%%): ₹%.2f%n", TAX_PERCENT, tax);
        System.out.printf("Grand Total: ₹%.2f%n", subtotal + tax);
    }

    private static void placeOrder(Scanner sc) {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty. Add items before placing an order.");
            return;
        }

        System.out.print("Enter customer name: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) name = "Guest";

        System.out.println("\n--- Order Receipt ---");
        double subtotal = 0.0;
        System.out.println("Customer: " + name);
        System.out.println("-----------------------------------");
        for (CartItem ci : cart) {
            System.out.printf("%s x%d  ₹%.2f%n", ci.getItem().getName(), ci.getQuantity(), ci.getSubtotal());
            subtotal += ci.getSubtotal();
        }
        System.out.println("-----------------------------------");
        double tax = subtotal * TAX_PERCENT / 100.0;
        double grand = subtotal + tax;
        System.out.printf("Subtotal: ₹%.2f%n", subtotal);
        System.out.printf("Tax (%.1f%%): ₹%.2f%n", TAX_PERCENT, tax);
        System.out.printf("Grand Total: ₹%.2f%n", grand);
        System.out.println("Thank you! Your order has been placed.");

        // After placing order, clear cart
        cart.clear();
    }

    // Helper to safely read an int
    private static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextInt()) {
                int x = sc.nextInt();
                sc.nextLine(); // consume newline
                return x;
            } else {
                System.out.println("Please enter a valid number.");
                sc.nextLine(); // discard
            }
        }
    }
}

