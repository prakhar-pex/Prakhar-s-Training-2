package ProductRecords;

import java.util.Objects;

public class Product implements Comparable<Product> {
    private String productId;
    private String productName;
    private String category;
    private double price;

    // Constructor
    public Product(String productId, String productName, String category, double price) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.price = price;
    }

    // Getters (Setters can be added if needed)
    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    // Override equals() & hashCode() to avoid duplicates (based on productId)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productId.equals(product.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }

    // Natural ordering (sort by productId)
    @Override
    public int compareTo(Product other) {
        return this.productId.compareTo(other.productId);
    }

    // For better printing
    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                '}';
    }
}