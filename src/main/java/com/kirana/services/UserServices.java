/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kirana.services;

import com.kirana.controller.utils.Authorization;
import com.kirana.model.User;
import java.util.List;

/**
 *
 * @author nikhilvs
 */
public interface UserServices {

    public boolean addEntity(User user) throws Exception;

    public User getEntityById(long id) throws Exception;

    public List<User> getEntityList() throws Exception;

    public boolean deleteEntity(long id) throws Exception;
    
    public User getLoginInfo(String email,String password) throws Exception;
    
    public User getUsernfo(String userToken) throws Exception;
    
    public boolean updateUser(User user) throws Exception;
    
    public User isAuthenticatedUser(String userToken,Authorization queryName) throws Exception;
    
    public boolean CheckIfUserExistByName(String userName) throws Exception ;
    public boolean CheckIfUserExistByMail(String email) throws Exception ;
    
//    public List<String> get

}
