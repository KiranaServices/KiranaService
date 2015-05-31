package com.kirana.controller.utils;

import com.kirana.model.User;
import com.kirana.services.UserServices;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class UserRegisterParamValidator implements Validator {

    public UserRegisterParamValidator(UserServices userServices) {
        this.userServices = userServices;
    }

    
    
    
    private final UserServices userServices;
//	 private static final Logger logger = LoggerFactory.getLogger(LoginParamValidator.class);
    @Override
    public boolean supports(Class<?> classObject) {
        return classObject == User.class;
    }

    @Override
    public void validate(Object obj, Errors errors) {
        User user = (User) obj;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "UserName","error code not defined", "username parameter missing");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password","error code not defined", "password parameter missing");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email","error code not defined", "Email parameter missing");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone","error code not defined", "Number parameter missing");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "state","error code not defined", "State parameter missing");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "street","error code not defined", "Street parameter missing");
        if(!errors.hasErrors())
        {
            try {
                if(userServices!=null){
                if(userServices.CheckIfUserExistByName(user.getUserName()))
                    errors.rejectValue("UserName", "error code not defined", "duplicate username");
                else if(userServices.CheckIfUserExistByMail(user.getEmail()))
                    errors.rejectValue("email", "error code not defined", "duplicate email");
                }
                else
                {
                    errors.rejectValue("UserName", "error code not defined", "exception in init of db");
                }
            } catch (Exception ex) {
                Logger.getLogger(UserRegisterParamValidator.class.getName()).log(Level.SEVERE, null, ex);
                errors.rejectValue("UserName", "error code not defined", "exception while checking params uniqueness" );
            }
        }   

            

    }
}
