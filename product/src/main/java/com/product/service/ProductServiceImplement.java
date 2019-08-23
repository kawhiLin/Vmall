package com.product.service;

import com.product.dao.*;
import com.product.entity.Product;
import com.product.utils.Response;
import com.product.utils.HttpUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by 14437 on 2017/3/2.
 */

@Service
public class ProductServiceImplement implements ProductService {
    @Autowired
    private ProductDao productDao;
//    @Autowired
//    private ShoppingRecordDao shoppingRecordDao;
//    @Autowired
//    private ShoppingCarDao shoppingCarDao;
//    @Autowired
//    private EvaluationDao evaluationDao;

    @Override
    public Product getProduct(int id) {
        return productDao.getProduct(id);
    }

    @Override
    public Product getProduct(String name) {
        return productDao.getProduct(name);
    }

    @Override
    public void addProduct(Product product) {
        productDao.addProduct(product);
    }

    @Override
    @Transactional
    public Response deleteProduct(int id, String shoppingcarUrl, String recordUrl) {
        try {
            //evaluationDao.deleteEvaluationByProduct(id);
        	String url1 = shoppingcarUrl+"/deleteEvaluationByProduct?productId=" + id;
     		String result1 = HttpUtil.sendGet(url1);
        	
            //shoppingCarDao.deleteShoppingCarByProduct(id);
        	String url2 = shoppingcarUrl+"/deleteShoppingCarByProduct?productId=" + id;
     		String result2 = HttpUtil.sendGet(url2);
        	
        	
            //shoppingRecordDao.deleteShoppingRecordByProductId(id);
        	String url3 = recordUrl+"/deleteShoppingRecordByProductId?productId=" + id;
     		String result3 = HttpUtil.sendGet(url3);
        
            productDao.deleteProduct(id);
            return new Response(1, "删除商品成功", null);
        }catch (Exception e){
            return new Response(0,"删除商品失败",null);
        }
    }

    @Override
    public boolean updateProduct(Product product) {
        return productDao.updateProduct(product);
    }

    @Override
    public List<Product> getProductsByKeyWord(String searchKeyWord) {
        return productDao.getProductsByKeyWord(searchKeyWord);
    }

    @Override
    public List<Product> getProductsByType(int type) {
        return productDao.getProductsByType(type);
    }

    @Override
    public List<Product> getAllProduct() {
        return productDao.getAllProduct();
    }
}
