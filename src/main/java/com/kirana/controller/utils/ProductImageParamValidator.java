/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kirana.controller.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author nikhilvs
 */
public class ProductImageParamValidator implements Validator {

    private static final Logger log = Logger.getLogger(ProductImageParamValidator.class);
    @Override
    public boolean supports(Class<?> classObject) {
        return classObject == ProductImageParam.class;
    }

    @Override
    public void validate(Object obj, Errors errors) {
        ProductImageParam productImageParam = (ProductImageParam) obj;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "productCode", "valid productCode required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "productImageFile","valid productImageFile required");

        if (!errors.hasErrors()) {

                try {
                    BufferedImage image = ImageIO.read(productImageParam.getProductImageFile());
                    if (image == null) { 
                        errors.reject("Invalid image file");
                    }
                } catch (IOException ex) {
                    errors.reject("Invalid image file");
                }
            try {
                if(!productImageParam.getProductServices().checkProductExistByName(productImageParam.getProductCode()))
                    errors.reject("Invalid product code");
            } catch (Exception ex) {
                log.error(ex);
                errors.reject("Invalid product code");
            }
        }
    }
}
