package ProductCatalogue;

import ProductRecords.Product;
import java.util.*;

public class ProductCatalogue {
    private Map<Product, Integer> inventory = new TreeMap<>();

    public void addOrUpdateProduct(Product product, int quantity) {
        inventory.merge(product, quantity, Integer::sum);
    }

    public int getQuantity(Product product) {
        return inventory.getOrDefault(product, 0);
    }

    public boolean updateQuantity(Product product, int newQuantity) {
        if (inventory.containsKey(product)) {
            inventory.put(product, newQuantity);
            return true;
        }
        return false;
    }

    public boolean removeProduct(Product product) {
        return inventory.remove(product) != null;
    }

    public Set<Product> getAllProducts() {
        return inventory.keySet();
    }

    public Map<Product, Integer> getInventory() {
        return inventory;
    }

    public List<Product> sortProductsByName() {
        List<Product> sorted = new ArrayList<>(inventory.keySet());
        sorted.sort(Comparator.comparing(Product::getProductName));
        return sorted;
    }
}