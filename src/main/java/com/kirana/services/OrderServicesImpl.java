/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kirana.services;

import com.kirana.dao.OrderDao;
import com.kirana.model.Order;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author nikhilvs
 */
public class OrderServicesImpl implements OrderServices{
    
    private static final Logger log = Logger.getLogger(OrderServicesImpl.class);
    
    @Autowired
    OrderDao orderDao;
    
//    @Autowired
//    OrderDao entitydao;
    
    /**
     *
     * @param shop
     * @return
     * @throws Exception
     */
        
    @Override
    public boolean addOrder(Order shop) throws Exception {
        return orderDao.addOrder(shop);
    }

    @Override
    public List<Order> getOrderByShopId(long id, String createdAt) throws Exception {
        return orderDao.getOrderByShopId(id);
    }

    @Override
    public Order getOrderById(String id) throws Exception {
        return orderDao.getOrderById(id);
    }

    @Override
    public List<Order> getOrderBetween(long id, String FromDate, String ToDate) throws Exception {
        return orderDao.getOrderBetween(id, FromDate, ToDate);
    }

    @Override
    public boolean deleteOrder(long id, String createdAt) throws Exception {
        return orderDao.deleteOrder(id, createdAt);
    }

    @Override
    public boolean updateOrder(Order order) throws Exception {
        return orderDao.updateOrder(order);
    }
    
    
    
}
