package com.kirana.user.utils;

import java.util.HashMap;
import java.util.Iterator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class GeneralValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		GeneralParam loginParam = (GeneralParam)target;
		HashMap<String,String> map = loginParam.getParamMap();
		for (Iterator<String> iterator = map.keySet().iterator(); iterator.hasNext();) {
			String param = (String) iterator.next();
			if(param ==null || param.trim().length()==0)
			{
		            errors.rejectValue(map.get(param), map.get(param)+" missing",map.get(param)+" missing");
			}
		}
	}

}
