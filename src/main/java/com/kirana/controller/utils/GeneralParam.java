package com.kirana.controller.utils;

import java.util.HashMap;

public class GeneralParam {
	
	
	public GeneralParam(HashMap<String,String> params) {
		paramMap=params;
	}
	
	private HashMap<String,String> paramMap = null;

	public HashMap<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(HashMap<String, String> paramMap) {
		this.paramMap = paramMap;
	}
	

}
