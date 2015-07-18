package com.kirana.controller.utils;

import com.kirana.model.Order;
import com.kirana.model.Product;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class OrderRegisterValidator implements Validator {

	 private static final Logger logger = LoggerFactory.getLogger(OrderRegisterValidator.class);
    @Override
    public boolean supports(Class<?> classObject) {
        return classObject == Order.class;
    }

    public OrderRegisterValidator(List<Product> productList) {
        this.productList=productList;
    }
    List<Product> productList;

    @Override
    public void validate(Object obj, Errors errors) {
        Order order = (Order) obj;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "createdAt", "not defined", "createdAt parameter missing");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "updatedAt", "not defined", "updatedAt parameter missing");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "orderList", "not defined", "list parameter missing");
        if (!errors.hasErrors()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            sdf.setLenient(false);
            try {
                Date date = sdf.parse(order.getCreatedAt());
            } catch (ParseException e) {
                logger.warn(e+"sfsd");
                errors.rejectValue("createdAt","createdAt not in proper format");
            }
            try {
                Date date = sdf.parse(order.getUpdatedAt());
            } catch (ParseException e) {
                logger.warn(e+"sfsd");
                errors.rejectValue("updatedAt", "updatedAt not in proper format");
            }
          
            List<String> productCodeList = new ArrayList<>();
            productList.forEach((product)->{
                logger.info("product :"+product.getProductCode());
            productCodeList.add(product.getProductCode());
            });
//            logger.info("order.getOrderList() : "+order.getOrderList().keySet().toString());
//            
//            for(String productCode:order.getOrderList().keySet())
//            {
//                logger.info("productCode : "+productCode);
//                if(!productCodeList.contains(productCode))
//                    errors.rejectValue("orderlist","Invalid product code in orderList");
//            }
            order.getOrderList().keySet().forEach((String productCode) -> {
                if(!productCodeList.contains(productCode))
                    errors.rejectValue("orderList",productCode+" not an valid product code");
            });

        }

    }
}
