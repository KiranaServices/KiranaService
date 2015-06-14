/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kirana.controller.utils;

import java.io.File;

/**
 *
 * @author nikhilvs
 */
public class ProductRegisterParam {

    public ProductRegisterParam(String userToken, File productCsvFile) {
        this.userToken = userToken;
        this.productCsvFile = productCsvFile;
    }

    private String userToken;
    private File productCsvFile;

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public File getProductCsvFile() {
        return productCsvFile;
    }

    public void setProductCsvFile(File productCsvFile) {
        this.productCsvFile = productCsvFile;
    }
    
}
