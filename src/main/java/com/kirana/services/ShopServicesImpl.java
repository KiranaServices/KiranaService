/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kirana.services;

import com.kirana.dao.EntityDao;
import com.kirana.dao.ShopDao;
import com.kirana.model.Shop;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author nikhilvs
 */
public class ShopServicesImpl implements ShopServices{
    
    @Autowired
    ShopDao shopdao;
    
//    @Autowired
//    EntityDao entitydao;
    
    // to do
    @Autowired
    BCryptPasswordEncoder encoder;
    
    /**
     *
     * @param shop
     * @return
     * @throws Exception
     */
        
    @Override
    public boolean addShop(Shop shop) throws Exception {
        return shopdao.addEntity(shop);
    }

    /**
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<Shop> getShopList() throws Exception {
        return shopdao.getEntityList();
    }

    @Override
    public boolean deleteShop(long id) throws Exception {
        return shopdao.deleteEntity(id);
    }
    



    @Override
    public boolean updateShop(Shop shop) throws Exception {
        return shopdao.updateEntity(shop);
    }

    @Override
    public Shop getShopById(long id) throws Exception {
        return (Shop)shopdao.getEntityById(id);
    }

    @Override
    public boolean checkShopExistByName(String name) throws Exception
    {
        Shop shop = shopdao.getShopExistByName(name);
        
        return shop!=null;
    }

}
