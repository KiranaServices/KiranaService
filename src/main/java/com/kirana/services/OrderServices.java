/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kirana.services;

import com.kirana.model.Order;
import java.util.List;

/**
 *
 * @author nikhilvs
 */
public interface OrderServices {

    public boolean addOrder(Order order) throws Exception;
    public List<Order> getOrderByShopId(long id) throws Exception;
    public Order getOrderByCreatedAt(long id,String createdAt) throws Exception;
    public Order getOrderById(String id) throws Exception;
    public List<Order> getOrderBetween(long id,String FromDate,String ToDate) throws Exception;
    public boolean deleteOrder(long id,String createdAt) throws Exception;
    public boolean updateOrder(Order order) throws Exception;
    
}
