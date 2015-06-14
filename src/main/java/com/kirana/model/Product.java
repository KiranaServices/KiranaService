package com.kirana.model;

//import com.google.gson.annotations.Expose;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
        this.product_id = product_id;
        this.quantity = Float.valueOf(quantity);
        this.price = Float.valueOf(price);
        this.discount = Float.valueOf(discount);
        this.taxBracket = Float.valueOf(taxBracket);
        this.shop = shop;
    }

    
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "product_id",unique = true)
    private String product_id;
    
    @Column(name = "quantity",precision = 2)
    private float quantity;

    @Column(name = "price",precision = 2)
    private float price;
    
    @Column(name = "discount",precision = 2)
    private float discount;
    
    @Column(name = "tax_bracket",precision = 2)
    private float taxBracket;
    

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="shop_id")
    private Shop shop;

    public Shop getShop() {
        return shop;
    }

    
    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
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

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", quantity=" + quantity + ", price=" + price + ", discount=" + discount + ", taxBracket=" + taxBracket + '}';
    }
    
    
    
}
