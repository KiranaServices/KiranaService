/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kirana.dao;

import com.kirana.model.Product;
import java.util.List;

/**
 *
 * @author nikhilvs
 */
public interface ProductDao {

    public boolean addProduct(Product product) throws Exception;

    public boolean addProductBulk(List<Product> products) throws Exception;

    public Product getProductById(long id) throws Exception;

    public List<Product> getProductList() throws Exception;
    
    public List<Product> getProductListByShopId(long shopId) throws Exception;

    public boolean deleteProduct(long id) throws Exception;

    public boolean updateProduct(Product product) throws Exception;

}
