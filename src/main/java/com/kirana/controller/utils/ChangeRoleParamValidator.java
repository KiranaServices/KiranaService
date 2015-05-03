/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kirana.controller.utils;

import com.kirana.utils.GlobalConfig;
import org.springframework.validation.Errors;
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

	        if(changeRole.getUserName() ==null || changeRole.getUserName().trim().length()==0)
                {
                    errors.rejectValue("UserName", "username.required","username parameter issing");
                }
                else if(changeRole.getUserToken()==null || changeRole.getUserToken().trim().length()==0)
                {
                    errors.rejectValue("ApiToken", "ApiToken.required","ApiToken missing");
                }
                else if(changeRole.getUserRole()==null || changeRole.getUserRole().trim().length()==0)
                {
                    errors.rejectValue("userRole", "Role.required","Role.required");
                }
                else
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
