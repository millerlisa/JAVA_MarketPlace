package main.com.ecommerce.services;

import main.com.ecommerce.dao.ProductDAO;
import main.com.ecommerce.models.Product;

import java.util.List;

public class ProductService {
    private ProductDAO productDAO = new ProductDAO();

    public void addProduct(Product product) {
        productDAO.addProduct(product);
    }

    public List<Product> getProductsBySeller(int sellerId) {
        return productDAO.getProductsBySeller(sellerId);
    }

    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }

    public List<Product> searchProductsByName(String productName) {
        return productDAO.searchProductsByName(productName);
    }

    public void deleteProductById(int id) {
        productDAO.deleteProductById(id);
    }
}
