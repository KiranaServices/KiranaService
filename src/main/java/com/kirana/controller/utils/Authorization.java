/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kirana.controller.utils;

import static com.kirana.controller.utils.UserRole.ADMIN;
import static com.kirana.controller.utils.UserRole.USER;

/**
 *
 * @author nikhilvs
 */
public enum Authorization {
    
    
    USER_LOGIN (USER,ADMIN),
    USER_REGISTER(USER,ADMIN),
    USER_CHANGEROLE(ADMIN,USER),
    USER_CHANGEPASSWORD(USER,ADMIN),
    USER_DELETE(ADMIN),
    USER_LIST(USER,ADMIN),
    USER_LISTALL(ADMIN),
    SHOP_REGISTER(ADMIN,USER),
    SHOP_DELETE(ADMIN,USER),
    SHOP_LIST_ALL(ADMIN),
    SHOP_LIST(ADMIN,USER);
    
    private final UserRole[] roles;

    private Authorization(UserRole... role) {
        this.roles = role;
    }
    
    public boolean hasAuthority(String role) throws AuthorizationException{
        boolean status=false;
        for(UserRole r:roles)
        {
            System.out.println("role :"+r+" userr:"+role);
            if(r.toString().equalsIgnoreCase(role))
            {
                status=true;
                break;
            }   
        }
        if(!status)
            throw new AuthorizationException("User doesn't have access to this resource");
        return status;
    }
    
        
}