/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kirana.services;

import com.kirana.dao.UserDao;
import com.kirana.model.User;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author nikhilvs
 */
public class UserServicesImpl implements UserServices{
    
    @Autowired
    UserDao userdao;
    
    // to do
    @Autowired
    BCryptPasswordEncoder encoder;
    
    /**
     *
     * @param user
     * @return
     * @throws Exception
     */
        
    @Override
    public boolean addEntity(User user) throws Exception {
        user.setPassword(encoder.encode(user.getPass()));
        user.setUserToken(UUID.randomUUID().toString());
        return userdao.addEntity(user);
    }

    @Override
    public User getEntityById(long id) throws Exception {
        return userdao.getEntityById(id);
    }

    @Override
    public List<User> getEntityList() throws Exception {
        return userdao.getEntityList();
    }

    @Override
    public boolean deleteEntity(long id) throws Exception {
        return userdao.deleteEntity(id);
    }
    
    @Override
    public User getLoginInfo(String email,String password) throws Exception{
     
        User user = userdao.getLoginInfo(email);
        if(user!=null && encoder.matches(password,user.getPassword()))    
            return user;
        else
            return null;
    }

    @Override
    public User getUsernfo(String userName, String userToken) throws Exception {
        return  userdao.getUsernfo(userName, userToken);
    }

    @Override
    public boolean updateUser(User user) throws Exception {
        return userdao.updateUser(user);
    }
    
}
