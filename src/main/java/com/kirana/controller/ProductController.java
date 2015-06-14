package com.kirana.controller;

import com.kirana.controller.utils.AuthenticationException;
import com.kirana.controller.utils.Authorization;
import com.kirana.controller.utils.AuthorizationException;
import com.kirana.controller.utils.ProductRegisterParam;
import com.kirana.controller.utils.ProductRegisterParamValidator;
import com.kirana.model.Product;
import com.kirana.model.Shop;
import com.kirana.model.User;
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
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * Handles requests for the application home page.
 */
@Controller
@Api(value = "product", description = "Product operations", produces = "application/json")
@RequestMapping("/v1/product")
public class ProductController {

    private static final Logger log = Logger.getLogger(ProductController.class);

    @Autowired
    private UserServices userServices;

    @Autowired
    private ShopServices shopServices;
    
    
    @Autowired
    private ProductServices productServices;

    AuthenticationManager manager = null;

    /**
     * Simply selects the home view to render by returning its name.
     * @param userToken
     * @return
     */
    
    @ApiOperation(value = "View products of currently logged user")
    @RequestMapping(value = "/own", method = RequestMethod.GET)
    public ResponseEntity home(@RequestParam("userToken") String userToken) {
        
        try {
            User user = userServices.isAuthenticatedUser(userToken, Authorization.PRODUCT_OWN);
            List<Product> userList = new ArrayList<>();
            Shop shop = user.getShop();
            if(user.getShop()==null)
                    return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST.value(),"No shop created for this user"), HttpStatus.BAD_REQUEST);
            userList.addAll(productServices.getProductListByShopId(shop.getId()));
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

    @ApiOperation(value = "Product register for an shop, since there are many products in an shop,query expects an csv file", notes = "csv file format --> 'product_id,quantity,price,discount,tax_bracket'")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity handleFileUpload(@RequestParam("userToken") String userToken,
            @RequestParam("file") MultipartFile file) {

        try {
            if (!file.isEmpty()) {
                User user = userServices.isAuthenticatedUser(userToken, Authorization.SHOP_DELETE);
                if(user.getShop()==null)
                    return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST.value(),"No shop created for this user"), HttpStatus.BAD_REQUEST);
                StringBuilder fileContent = new StringBuilder();
                File productsFile = File.createTempFile(file.getName(), "csv");
                file.transferTo(productsFile);
                ProductRegisterParam param = new ProductRegisterParam(userToken,productsFile);
                BeanPropertyBindingResult result = new BeanPropertyBindingResult(param, "login");
                ValidationUtils.invokeValidator(new ProductRegisterParamValidator(), param, result);
                if (result.getErrorCount() >= 1) {
                    return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST.value(),result.getAllErrors().toString()), HttpStatus.BAD_REQUEST);
                }
                productServices.addProductBulk(productsFile, user.getShop());
                return new ResponseEntity<>(new Response(HttpStatus.OK.value(), "Product added Successfully !"), HttpStatus.OK);
            } else {
                throw new ParameterException("csv file required");
            }
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
