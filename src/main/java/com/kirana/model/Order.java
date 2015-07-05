package com.kirana.model;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshaller;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshalling;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.wordnik.swagger.annotations.ApiModelProperty;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.HashMap;








@DynamoDBTable(tableName="order")
public  class Order implements Serializable {
    
    
    
    @ApiModelProperty(hidden = true, required=false)
    private Long shopId;
    
    @ApiModelProperty(notes = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z' --> 2001-07-04T12:08:56.235-0700")
    private String createdAt;
    
    @ApiModelProperty(notes = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z' --> 2001-07-04T12:08:56.235-0700")
    private String updatedAt;
    
    @ApiModelProperty(notes = "Hash of productid and quantity, ex: {'idly':1,'porotta':2}")
    private HashMap<String,Integer> orderList;
    
    @ApiModelProperty(notes = "Extra info like geo location....")
    private HashMap<String,String> extraInfo;

    public Order(Long shopId) {
        this.shopId=shopId;
    }

    public Order() {
    }

    @DynamoDBMarshalling(marshallerClass = OrderListConverter.class) 
    public HashMap<String, Integer> getOrderList() {return orderList; }
    public void setOrderList(HashMap<String, Integer> orderList) {this.orderList = orderList; }

    
    @DynamoDBMarshalling(marshallerClass = ExtraInfoConverter.class) 
    public HashMap<String, String> getExtraInfo() {return extraInfo;}
    public void setExtraInfo(HashMap<String, String> extraInfo) {this.extraInfo = extraInfo; }
    
    @DynamoDBRangeKey(attributeName="created_at")
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    
    @DynamoDBHashKey(attributeName="shop_id")
    public Long getShopId() { return shopId; }
    public void setShopId(Long shopId) {this.shopId = shopId; }

    @DynamoDBAttribute(attributeName = "updated_at")
    public String getUpdatedAt() {return updatedAt; }
    public void setUpdatedAt(String updatedAt) {this.updatedAt = updatedAt; }
   
    
     
    @Override
    public String toString() {
        return "Order{" + "shopId=" + shopId + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }
    
    
    
    static public class OrderListConverter implements DynamoDBMarshaller<HashMap<String,Integer>> {
        @Override
        public String marshall(HashMap<String,Integer> getterReturnResult) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            XMLEncoder xmlEncoder = new XMLEncoder(bos);
            xmlEncoder.writeObject(getterReturnResult);
            xmlEncoder.flush();
             return bos.toString();
        }
        @Override
        public HashMap<String,Integer> unmarshall(Class<HashMap<String,Integer>> clazz, String obj) {
            XMLDecoder xmlDecoder = new XMLDecoder(new ByteArrayInputStream(obj.getBytes()));
            HashMap<String,Integer> list = (HashMap<String,Integer>) xmlDecoder.readObject();
            return list;
        }
    }
    
    static public class ExtraInfoConverter implements DynamoDBMarshaller<HashMap<String,String>> {
        @Override
        public String marshall(HashMap<String,String> getterReturnResult) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            XMLEncoder xmlEncoder = new XMLEncoder(bos);
            xmlEncoder.writeObject(getterReturnResult);
            xmlEncoder.flush();
             return bos.toString();
        }
        @Override
        public HashMap<String,String> unmarshall(Class<HashMap<String,String>> clazz, String obj) {
            XMLDecoder xmlDecoder = new XMLDecoder(new ByteArrayInputStream(obj.getBytes()));
            HashMap<String,String> list = (HashMap<String,String>) xmlDecoder.readObject();
            return list;
        }
    }
    

}
