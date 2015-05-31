/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kirana.dao;

import com.kirana.model.Shop;
import com.kirana.model.User;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author nikhilvs
 */
public class ShopDaoImpl implements ShopDao {
    
    private static final Logger log = Logger.getLogger(ShopDaoImpl.class);

    @Autowired
    SessionFactory sessionFactory;

    Session session = null;
    Transaction tx = null;

    @Override
    public boolean addEntity(Shop shop) throws Exception {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        session.save(shop);
        tx.commit();
        session.close();

        return false;
    }

    @Override
    public Shop getEntityById(long id) throws Exception {
        session = sessionFactory.openSession();
        Shop shop = (Shop) session.get(Shop.class, new Long(id));
        tx = session.getTransaction();
        session.beginTransaction();
        tx.commit();
        session.close();
        return shop;
    }

    @Override
    public List<Shop> getEntityList() throws Exception {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        List<Shop> shopList = session.createCriteria(Shop.class).list();
        tx.commit();
        session.close();
        return shopList;
    }

    @Override
    public boolean deleteEntity(long id) throws Exception {
        session = sessionFactory.openSession();
        Object o = session.load(User.class, id);
        tx = session.getTransaction();
        session.beginTransaction();
        session.delete(o);
        tx.commit();
        session.close();
        return false;
    }
    
    /**
     *
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean updateEntity(Shop o) throws Exception {
        session = sessionFactory.openSession();
        boolean success=false;
        try
        {    
        tx = session.beginTransaction();
        session.update(o);
        tx.commit();
        success=true;
        }
        catch(Exception e)
        {
            if (tx!=null) tx.rollback();
            throw e;
        }
        finally{
            session.close();
        }
        return success;
    }
    
    @Override
    public Shop getShopExistByName(String name) throws Exception
    {
         session = sessionFactory.openSession();
         Shop shop=null;
        try
        {    
            String hql = "FROM Shop  where name=:username";
            Query query = session.createQuery(hql);
            query.setParameter("username",name);

            List u = query.list();
            try
            {
                 shop = (Shop)u.get(0);
            }
            catch(IndexOutOfBoundsException ex)
            { 
                log.warn(ex);
            }
        }
        finally
        {
        session.close();
        }
        return shop;
    }


}
