/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kirana.services;

import com.kirana.model.Product;
import com.kirana.model.Shop;
import java.io.File;
import java.util.List;

/**
 *
 * @author nikhilvs
 */
public interface ProductServices {

    public boolean addProduct(Product product) throws Exception;

    public Product getProductById(long id) throws Exception;

    public List<Product> getProductList() throws Exception;
    
    public List<Product> getProductListByShopId(long shopId) throws Exception;

    public boolean deleteProduct(long id) throws Exception;
    
    public boolean updateProduct(Product product) throws Exception;
    
    public boolean checkProductExistByName(String name) throws Exception;
    
    public boolean addProductBulk(File productCsv,Shop shop) throws Exception;
    
}
