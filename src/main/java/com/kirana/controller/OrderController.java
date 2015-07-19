package com.kirana.controller;

import com.kirana.controller.utils.AuthenticationException;
import com.kirana.controller.utils.Authorization;
import com.kirana.controller.utils.AuthorizationException;
import com.kirana.controller.utils.OrderRegisterValidator;
import com.kirana.controller.utils.ProductRegisterParamValidator;
import com.kirana.model.Order;
import com.kirana.model.Shop;
import com.kirana.model.User;
import com.kirana.services.OrderServices;
import com.kirana.services.ProductServices;
import com.kirana.services.UserServices;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kirana.services.ShopServices;
import com.kirana.utils.GlobalConfig;
import com.kirana.utils.ParameterException;
import com.kirana.utils.Response;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Handles requests for the application home page.
 */
@Controller
@Api(value = "order", description = "Order operations", produces = "application/json")
@RequestMapping("/v1/order")
public class OrderController {

    private static final Logger log = Logger.getLogger(OrderController.class);

    @Autowired
    private UserServices userServices;

    @Autowired
    private ShopServices shopServices;
    
    
    @Autowired
    private ProductServices productServices;
    
    @Autowired
    private OrderServices orderServices;

    AuthenticationManager manager = null;

    /**
     * Simply selects the home view to render by returning its name.
     * @param userToken
     * @return
     */
    
