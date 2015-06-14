/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kirana.services;

import au.com.bytecode.opencsv.CSVReader;
import com.kirana.dao.ProductDao;
import com.kirana.model.Product;
import com.kirana.model.Shop;
import com.kirana.utils.ParameterException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author nikhilvs
 */
public class ProductServicesImpl implements ProductServices{
    
    private static final Logger log = Logger.getLogger(ProductServicesImpl.class);
    
    @Autowired
    ProductDao productDao;
    
//    @Autowired
//    ProductDao entitydao;
    
    /**
     *
     * @param shop
     * @return
     * @throws Exception
     */
        
    @Override
    public boolean addProduct(Product shop) throws Exception {
        return productDao.addProduct(shop);
    }
    
    
    @Override
    public boolean addProductBulk(File productCsv,Shop shop) throws Exception {
        
        CSVReader reader = new CSVReader(new FileReader(productCsv));
        String[] productLine;
        List<Product> list ;
        list = new ArrayList<>();
        try{
            reader.readNext(); // skip the header part  
            while ((productLine = reader.readNext()) != null ) {
            list.add(new Product(productLine[0],productLine[1],productLine[2],productLine[3],productLine[4],shop));
            log.info(productLine[0] + ":" + productLine[1] + ": etc...");
            }
            return productDao.addProductBulk(list);
        }
        catch(NumberFormatException e)
        {
            throw new ParameterException("CSV Parsing problem,some fields are not in proper format :"+e.getMessage());
        }
        catch(ArrayIndexOutOfBoundsException | IOException ex)
        {
            throw new ParameterException("CSV Parsing problem :"+ex.getMessage());
        }
    }

    /**
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<Product> getProductList() throws Exception {
        return productDao.getProductList();
    }

    @Override
    public boolean deleteProduct(long id) throws Exception {
        return productDao.deleteProduct(id);
    }
    



    @Override
    public boolean updateProduct(Product shop) throws Exception {
        return productDao.updateProduct(shop);
    }

    @Override
    public Product getProductById(long id) throws Exception {
        return (Product)productDao.getProductById(id);
    }

    @Override
    public boolean checkProductExistByName(String name) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Product> getProductListByShopId(long shopId) throws Exception {
        
        List<Product> product = null;
        try
        {
            product = productDao.getProductListByShopId(shopId);
        }
        catch(NullPointerException ex)
        {
            log.warn(ex);
            product = new ArrayList<>(0);
        }
        
        return product;
        
    }

}
