/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kirana.services;

import com.kirana.controller.utils.AuthenticationException;
import com.kirana.controller.utils.Authorization;
import com.kirana.controller.utils.AuthorizationException;
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
        user.setPassword(encoder.encode(user.getPassword()));
        user.setUserToken(UUID.randomUUID().toString());
        return userdao.addUser(user);
    }

    @Override
    public User getEntityById(long id) throws Exception {
        return userdao.getUserById(id);
    }

    @Override
    public List<User> getEntityList() throws Exception {
        return userdao.getUserList();
    }

    @Override
    public boolean deleteEntity(long id) throws Exception {
        return userdao.deleteUser(id);
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
    public User getUsernfo(String userToken) throws Exception {
        return  userdao.getUsernfo(userToken);
    }
    
    @Override
    public boolean CheckIfUserExistByName(String userName) throws Exception {
        User user=userdao.CheckIfUserExistByName(userName);
        return user!=null;
    }
    
    @Override
    public boolean CheckIfUserExistByMail(String email) throws Exception {
        User user=userdao.CheckIfUserExistByMail(email);
        return user!=null;
    }

    @Override
    public boolean updateUser(User user) throws Exception {
        return userdao.updateUser(user);
    }

    @Override
    public User isAuthenticatedUser(String userToken,Authorization queryName) throws AuthenticationException,AuthorizationException,Exception {
        
        User user =getUsernfo(userToken);
        if(user==null)
            throw new AuthenticationException("Invalid User");
        else
        {
            queryName.hasAuthority(user.getUserRole());
            return user;
        }
    }
    
}
