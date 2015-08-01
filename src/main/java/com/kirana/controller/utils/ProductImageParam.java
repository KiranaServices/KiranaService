/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kirana.controller.utils;

import com.kirana.services.ProductServices;
import java.io.File;

/**
 *
 * @author nikhilvs
 */
public class ProductImageParam {

   

    public ProductImageParam(ProductServices productServices, String productCode, File productImageFile) {
        this.productServices = productServices;
        this.productCode = productCode;
        this.productImageFile = productImageFile;
    }
    
    
     private ProductServices productServices;
    private String productCode;
    private File productImageFile;

    public ProductServices getProductServices() {
        return productServices;
    }

    public void setProductServices(ProductServices productServices) {
        this.productServices = productServices;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public File getProductImageFile() {
        return productImageFile;
    }

    public void setProductImageFile(File productImageFile) {
        this.productImageFile = productImageFile;
    }
    
    
    
    
    
}
