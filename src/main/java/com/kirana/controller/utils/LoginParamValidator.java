package com.kirana.controller.utils;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


public class LoginParamValidator implements Validator{

	
//	 private static final Logger logger = LoggerFactory.getLogger(LoginParamValidator.class);
	    
	    public boolean supports(Class<?> classObject) {
	        return classObject == LoginParam.class;
	    }

	    public void validate(Object obj, Errors errors)
	    {
	    	LoginParam loginParam = (LoginParam)obj;

	        if(loginParam.getUserName() ==null || loginParam.getUserName().trim().length()==0)
			{
		            errors.rejectValue("UserName", "username.required","username parameter issing");
			}
			else if(loginParam.getPassWord()==null || loginParam.getPassWord().trim().length()==0)
			{
		            errors.rejectValue("PassWord", "password.required","password missing");
			}
	    }
}
