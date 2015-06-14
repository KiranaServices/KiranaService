/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kirana.controller.utils;

import static com.kirana.utils.GlobalConfig.PRODUCT_CSV_FIRST_LINE;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author nikhilvs
 */
public class ProductRegisterParamValidator implements Validator {

//	 private static final Logger logger = LoggerFactory.getLogger(LoginParamValidator.class);
    @Override
    public boolean supports(Class<?> classObject) {
        return classObject == ProductRegisterParam.class;
    }

    @Override
    public void validate(Object obj, Errors errors) {
        ProductRegisterParam productRegister = (ProductRegisterParam) obj;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userToken", "error code not defined", "ApiToken.required");

        if (!errors.hasErrors()) {
            boolean roleMatches = false;
            Charset charset = Charset.forName("US-ASCII");
            try (BufferedReader reader = Files.newBufferedReader(Paths.get(productRegister.getProductCsvFile().getAbsolutePath()), charset)) {
                String line = reader.readLine();
                if (!line.contains(PRODUCT_CSV_FIRST_LINE)) {
                    errors.reject( "CSV format wrong", "CSV format wrong");
                }
                line = reader.readLine();
                if (line.isEmpty()) {
                    errors.reject( "CSV file empty", "CSV file empty");
                }
            } catch (IOException x) {
                errors.reject( "CSV format wrong", "CSV format wrong");
            }
        }
    }
}
