package com.kirana.utils;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {

	private int majorCode;
        private int minorCode;
	private String message;
        private Object data;
        private List<Object> objectList;

	public Response(int majorCode,int minorCode,String message,Object obj) {
            this.minorCode = minorCode;
            this.majorCode = majorCode;
	    this.message = message;
            this.data=obj;
	}
        
        public Response(int majorCode,Object obj) {
            this.majorCode = majorCode;
            this.data=obj;
	}
        
        public Response(int majorCode,int minorCode,String message,List<Object> objectList) {
            this.minorCode = minorCode;
            this.majorCode = majorCode;
	    this.message = message;
            this.objectList=objectList;
	}
        
//        public Response(Object obj,int majorCode,int minorCode,String message) {
//            this.minorCode = minorCode;
//            this.majorCode = majorCode;
//	    this.message = message;
//            this.data=obj;
//	}

	public Response(int majorCode, String message) {
		this.majorCode = majorCode;
		this.message = message;
	}

    public int getMajorCode() {
        return majorCode;
    }

    public void setMajorCode(int majorCode) {
        this.majorCode = majorCode;
    }

    public int getMinorCode() {
        return minorCode;
    }

    public void setMinorCode(int minorCode) {
        this.minorCode = minorCode;
    }

    public Object getObject() {
        return data;
    }

    public void setObject(Object object) {
        this.data = object;
    }

    public String getMessage() {
            return message;
    }

    public void setMessage(String message) {
            this.message = message;
    }
}
