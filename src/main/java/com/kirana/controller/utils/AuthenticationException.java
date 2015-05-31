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
public class AuthenticationException extends  Exception{
    
    public AuthenticationException(){
            super();
    }
    
    public AuthenticationException(String message){
            super(message);
    }
    
    public AuthenticationException(Throwable cause){
        super(cause);
    }
    
    public AuthenticationException(String message,Throwable cause){
        super(message,cause);
    }
}
