package ProductCatalogue;

import ProductCatalogue.ProductCatalogue;
import ProductRecords.Product;

public class Main {
    public static void main(String[] args) {
        ProductCatalogue catalogue = new ProductCatalogue();

        // Create products
        Product laptop = new Product("P1", "Laptop", "Electronics", 999.99);
        Product phone = new Product("P2", "Smartphone", "Electronics", 599.99);
        Product headphones = new Product("P3", "Headphones", "Accessories", 99.99);

        // Add to catalogue
        catalogue.addOrUpdateProduct(laptop, 10);
        catalogue.addOrUpdateProduct(phone, 5);
        catalogue.addOrUpdateProduct(headphones, 20);

        // Update quantity
        catalogue.addOrUpdateProduct(laptop, 5); // Now 15 laptops

        // Display inventory
        System.out.println("Current Inventory:");
        catalogue.getInventory().forEach((p, q) ->
                System.out.println(p + " | Qty: " + q));

        // Sort by name
        System.out.println("\nSorted by Name:");
        catalogue.sortProductsByName().forEach(System.out::println);

        // Update and remove
        catalogue.updateQuantity(phone, 8);
        catalogue.removeProduct(headphones);

        System.out.println("\nAfter Updates:");
        catalogue.getInventory().forEach((p, q) ->
                System.out.println(p + " | Qty: " + q));
    }
}