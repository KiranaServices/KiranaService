/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kirana.dao;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.kirana.model.Order;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import org.apache.log4j.Logger;

/**
 *
 * @author nikhilvs
 */
public class OrderDaoImpl implements OrderDao {

    private static final Logger log = Logger.getLogger(OrderDaoImpl.class);
    private static final AmazonDynamoDBClient dbClient =
        new AmazonDynamoDBClient(new ClasspathPropertiesFileCredentialsProvider());
    
    static{
    dbClient.setRegion(Region.getRegion(Regions.AP_SOUTHEAST_1));     
    }
    

    @Override
    public boolean addOrder(Order order) throws Exception {
        boolean status=false;
        DynamoDBMapper mapper = new DynamoDBMapper(dbClient);
        mapper.save(order);
        status=true;
        return status;
    }



    @Override
    public boolean updateOrder(Order order) throws Exception {
        boolean status=false;
        DynamoDBMapper mapper = new DynamoDBMapper(dbClient);
        mapper.save(order,new DynamoDBMapperConfig(DynamoDBMapperConfig.SaveBehavior.UPDATE_SKIP_NULL_ATTRIBUTES));
        status=true;
        return status;
    }

    @Override
    public List<Order> getOrderByShopId(long shopId) throws Exception {
        DynamoDBMapper mapper = new DynamoDBMapper(dbClient);
        
        Date startTime = new Date(0L);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        String startTimeStr = dateFormatter.format(startTime);

                
        Condition rangeKeyCondition = new Condition()
            .withComparisonOperator(ComparisonOperator.GT.toString())
            .withAttributeValueList(new AttributeValue().withS(startTimeStr));

        
       DynamoDBQueryExpression<Order> queryExpression = new DynamoDBQueryExpression<Order>()
            .withHashKeyValues(new Order(shopId))
            .withRangeKeyCondition("created_at", rangeKeyCondition);
            
        return mapper.query(Order.class, queryExpression);
        
    }

    @Override
    public List<Order> getOrderBetween(long id, String FromDate, String ToDate) throws Exception {
        
        DynamoDBMapper mapper = new DynamoDBMapper(dbClient);
        Condition rangeKeyCondition = new Condition()
            .withComparisonOperator(ComparisonOperator.BETWEEN.toString())
            .withAttributeValueList(new AttributeValue().withS(FromDate),new AttributeValue().withS(ToDate));
        Order replyKey = new Order();
        replyKey.setShopId(id);
        DynamoDBQueryExpression<Order> queryExpression = new DynamoDBQueryExpression<Order>()
            .withHashKeyValues(replyKey)
            .withRangeKeyCondition("created_at", rangeKeyCondition);
        List<Order> latestReplies = mapper.query(Order.class, queryExpression);
        return latestReplies;
    }

    @Override
    public boolean deleteOrder(long id, String createdAt) throws Exception {
        boolean status=false;
        DynamoDBMapper mapper = new DynamoDBMapper(dbClient);
        Order order = mapper.load(Order.class,id,createdAt);
        if(order!=null)
        {
            mapper.delete(order,new DynamoDBMapperConfig(DynamoDBMapperConfig.SaveBehavior.CLOBBER));
            status=true;
        }   
        return status;
    }

    @Override
    public Order getOrderById(String id) throws Exception {
        DynamoDBMapper mapper = new DynamoDBMapper(dbClient);
        return mapper.load(Order.class,id);
    }

    @Override
    public Order getOrderByCreatedAt(long id, String createdAt) throws Exception {
        DynamoDBMapper mapper = new DynamoDBMapper(dbClient);
        log.info("id : "+id+"  createdat:"+createdAt);
        return mapper.load(Order.class,id,createdAt);
    }

    // 2001-07-04T12:08:57.235Z
    // 2001-07-04T12:08:57.235Z
    // 2001-07-04T12:08:57
    
}
