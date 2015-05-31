package com.kirana.controller.utils;

import com.kirana.model.Shop;
import com.kirana.services.ShopServices;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ShopRegisterParamValidator implements Validator {

    public ShopRegisterParamValidator(ShopServices shopServices) {
        this.shopServices = shopServices;
    }

    
    
    
    private final ShopServices shopServices;
//	 private static final Logger logger = LoggerFactory.getLogger(LoginParamValidator.class);
    @Override
    public boolean supports(Class<?> classObject) {
        return classObject == Shop.class;
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Shop shop = (Shop) obj;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name","error code not defined", "username parameter missing");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "type","error code not defined", "password parameter missing");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address","error code not defined", "Email parameter missing");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tin","error code not defined", "Number parameter missing");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "serviceTax","error code not defined", "State parameter missing");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "serviceCharge","error code not defined", "Street parameter missing");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "vat","error code not defined", "Street parameter missing");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "website","error code not defined", "Street parameter missing");
        if(!errors.hasErrors())
        {
            try {
                if(shopServices!=null )
                {
                    if(shopServices.checkShopExistByName(shop.getName()))
                    errors.rejectValue("name", "error code not defined", "duplicate shop anme");
                }
                else
                {
                    errors.rejectValue("name", "error code not defined", "exception in init of db");
                }
            } catch (Exception ex) {
                Logger.getLogger(ShopRegisterParamValidator.class.getName()).log(Level.SEVERE, null, ex);
                errors.rejectValue("name", "error code not defined", "exception while checking params uniqueness" );
            }
        }   

            

    }
}
