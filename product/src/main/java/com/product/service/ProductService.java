package com.product.service;

import com.product.entity.Product;
import com.product.utils.Response;

import java.util.List;

/**
 * Created by 14437 on 2017/3/2.
 */
public interface ProductService {
    public Product getProduct(int id);

    public Product getProduct(String name);

    public void addProduct(Product product);

    Response deleteProduct(int id, String shoppingcarUrl, String recordUrl);

    public boolean updateProduct(Product product);

    public List<Product> getProductsByKeyWord(String searchKeyWord);

    public List<Product> getProductsByType(int type);

    public List<Product> getAllProduct();
}
