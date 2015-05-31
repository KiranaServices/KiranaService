/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kirana.dao;

import com.kirana.model.Shop;
import java.util.List;

/**
 *
 * @author nikhilvs
 */
public interface ShopDao {
        public boolean addEntity(Shop shop) throws Exception;
	public Shop getEntityById(long id) throws Exception;
	public List<Shop> getEntityList() throws Exception;
	public boolean deleteEntity(long id) throws Exception;
        public boolean updateEntity(Shop shop) throws Exception;
        
        public Shop getShopExistByName(String name) throws Exception;

}
