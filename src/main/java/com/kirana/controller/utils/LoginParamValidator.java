package com.kirana.controller.utils;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


public class LoginParamValidator implements Validator{

	
//	 private static final Logger logger = LoggerFactory.getLogger(LoginParamValidator.class);
	    
            @Override
	    public boolean supports(Class<?> classObject) {
	        return classObject == LoginParam.class;
	    }

            @Override
	    public void validate(Object obj, Errors errors)
	    {
	    	LoginParam loginParam = (LoginParam)obj;
                ValidationUtils.rejectIfEmptyOrWhitespace(errors,"email","not defined","email parameter issing");
                ValidationUtils.rejectIfEmptyOrWhitespace(errors,"password","not defined","password parameter issing");
	        
	    }
}
