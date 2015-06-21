/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kirana.dao;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.kirana.model.Order;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author nikhilvs
 */
public class OrderDaoImpl implements OrderDao {

    private static final Logger log = Logger.getLogger(OrderDaoImpl.class);
    private static final AmazonDynamoDBClient dbClient =
        new AmazonDynamoDBClient(new ClasspathPropertiesFileCredentialsProvider());

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
    public Order getOrderByShopId(long id, String createdAt) throws Exception {
        DynamoDBMapper mapper = new DynamoDBMapper(dbClient);
        return mapper.load(Order.class,id,createdAt);
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
            .withRangeKeyCondition("ReplyDateTime", rangeKeyCondition);
        List<Order> latestReplies = mapper.query(Order.class, queryExpression);
        return latestReplies;
    }

    @Override
    public boolean deleteOrder(long id, String createdAt) throws Exception {
        boolean status=false;
        DynamoDBMapper mapper = new DynamoDBMapper(dbClient);
        mapper.delete(Order.class,new DynamoDBMapperConfig(DynamoDBMapperConfig.SaveBehavior.CLOBBER));
        status=true;
        return status;
    }

    
}
