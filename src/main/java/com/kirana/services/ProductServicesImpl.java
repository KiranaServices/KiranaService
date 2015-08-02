/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kirana.services;

import au.com.bytecode.opencsv.CSVReader;
import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.kirana.dao.ProductDao;
import com.kirana.model.Product;
import com.kirana.model.Shop;
import static com.kirana.utils.GlobalConfig.S3_BUCKET_NAME;
import com.kirana.utils.ParameterException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author nikhilvs
 */
public class ProductServicesImpl implements ProductServices {

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
    public boolean addProductBulk(File productCsv, Shop shop) throws Exception {

        CSVReader reader = new CSVReader(new FileReader(productCsv));
        String[] productLine;
        List<Product> list;
        list = new ArrayList<>();
        try {
            reader.readNext(); // skip the header part  
            while ((productLine = reader.readNext()) != null) {
                if(productLine.length>5 && productLine[5]!=null)
                {
                    list.add(new Product(productLine[0], productLine[1], productLine[2], productLine[3], productLine[4],productLine[5], shop));
                    log.info(productLine[0] + ":" + productLine[1] + ": etc..."+productLine[5]);
                }
                else
                    list.add(new Product(productLine[0], productLine[1], productLine[2], productLine[3], productLine[4], shop));
            }
            return productDao.addProductBulk(list);
        } catch (NumberFormatException e) {
            throw new ParameterException("CSV Parsing problem,some fields are not in proper format :" + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException | IOException ex) {
            throw new ParameterException("CSV Parsing problem :" + ex.getMessage());
        }
    }

    /**
     *
     * @return @throws Exception
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
        return (Product) productDao.getProductById(id);
    }

    @Override
    public boolean checkProductExistByName(String name) throws Exception {
       return  productDao.checkProductExistByName(name);
    }

    @Override
    public List<Product> getProductListByShopId(Shop shop, boolean isImageRequired) throws Exception {

        List<Product> products = null;
        try {
            products = productDao.getProductListByShopId(shop.getId());
            if (products != null && isImageRequired) {

                AmazonS3 s3client = getS3Client();
                for (Product product : products) {
                    try {
                        product.setProductCodeImageUrl(s3client.generatePresignedUrl(S3_BUCKET_NAME, shop.getName() + "/" + product.getProductCode(), getExpiration(24)).toExternalForm());
                    } catch (AmazonClientException ae) {
                        log.warn("No url for object :" + product.getProductCode());
                    }
                }
            }

        } catch (NullPointerException ex) {
            log.warn(ex);
            products = new ArrayList<>(0);
        }

        return products;

    }

    @Override
    public boolean uploadProductImage(File productCsv, Shop shop, String productCode) throws Exception {
        AmazonS3 s3client = getS3Client();
        if (!s3client.doesBucketExist(S3_BUCKET_NAME)) {
            s3client.createBucket(S3_BUCKET_NAME);
        }
        s3client.putObject(S3_BUCKET_NAME, shop.getName() + "/" + productCode, productCsv);
        return true;
    }

    public static Date getExpiration(int hour) {
        Date expiration = new Date();
        long milliSeconds = expiration.getTime();
        milliSeconds += 1000 * 60 * 60 * hour; // Add 1 hour.
        expiration.setTime(milliSeconds);
        return expiration;
    }

    public AmazonS3 getS3Client() {
        AmazonS3 s3client = new AmazonS3Client(new ProfileCredentialsProvider("kirana-s3"));
        s3client.setRegion(Region.getRegion(Regions.AP_SOUTHEAST_1));
        return s3client;
    }
}