    @ApiOperation(value = "View Orders of logged user")
    @RequestMapping(value = "/own", method = RequestMethod.GET)
    public ResponseEntity own(@RequestParam("userToken") String userToken) {
        
        try {
            User user = userServices.isAuthenticatedUser(userToken, Authorization.ORDER_OWN);
            List<Order> userList = new ArrayList<>();
            Shop shop = user.getShop();
            if(user.getShop()==null)
                    return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST.value(),"No shop created for this user"), HttpStatus.BAD_REQUEST);
            userList.addAll(orderServices.getOrderByShopId(shop.getId()));
            return new ResponseEntity<>(new Response(HttpStatus.OK.value(),GlobalConfig.MINOR_OK,GlobalConfig.SUCCESS_MESSAGE,userList), HttpStatus.OK);
        } catch (ParameterException pe) {
            log.warn(pe, pe);
            return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST.value(), pe.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (AuthenticationException ate) {
            log.warn(ate, ate);
            return new ResponseEntity<>(new Response(HttpStatus.UNAUTHORIZED.value(), ate.getMessage()), HttpStatus.UNAUTHORIZED);
        } catch (AuthorizationException aue) {
            log.warn(aue, aue);
            return new ResponseEntity<>(new Response(HttpStatus.FORBIDDEN.value(), aue.getMessage()), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            log.error(e, e);
            return new ResponseEntity<>(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalConfig.FAILURE_MESSAGE), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    @RequestMapping(value = "/createdAt", method = RequestMethod.GET)
    public ResponseEntity getSpecificOrder(@ApiParam(value = "in format yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") @RequestParam("createdAt") String createdAt,@RequestParam("userToken") String userToken) {
        
        try {
            User user = userServices.isAuthenticatedUser(userToken, Authorization.ORDER_OWN);
            List<Order> userList = new ArrayList<>();
            Shop shop = user.getShop();
            if(user.getShop()==null)
                    return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST.value(),"No shop created for this user"), HttpStatus.BAD_REQUEST);
            Order order = orderServices.getOrderByCreatedAt(shop.getId(), createdAt);

            return new ResponseEntity<>(new Response(HttpStatus.OK.value(),GlobalConfig.MINOR_OK,GlobalConfig.SUCCESS_MESSAGE,order), HttpStatus.OK);
        } catch (ParameterException pe) {
            log.warn(pe, pe);
            return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST.value(), pe.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (AuthenticationException ate) {
            log.warn(ate, ate);
            return new ResponseEntity<>(new Response(HttpStatus.UNAUTHORIZED.value(), ate.getMessage()), HttpStatus.UNAUTHORIZED);
        } catch (AuthorizationException aue) {
            log.warn(aue, aue);
            return new ResponseEntity<>(new Response(HttpStatus.FORBIDDEN.value(), aue.getMessage()), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            log.error(e, e);
            return new ResponseEntity<>(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalConfig.FAILURE_MESSAGE), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getOrderById(@ApiParam(value = "Unique order Id") @PathVariable("id") String id,@RequestParam("userToken") String userToken) {
        
        try {
            User user = userServices.isAuthenticatedUser(userToken, Authorization.ORDER_OWN);
            List<Order> userList = new ArrayList<>();
            Shop shop = user.getShop();
            if(shop==null)
                    return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST.value(),"No shop created for this user"), HttpStatus.BAD_REQUEST);
            Order order = orderServices.getOrderById(id);
            
            if(order!=null)
            {
                if(order.getShopId()!=shop.getId())
                   return new ResponseEntity<>(new Response(HttpStatus.NOT_FOUND.value(),"Order Not found"), HttpStatus.NOT_FOUND);
                else
                   return new ResponseEntity<>(new Response(HttpStatus.OK.value(),GlobalConfig.MINOR_OK,GlobalConfig.SUCCESS_MESSAGE,order), HttpStatus.OK);
            }
            else
                return new ResponseEntity<>(new Response(HttpStatus.NOT_FOUND.value(),"Order Not found"), HttpStatus.NOT_FOUND);
        } catch (ParameterException pe) {
            log.warn(pe, pe);
            return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST.value(), pe.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (AuthenticationException ate) {
            log.warn(ate, ate);
            return new ResponseEntity<>(new Response(HttpStatus.UNAUTHORIZED.value(), ate.getMessage()), HttpStatus.UNAUTHORIZED);
        } catch (AuthorizationException aue) {
            log.warn(aue, aue);
            return new ResponseEntity<>(new Response(HttpStatus.FORBIDDEN.value(), aue.getMessage()), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            log.error(e, e);
            return new ResponseEntity<>(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalConfig.FAILURE_MESSAGE), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "create an order", notes = "order ex: created_at,updated_at : yyyy-MM-dd'T'HH:mm:ss.SSS'Z' --> 2001-07-04T12:08:56.235Z, ")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity addOrder(@RequestParam("userToken") String userToken,@Validated @RequestBody Order order) {

        try {
            User user = userServices.isAuthenticatedUser(userToken, Authorization.ORDER_CREATE);
            Shop shop = user.getShop();
            if(shop==null)
                return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST.value(),"User doesn't has shop"), HttpStatus.BAD_REQUEST);

            BeanPropertyBindingResult result = new BeanPropertyBindingResult(order, "Order");
            ValidationUtils.invokeValidator(new OrderRegisterValidator(productServices.getProductListByShopId(shop,false)), order, result);
            if (result.getErrorCount() >= 1) {
                return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST.value(),result.getAllErrors().toString()), HttpStatus.BAD_REQUEST);
            }
            order.setShopId(shop.getId());
            
            log.info("status :"+orderServices.addOrder(order));
            return new ResponseEntity<>(new Response(HttpStatus.OK.value(),GlobalConfig.MINOR_OK,GlobalConfig.SUCCESS_MESSAGE,order), HttpStatus.OK);
        } catch (ParameterException pe) {
            log.warn(pe, pe);
            return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST.value(), pe.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (AuthenticationException ate) {
            log.warn(ate, ate);
            return new ResponseEntity<>(new Response(HttpStatus.UNAUTHORIZED.value(), ate.getMessage()), HttpStatus.UNAUTHORIZED);
        } catch (AuthorizationException aue) {
            log.warn(aue, aue);
            return new ResponseEntity<>(new Response(HttpStatus.FORBIDDEN.value(), aue.getMessage()), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            log.error(e, e);
            return new ResponseEntity<>(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalConfig.FAILURE_MESSAGE), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    @ApiOperation(value = "Delete specific order of  user")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOrder(@ApiParam(value = "Unique order Id") @PathVariable("id") String id,@RequestParam("userToken") String userToken) {
        
        try {
            User user = userServices.isAuthenticatedUser(userToken, Authorization.ORDER_DELETE);
            List<Order> userList = new ArrayList<>();
            Shop shop = user.getShop();
            if(user.getShop()==null)
                    return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST.value(),"No shop created for this user"), HttpStatus.BAD_REQUEST);
            
            boolean status =orderServices.deleteOrder(id);
            return new ResponseEntity<>(new Response(HttpStatus.OK.value(),GlobalConfig.MINOR_OK,GlobalConfig.SUCCESS_MESSAGE,status), HttpStatus.OK);
        } catch (ParameterException pe) {
            log.warn(pe, pe);
            return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST.value(), pe.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (AuthenticationException ate) {
            log.warn(ate, ate);
            return new ResponseEntity<>(new Response(HttpStatus.UNAUTHORIZED.value(), ate.getMessage()), HttpStatus.UNAUTHORIZED);
        } catch (AuthorizationException aue) {
            log.warn(aue, aue);
            return new ResponseEntity<>(new Response(HttpStatus.FORBIDDEN.value(), aue.getMessage()), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            log.error(e, e);
            return new ResponseEntity<>(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalConfig.FAILURE_MESSAGE), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    @ApiOperation(value = "View Orders between")
    
    @RequestMapping(value = "/between", method = RequestMethod.GET)
    public ResponseEntity getBetween(@RequestParam("userToken") String userToken,@ApiParam(value = "in format yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") @RequestParam("FromDate") String FromDate,@ApiParam(value = "in format yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") @RequestParam("ToDate") String ToDate) {
        
        try {
            User user = userServices.isAuthenticatedUser(userToken, Authorization.ORDER_OWN);
            List<Order> userList = new ArrayList<>();
            Shop shop = user.getShop();
            if(user.getShop()==null)
                    return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST.value(),"No shop created for this user"), HttpStatus.BAD_REQUEST);
            userList.addAll(orderServices.getOrderBetween(user.getShop().getId(),FromDate,ToDate));
            return new ResponseEntity<>(new Response(HttpStatus.OK.value(),GlobalConfig.MINOR_OK,GlobalConfig.SUCCESS_MESSAGE,userList), HttpStatus.OK);
        } catch (ParameterException pe) {
            log.warn(pe, pe);
            return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST.value(), pe.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (AuthenticationException ate) {
            log.warn(ate, ate);
            return new ResponseEntity<>(new Response(HttpStatus.UNAUTHORIZED.value(), ate.getMessage()), HttpStatus.UNAUTHORIZED);
        } catch (AuthorizationException aue) {
            log.warn(aue, aue);
            return new ResponseEntity<>(new Response(HttpStatus.FORBIDDEN.value(), aue.getMessage()), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            log.error(e, e);
            return new ResponseEntity<>(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalConfig.FAILURE_MESSAGE), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
