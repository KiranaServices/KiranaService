/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kirana.controller.utils;

/**
 *
 * @author nikhilvs
 */
public class AuthorizationException extends  Exception{
    
    public AuthorizationException(){
            super();
    }
    
    public AuthorizationException(String message){
            super(message);
    }
    
    public AuthorizationException(Throwable cause){
        super(cause);
    }
    
    public AuthorizationException(String message,Throwable cause){
        super(message,cause);
    }
}
