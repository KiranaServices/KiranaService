package com.kirana.controller;

import com.kirana.controller.utils.AuthenticationException;
import com.kirana.controller.utils.Authorization;
import com.kirana.controller.utils.AuthorizationException;
import com.kirana.controller.utils.ShopRegisterParamValidator;
import com.kirana.utils.Response;
import com.kirana.model.User;
import com.kirana.services.UserServices;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kirana.model.Shop;
import com.kirana.services.ShopServices;
import com.kirana.utils.GlobalConfig;
import com.kirana.utils.ParameterException;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Handles requests for the application shop page.
 */
@Controller
@Api(value = "shop", description = "shop operations", produces = "application/json")
@RequestMapping("/v1/shop")
public class ShopController {

    private static final Logger log = Logger.getLogger(ShopController.class);

    @Autowired
    private UserServices userServices;

    @Autowired
    private ShopServices shopServices;

    AuthenticationManager manager = null;

    /**
     * Simply selects the shop view to render by returning its name.
     *
     * @param userToken
     * @return
     */
    
    @ApiOperation(value = "View shop of currently logged user")
    @RequestMapping(value = "/own", method = RequestMethod.GET)
    public ResponseEntity<Response> shop(@RequestParam("userToken") String userToken) {
        try {
            if (userToken != null && userToken.trim().length() == 0) {
                throw new ParameterException("Query Syntax wrong");
            }
            User user = userServices.isAuthenticatedUser(userToken, Authorization.SHOP_OWN);
            return new ResponseEntity<>(new Response(HttpStatus.OK.value(), GlobalConfig.MINOR_OK, GlobalConfig.SUCCESS_MESSAGE, user.getShop()), HttpStatus.OK);
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

    @ApiOperation(value = "(SUPERADMIN)Delete an shop by id")
    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    public @ResponseBody
    ResponseEntity<Response> deleteShop(@PathVariable("id") long id, @RequestParam("userToken") String userToken) {

        try {
            if (userToken != null && userToken.trim().length() == 0) {
                throw new ParameterException("Query Syntax wrong");
            }
            User user = userServices.isAuthenticatedUser(userToken, Authorization.SHOP_DELETE);
            if(shopServices.getShopById(id)==null)
              return new ResponseEntity<>(new Response(HttpStatus.NOT_FOUND.value(), "Shop Not found"), HttpStatus.NOT_FOUND);    

            
            if(shopServices.deleteShop(id))
            {
                user.setShop(null);
                userServices.updateUser(user);
            }
            else
                throw new Exception("Unknown error,not able to delete shop");
            return new ResponseEntity<>(new Response(HttpStatus.OK.value(), "Shop deleted Successfully !"), HttpStatus.OK);
            
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
            return new ResponseEntity<>(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @ApiOperation(value = "(SUPERADMIN)List shop by  id")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Response> getShop(@PathVariable("id") long id, @RequestParam("userToken") String userToken) {

        try {
            if (userToken != null && userToken.trim().length() == 0) {
                throw new ParameterException("Query Syntax wrong");
            }
            userServices.isAuthenticatedUser(userToken, Authorization.SHOP_LIST);
            Shop shop = shopServices.getShopById(id);
            System.out.println("user :" + shop.toString());
            return new ResponseEntity<>(new Response(HttpStatus.OK.value(), GlobalConfig.MINOR_OK, GlobalConfig.SUCCESS_MESSAGE, shop), HttpStatus.OK);
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

    @ApiOperation(value = "(SUPERADMIN)List all shops")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Response>
            getAllShop(@RequestParam("userToken") String userToken) {
        try {
            if (userToken != null && userToken.trim().length() == 0) {
                throw new ParameterException("Query Syntax wrong");
            }
            List<Shop> shopList;
            userServices.isAuthenticatedUser(userToken, Authorization.SHOP_LIST_ALL);
            shopList = shopServices.getShopList();
            return new ResponseEntity<>(new Response(HttpStatus.OK.value(), GlobalConfig.MINOR_OK, GlobalConfig.SUCCESS_MESSAGE, shopList), HttpStatus.OK);
        } catch (ParameterException pe) {
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

    /**
     *
     * @param shop
     * @param userToken
     * @return
     */

    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Response> CreateShop(@Validated @RequestBody Shop shop, @RequestParam("userToken") String userToken) {

        try {
            if (userToken != null && userToken.trim().length() == 0) {
                throw new ParameterException("Query Syntax wrong");
            }
            User user = userServices.isAuthenticatedUser(userToken, Authorization.SHOP_REGISTER);
            if(user.getShop()!=null)
                return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST.value(),"User already has one shop"), HttpStatus.BAD_REQUEST);
            //validation
            BeanPropertyBindingResult result = new BeanPropertyBindingResult(shop, "register");
            ValidationUtils.invokeValidator(new ShopRegisterParamValidator(shopServices), shop, result);
            if (result.getErrorCount() >= 1) {
                return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST.value(), result.getAllErrors().toString()), HttpStatus.BAD_REQUEST);
            }
            //authentication and authorization 
            log.info(shop.toString());
            shopServices.addShop(shop);
            user.setShop(shop);

            if (userServices.updateUser(user)) {
                return new ResponseEntity<>(new Response(HttpStatus.OK.value(), GlobalConfig.MINOR_OK, GlobalConfig.SUCCESS_MESSAGE,userServices.getUsernfo(userToken).getShop()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new Response(HttpStatus.NOT_MODIFIED.value(), GlobalConfig.MINOR_OK, GlobalConfig.FAILURE_MESSAGE, shop), HttpStatus.NOT_MODIFIED);
            }
        } catch (AuthenticationException ate) {
            log.warn(ate, ate);
            return new ResponseEntity<>(new Response(HttpStatus.UNAUTHORIZED.value(), GlobalConfig.FAILURE_MESSAGE), HttpStatus.UNAUTHORIZED);
        } catch (AuthorizationException aue) {
            log.warn(aue, aue);
            return new ResponseEntity<>(new Response(HttpStatus.FORBIDDEN.value(), GlobalConfig.FAILURE_MESSAGE), HttpStatus.FORBIDDEN);
        } catch (Exception ex) {
            log.error(ex, ex);
            return new ResponseEntity<>(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalConfig.FAILURE_MESSAGE), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
