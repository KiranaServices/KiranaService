package com.kirana.model;

//import com.google.gson.annotations.Expose;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;



@Entity
@Table(name = "shop")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Shop implements Serializable {

    
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @NotNull
    @Column(name = "name",unique = true,nullable = false)
    private String name;

    @Column(name = "type")
    private String type;
    
    @Column(name = "address")
    private String address;
    
    @NotNull
    @Column(name = "TIN")
    private String tin;
    
    @Column(name = "service_tax",precision = 2)
    private float serviceTax;
    
    @Column(name = "service_charge",precision = 2)
    private float serviceCharge;
    
   
    @Column(name = "VAT")
    private float vat;

    @Column(name = "website")
    private String website;
    
    @JsonIgnore
    @OneToMany(mappedBy="shop")
//    @ca
    private Set<User> user;
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public float getServiceTax() {
        return serviceTax;
    }

    public void setServiceTax(float serviceTax) {
        this.serviceTax = serviceTax;
    }

    public float getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(float serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public float getVat() {
        return vat;
    }

    public void setVat(float vat) {
        this.vat = vat;
    }

    public Set<User> getUser() {
        return user;
    }

    public void setUser(Set<User> user) {
        this.user = user;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
    
    @Override
    public String toString() {
        return "Shop{" + "id=" + id + ", name=" + name + ", type=" + type + ", address=" + address + ", TIN=" + tin + ", serviceTax=" + serviceTax + ", serviceCharge=" + serviceCharge + ", VAT=" + vat + ", user=" + user + '}';
    }

}