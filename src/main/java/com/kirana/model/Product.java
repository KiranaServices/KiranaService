package com.kirana.model;

//import com.google.gson.annotations.Expose;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wordnik.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author nikhilvs
 */
@Entity
@Table(name = "products")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Product implements Serializable {

    public Product() {
    }

    public Product(String product_id, String quantity, String price, String discount, String taxBracket, Shop shop) throws NumberFormatException {
        this.productCode = product_id;
        this.quantity = Float.valueOf(quantity);
        this.price = Float.valueOf(price);
        this.discount = Float.valueOf(discount);
        this.taxBracket = Float.valueOf(taxBracket);
        this.shop = shop;
    }

    
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "product_id",unique = true)
    private String productCode;
    
    @Column(name = "quantity",precision = 2)
    private float quantity;

    @Column(name = "price",precision = 2)
    private float price;
    
    @Column(name = "discount",precision = 2)
    private float discount;
    
    @Column(name = "tax_bracket",precision = 2)
    private float taxBracket;
    
    @ApiModelProperty(hidden = true,value = "productCodeImageUrl", required=false)
    @Transient
    private String productCodeImageUrl;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="shop_id")
    private Shop shop;
    
    
    
    
        @ApiModelProperty(hidden = true,value = "created at", required=false)
    private java.sql.Timestamp created_at;
    
    
    @ApiModelProperty(hidden = true,value = "last updated at", required=false)
    private java.sql.Timestamp updated_at;
    
    
    public String getCreated_at() {
        return  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(created_at.getTime()));
    }

    public String getUpdated_at() {
        return  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(updated_at.getTime()));
    }
    
    

    public Shop getShop() {
        return shop;
    }

    
    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public long getId() {
        return id;
    }


    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public float getTaxBracket() {
        return taxBracket;
    }

    public void setTaxBracket(float taxBracket) {
        this.taxBracket = taxBracket;
    }

    public String getProductCodeImageUrl() {
        return productCodeImageUrl;
    }

    public void setProductCodeImageUrl(String productCodeImageUrl) {
        this.productCodeImageUrl = productCodeImageUrl;
    }

    
    
    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", quantity=" + quantity + ", price=" + price + ", discount=" + discount + ", taxBracket=" + taxBracket + '}';
    }
    
    
    
}
