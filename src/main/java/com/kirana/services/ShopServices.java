/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kirana.services;

import com.kirana.model.Shop;
import java.util.List;

/**
 *
 * @author nikhilvs
 */
public interface ShopServices {

    public boolean addShop(Shop shop) throws Exception;

    public Shop getShopById(long id) throws Exception;

    public List<Shop> getShopList() throws Exception;

    public boolean deleteShop(long id) throws Exception;
    
    public boolean updateShop(Shop shop) throws Exception;
    
    public boolean checkShopExistByName(String name) throws Exception;
    
}
