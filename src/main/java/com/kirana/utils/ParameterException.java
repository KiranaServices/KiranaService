package com.kirana.utils;

import java.io.Serializable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;


@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Invalid Parameter passed") 
public class ParameterException extends RuntimeException implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ParameterException(String msg) {
	    super(msg);
	    
	}
        public ParameterException(String msg,boolean d) {
	    super(msg);
	    
	}
}
