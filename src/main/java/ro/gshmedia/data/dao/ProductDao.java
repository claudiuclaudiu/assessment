package ro.gshmedia.data.dao;

import ro.gshmedia.data.model.Product;

public interface ProductDao {

    Product saveProduct(Product product);

    Product updateProduct(Product product);

    Product getProduct(int id);

    Product updateProductById(int quantity, int id);


}
