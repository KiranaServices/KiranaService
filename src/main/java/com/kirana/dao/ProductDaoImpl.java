/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kirana.dao;

import com.kirana.model.Product;
import com.kirana.model.User;
import static com.kirana.utils.GlobalConfig.BATCH_INSERT_SIZE;
import com.kirana.utils.ParameterException;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author nikhilvs
 */
public class ProductDaoImpl implements ProductDao {

    private static final Logger log = Logger.getLogger(ProductDaoImpl.class);

    @Autowired
    SessionFactory sessionFactory;

    Session session = null;
    Transaction tx = null;

    @Override
    public boolean addProduct(Product shop) throws Exception {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        session.save(shop);
        tx.commit();
        session.close();

        return false;
    }

    @Override
    public Product getProductById(long id) throws Exception {
        session = sessionFactory.openSession();
        Product shop = (Product) session.get(Product.class, new Long(id));
        tx = session.getTransaction();
        session.beginTransaction();
        tx.commit();
        session.close();
        return shop;
    }

    @Override
    public List<Product> getProductList() throws Exception {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        List<Product> shopList = session.createCriteria(Product.class).list();
        tx.commit();
        session.close();
        return shopList;
    }

    @Override
    public boolean deleteProduct(long id) throws Exception {
        session = sessionFactory.openSession();
        Object o = session.load(Product.class, id);
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
    public boolean updateProduct(Product o) throws Exception {
        session = sessionFactory.openSession();
        boolean success = false;
        try {
            tx = session.beginTransaction();
            session.update(o);
            tx.commit();
            success = true;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
        return success;
    }

    @Override
    public boolean addProductBulk(List<Product> products) throws Exception{
        boolean status=false;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            int i = 0;
            for (Product product : products) {
                session.save(product);
                if (i % BATCH_INSERT_SIZE == 0) { // Same as the JDBC batch size
                    //flush a batch of inserts and release memory:
                    session.flush();
                    session.clear();
                }
            }
            tx.commit();
            session.close();
            status=true;
        } catch (ConstraintViolationException ex) {
            throw new ParameterException("DB insert problem,constraint caused the problem :" + ex.getConstraintName()+"Message :"+ex.getMessage());
        }

        return status;
    }

   

    @Override
    public List<Product> getProductListByShopId(long shopId) throws Exception {
        session = sessionFactory.openSession();
         List<Product> product=null;
        try
        {    
            String hql = "FROM Product  where shop_id=:id";
            Query query = session.createQuery(hql);
            query.setParameter("id",shopId);
            product =query.list();
        }
        finally
        {
        session.close();
        }
        return product;
    }

    @Override
    public boolean checkProductExistByName(String name) throws Exception {
        
         session = sessionFactory.openSession();
         Product p=null;
        try
        {    
            String hql = "FROM Product  where product_id=:name";
            Query query = session.createQuery(hql);
            query.setParameter("name",name);
            try
            {
                 p = (Product)query.list().get(0);
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
        return p!=null;
    }

}
