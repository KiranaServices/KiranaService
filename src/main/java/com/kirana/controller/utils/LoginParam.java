package com.kirana.controller.utils;

import com.firebase.security.token.TokenGenerator;
import com.firebase.security.token.TokenOptions;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LoginParam implements Serializable {
	
	public LoginParam(String email,String password)
    {
        this.email=email;
        this.password=password;
    }
    private String password;
    private String email;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
