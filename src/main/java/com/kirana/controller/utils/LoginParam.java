package com.kirana.controller.utils;

import com.firebase.security.token.TokenGenerator;
import com.firebase.security.token.TokenOptions;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LoginParam implements Serializable {
	
	public LoginParam(String userName,String passWord)
    {
        this.userName=userName;
        this.passWord=passWord;
    }
    private String passWord;
    private String userName;
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

        
        public static void main(String[] args) {
            Map<String, Object> authPayload = new HashMap<>();
            authPayload.put("uid", "1");
            authPayload.put("some", "arbitrary");
            authPayload.put("data", "here");

            Date date = new Date((System.currentTimeMillis()+3600));
            TokenOptions tokenOptions = new TokenOptions();
            tokenOptions.setExpires(null);
            TokenGenerator tokenGenerator = new TokenGenerator("<YOUR_FIREBASE_SECRET>");
            String token = tokenGenerator.createToken(authPayload);
            System.out.println("token :"+token);
//            System.out.println("token :"+to);
            
    }
}
