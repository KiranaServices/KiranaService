/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kirana.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nikhilvs
 */
public class OrderList implements Serializable{
    
    private  Map orderSet;

    public OrderList() {
        this.orderSet = new HashMap<String,Integer>(5);
    }

    public OrderList(Map orderSet) {
        this.orderSet = orderSet;
    }
    
    

    public Map getOrderSet() {
        return orderSet;
    }

    public void setOrderSet(Map orderSet) {
        this.orderSet = orderSet;
    }
    
    
    
}
