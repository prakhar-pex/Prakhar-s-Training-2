package ProductRecords;

public class Main {
    public static void main(String[] args) {
        ProductManager manager = new ProductManager();

        // Adding products
        manager.addProduct(new Product("P1", "Laptop", "Electronics", 999.99));
        manager.addProduct(new Product("P2", "Smartphone", "Electronics", 599.99));
        manager.addProduct(new Product("P3", "Headphones", "Accessories", 99.99));

        // Try adding duplicate (will be ignored)
        manager.addProduct(new Product("P1", "Gaming Laptop", "Electronics", 1299.99));

        // Get all products (sorted by ID)
        System.out.println("All Products (Sorted by ID):");
        manager.getAllProducts().forEach(System.out::println);

        // Sort by name
        System.out.println("\nProducts Sorted by Name:");
        manager.sortProductsByName().forEach(System.out::println);

        // Update a product
        Product updatedPhone = new Product("P2", "Smartphone Pro", "Electronics", 699.99);
        manager.updateProduct(updatedPhone);

        // Delete a product
        manager.deleteProduct("P3");

        // Display after changes
        System.out.println("\nAfter Update & Delete:");
        manager.getAllProducts().forEach(System.out::println);
    }
}