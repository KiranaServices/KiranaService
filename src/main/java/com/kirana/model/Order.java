package com.kirana.model;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import java.util.Set;








@DynamoDBTable(tableName="order")
public  class Order {
	
    private Long shopId;
    private String createdAt;
    private String updatedAt;
    private Set<String> orderSet;
    
    
    @DynamoDBRangeKey(attributeName="created_at")
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    

    @DynamoDBHashKey(attributeName = "shop_id")
    public Long getShopId() { return shopId; }
    public void setShopId(Long shopId) {this.shopId = shopId; }

    @DynamoDBRangeKey(attributeName = "updated_at")
    public String getUpdatedAt() {return updatedAt; }
    public void setUpdatedAt(String updatedAt) {this.updatedAt = updatedAt; }
    
    @DynamoDBAttribute(attributeName = "order_set")
    public Set<String> getOrderSet() {return orderSet; }
    public void setOrderSet(Set<String> orderSet) {this.orderSet = orderSet;}

    
    @Override
    public String toString() {
        return "Order{" + "shopId=" + shopId + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }

    

}
