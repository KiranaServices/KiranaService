/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kirana.controller.utils;

import com.kirana.utils.GlobalConfig;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author nikhilvs
 */
public class ChangeRoleParamValidator implements Validator{

	
//	 private static final Logger logger = LoggerFactory.getLogger(LoginParamValidator.class);
	    
            @Override
	    public boolean supports(Class<?> classObject) {
	        return classObject == ChangeRoleParam.class;
	    }

            @Override
	    public void validate(Object obj, Errors errors)
	    {
	    	ChangeRoleParam changeRole = (ChangeRoleParam)obj;

                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userToken","error code not defined", "ApiToken.required");
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userRole","error code not defined", "Role.required");
	         
                if(!errors.hasErrors())
                {
                        boolean roleMatches=false;
                        for (String USERROLE : GlobalConfig.USERROLES) {
                            if (changeRole.getUserRole().equals(USERROLE)) {
                                roleMatches=true;
                                break;
                            }
                        }
                        if(!roleMatches)
                            errors.rejectValue("userRole", "Role invalid","Role.required");
                }
	    }
}
