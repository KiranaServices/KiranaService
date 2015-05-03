/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kirana.dao;

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
public class UserDaoImpl implements UserDao {
    
    private static final Logger log = Logger.getLogger(UserDaoImpl.class);

    @Autowired
    SessionFactory sessionFactory;

    Session session = null;
    Transaction tx = null;

    @Override
    public boolean addEntity(User user) throws Exception {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        session.save(user);
        tx.commit();
        session.close();

        return false;
    }

    @Override
    public User getEntityById(long id) throws Exception {
        session = sessionFactory.openSession();
        User user = (User) session.get(User.class, new Long(id));
        tx = session.getTransaction();
        session.beginTransaction();
        tx.commit();
        session.close();
        return user;
    }

    @Override
    public List<User> getEntityList() throws Exception {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        List<User> userList = session.createCriteria(User.class).list();
        tx.commit();
        session.close();
        return userList;
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
    
    @Override
    public User getLoginInfo(String email){
        session = sessionFactory.openSession();
         User user=null;
        try
        {    
            String hql = "FROM User  where email=:email";
            Query query = session.createQuery(hql);
            query.setParameter("email",email);

            List u = query.list();
            try
            {
                 user = (User)u.get(0);
            }
            catch(IndexOutOfBoundsException ex)
            { 
                log.warn(ex);
            }


    //        results.stream().forEach((result) -> {
    //            System.out.println("result :"+result);
    //        });

        }
        finally
        {
        session.close();
        }
        return user;
    }

    @Override
    public User getUsernfo(String userName, String userToken) throws Exception {
        session = sessionFactory.openSession();
        User user=null;
        try
        {    
            String hql = "FROM User  where userName=:userName and userToken=:token";
            Query query = session.createQuery(hql);
            query.setParameter("userName",userName);
            query.setParameter("token",userToken);
            List u = query.list();
            try
            {
                 user = (User)u.get(0);
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
        return user;
    }

    @Override
    public boolean updateUser(User user) throws Exception {
        session = sessionFactory.openSession();
        boolean success=false;
        try
        {    
        tx = session.beginTransaction();
        session.update(user);
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

}
