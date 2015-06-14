package com.kirana.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Response<Data> implements Serializable {

	private int majorCode;
        private int minorCode;
	private String message;
        private Data data;
        private List<Data> dataList;

	public Response(int majorCode,int minorCode,String message,Data obj) {
            this.minorCode = minorCode;
            this.majorCode = majorCode;
	    this.message = message;
            this.data=obj;
	}
        
        public Response(int majorCode,Data obj) {
            this.majorCode = majorCode;
            this.data=obj;
	}
        
        public Response(int majorCode,int minorCode,String message,List<Data> objectList) {
            this.minorCode = minorCode;
            this.majorCode = majorCode;
	    this.message = message;
            this.dataList=objectList;
	}

	public Response(int majorCode, String message) {
		this.majorCode = majorCode;
		this.message = message;
	}

    public int getMajorCode() {
        return majorCode;
    }

    public int getMinorCode() {
        return minorCode;
    }

    public String getMessage() {
        return message;
    }

    public List<Data> getData() {
        if(data!=null)
        {
            List<Data> d =new ArrayList();
            d.add(data);
            return d;
        }
        else
            return dataList;
    }

}
