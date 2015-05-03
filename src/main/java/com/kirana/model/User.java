package com.kirana.model;

import com.google.gson.annotations.Expose;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.codehaus.jackson.annotate.JsonIgnore;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "username",unique = true)
    private String userName;


    
    @Column(name = "password_hash")
    @Expose(deserialize = false)
    @JsonIgnore
    private String password;
    
    @Column(name = "user_token",unique = true)
    private String userToken;
    
    @Column(name = "user_role")
    private String userRole;
    
    @Column(name = "email",unique = true)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "street")
    private String street;

    @Column(name = "state")
    private String state;

    @Column(name = "tin_no")
    private String TIN;

    @Column(name = "website")
    private String website;

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }



    public void setPassword(String password) {
        this.password = password;
    }
    
    @JsonIgnore
    public String getPassword() {
        return this.password ;
    }

    public String getPass() {
        return this.password ;
    }
    
    
    
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTIN() {
        return TIN;
    }

    public void setTIN(String TIN) {
        this.TIN = TIN;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", userName=" + userName + ", password=" + password + ", userToken=" + userToken + ", userRole=" + userRole + ", email=" + email + ", phone=" + phone + ", street=" + street + ", state=" + state + ", TIN=" + TIN + ", website=" + website + '}';
    }
    
    
}
