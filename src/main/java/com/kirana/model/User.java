package com.kirana.model;

//import com.google.gson.annotations.Expose;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class User  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(hidden = true, required=false)
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "username",unique = true)
    private String userName;

    @JsonIgnore
    @Column(name = "password_hash")
    private String password;
    
    @ApiModelProperty(hidden = true, required=false)
    @Column(name = "user_token",unique = true)
    private String userToken;
    
    @ApiModelProperty(hidden = true, required=false)
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
    
    @ApiModelProperty(hidden = true,value = "Shop info of this user", required=false)
    @ManyToOne
    @JoinColumn(name="shop_id",referencedColumnName = "id")
    private Shop shop;

    public User() {
        this.userRole = "USER";
    }

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

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }
    
    @JsonIgnore
    public String getPassword() {
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

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

//    @Override
//    public String toString() {
//        return "User{" + "id=" + id + ", userName=" + userName + ", password=" + password + ", userToken=" + userToken + ", userRole=" + userRole + ", email=" + email + ", phone=" + phone + ", street=" + street + ", state=" + state + ", shop=" + shop + '}';
//    }
//
//    
 
    
    
}
