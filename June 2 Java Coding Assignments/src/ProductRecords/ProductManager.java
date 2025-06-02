package ProductRecords;
import java.util.*;


public class ProductManager {
    private Set<Product> products = new TreeSet<>(); // Automatically sorts & prevents duplicates

    // Add a product (duplicates auto-rejected by TreeSet)
    public void addProduct(Product product) {
        products.add(product);
    }

    // Get product by ID
    public Product getProductById(String productId) {
        for (Product product : products) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null; // Not found
    }

    // Update product (remove old, add new)
    public boolean updateProduct(Product updatedProduct) {
        Product existingProduct = getProductById(updatedProduct.getProductId());
        if (existingProduct != null) {
            products.remove(existingProduct);
            products.add(updatedProduct);
            return true;
        }
        return false; // Product not found
    }

    // Delete product by ID
    public boolean deleteProduct(String productId) {
        Product product = getProductById(productId);
        if (product != null) {
            products.remove(product);
            return true;
        }
        return false; // Product not found
    }

    // Get all products (sorted by ID, since TreeSet uses compareTo)
    public Set<Product> getAllProducts() {
        return products;
    }

    // Sort by name (returns a new sorted list)
    public List<Product> sortProductsByName() {
        List<Product> sortedList = new ArrayList<>(products);
        sortedList.sort(Comparator.comparing(Product::getProductName));
        return sortedList;
    }
}