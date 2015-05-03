package com.kirana.controller;

import com.kirana.controller.utils.ChangeRoleParam;
import com.kirana.controller.utils.ChangeRoleParamValidator;
import com.kirana.utils.Response;
import com.kirana.controller.utils.RegisterParamValidator;
import com.kirana.model.User;
import com.kirana.services.UserServices;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kirana.controller.utils.LoginParam;
import com.kirana.controller.utils.LoginParamValidator;
import com.kirana.utils.GlobalConfig;
import com.kirana.utils.ParameterException;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/v1/user")
public class UserController {

    private static final Logger log = Logger.getLogger(UserController.class);
    
    @Autowired
    private UserServices userServices;
    
    AuthenticationManager manager =null;

    /**
     * Simply selects the home view to render by returning its name.
     *
     * @param locale
     * @param model
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Locale locale, Model model) {
        String version = "N/A";
        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
        String formattedDate = dateFormat.format(date);
        log.info("Welcome home (UserController) ! The client locale is {}. + version :- " + version);
        model.addAttribute("serverTime", formattedDate + " sdfsdf Version :- " + version);
        return "home";
    }


    
    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Response> deleteUser(@PathVariable("id") long id) {

        try {
            userServices.deleteEntity(id);
            return new ResponseEntity<>(new Response(HttpStatus.OK.value(), "User deleted Successfully !"), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e, e);
            return new ResponseEntity<>(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(),GlobalConfig.FAILURE_MESSAGE), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Response> getUser(@PathVariable("id") long id) {
            
            try {
                    
                    User user = userServices.getEntityById(id);
                    System.out.println("user :"+user.toString());
                    return new ResponseEntity<>(new Response(HttpStatus.OK.value(),GlobalConfig.MINOR_OK,GlobalConfig.SUCCESS_MESSAGE,user), HttpStatus.OK);
            } catch (Exception e) {
                log.error(e,e);
                return new ResponseEntity<>(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(),GlobalConfig.FAILURE_MESSAGE), HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }
    
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Response>        
     getUser() {
            try {
                    List<User> userList = userList = userServices.getEntityList();
                    return new ResponseEntity<>(new Response(HttpStatus.OK.value(),GlobalConfig.MINOR_OK,GlobalConfig.SUCCESS_MESSAGE,userList), HttpStatus.OK);
            } catch (Exception e) {
                log.error(e,e);
                return new ResponseEntity<>(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(),GlobalConfig.FAILURE_MESSAGE), HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }

    /*
     * Login query
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, headers = "Accept=application/json")
    public @ResponseBody
    ResponseEntity<Response> login(@RequestParam("email") String email, @RequestParam("password") String passWord) {

        try {
            LoginParam param = new LoginParam(email, passWord);
            BeanPropertyBindingResult result = new BeanPropertyBindingResult(param, "login");
            ValidationUtils.invokeValidator(new LoginParamValidator(), param, result);
            if (result.getErrorCount() >= 1) {
                return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST.value(),new ParameterException(result.getAllErrors().toString())), HttpStatus.BAD_REQUEST);
            }
            
            User user = userServices.getLoginInfo(email,passWord);
            if(user!=null)
            {
                
                return new ResponseEntity<>(new Response(HttpStatus.OK.value(),GlobalConfig.MINOR_OK,GlobalConfig.SUCCESS_MESSAGE,user), HttpStatus.OK);
            }
            else
            {
                 return new ResponseEntity<>(new Response(HttpStatus.NOT_FOUND.value(),GlobalConfig.FAILURE_MESSAGE), HttpStatus.NOT_FOUND);
            }  
        } catch (Exception ex) {
            log.error(ex,ex);
            return new ResponseEntity<>(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(),GlobalConfig.FAILURE_MESSAGE), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * Register Query
     */
    //street	state	number	TIN number	Phone Number	Email Address	web site
    @RequestMapping(value = "/register", method = RequestMethod.POST,  consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Response> register(@RequestBody User user) {
        
        try {
            BeanPropertyBindingResult result = new BeanPropertyBindingResult(user, "register");
            ValidationUtils.invokeValidator(new RegisterParamValidator(), user, result);
            if (result.getErrorCount() >= 1) {
                return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST.value(),new ParameterException(result.getAllErrors().toString())), HttpStatus.BAD_REQUEST);
            }
            userServices.addEntity(user) ;
            return new ResponseEntity<>(new Response(HttpStatus.OK.value(),GlobalConfig.MINOR_OK,GlobalConfig.SUCCESS_MESSAGE,user), HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex,ex);
            return new ResponseEntity<>(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(),GlobalConfig.FAILURE_MESSAGE), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @RequestMapping(value = "/forgot-password", method = RequestMethod.POST, headers = "Accept=application/json")
//    public @ResponseBody
//    ResponseEntity<Response> forgotPassword(@RequestParam("username") String userName, @RequestParam("api-token") String apiToken, Model model) {
//        HashMap<String, String> map = new HashMap<String, String>();
//        map.put(userName, "username");
//        map.put(apiToken, "api-token");
//        GeneralParam param = new GeneralParam(map);
//        BeanPropertyBindingResult result = new BeanPropertyBindingResult(param, "forgot-password");
//        ValidationUtils.invokeValidator(new GeneralValidator(), param, result);
//        if (result.getErrorCount() >= 1) {
//            throw new ParameterException(result.getAllErrors().toString());
//        }
//        return "not implemented yet";
//    }
//
    @RequestMapping(value = "/change-role", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Response> changeRole(@RequestBody ChangeRoleParam param) {
        
        try {
            //validation
            BeanPropertyBindingResult result = new BeanPropertyBindingResult(param, "change-role");
            ValidationUtils.invokeValidator(new ChangeRoleParamValidator(), param, result);
            if (result.getErrorCount() >= 1) {
                return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST.value(),new ParameterException(result.getAllErrors().toString())), HttpStatus.BAD_REQUEST);
            }
            
            
            
            //authentication and authorization 
            
            User user = userServices.getUsernfo(param.getUserName(),param.getUserToken());
            if(user!=null )
            {
                user.setUserRole(param.getUserRole());
                if(userServices.updateUser(user))
                    return new ResponseEntity<>(new Response(HttpStatus.OK.value(),GlobalConfig.MINOR_OK,GlobalConfig.SUCCESS_MESSAGE,user), HttpStatus.OK);
                else
                    return new ResponseEntity<>(new Response(HttpStatus.NOT_MODIFIED.value(),GlobalConfig.MINOR_OK,GlobalConfig.FAILURE_MESSAGE,user), HttpStatus.NOT_MODIFIED);
            }
            else
            { 
                return new ResponseEntity<>(new Response(HttpStatus.NOT_FOUND.value(),GlobalConfig.FAILURE_MESSAGE), HttpStatus.NOT_FOUND);
            }
            
        } catch (Exception ex) {
            log.error(ex,ex);
            return new ResponseEntity<>(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(),GlobalConfig.FAILURE_MESSAGE), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
