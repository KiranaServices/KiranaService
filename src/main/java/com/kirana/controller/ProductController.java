package com.kirana.controller;

import au.com.bytecode.opencsv.CSVParser;
import au.com.bytecode.opencsv.CSVReader;
import com.kirana.controller.utils.AuthenticationException;
import com.kirana.controller.utils.Authorization;
import com.kirana.controller.utils.AuthorizationException;
import com.kirana.controller.utils.ShopRegisterParamValidator;
import com.kirana.utils.Response;
import com.kirana.model.User;
import com.kirana.services.UserServices;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

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
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
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

    AuthenticationManager manager = null;

    /**
     * Simply selects the home view to render by returning its name.
     *
     * @param locale
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Locale locale) {
        String version = "N/A";
        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
        String formattedDate = dateFormat.format(date);
        String result = "Welcome home (ShopController) ! The client locale is {}. + version :- " + version;
        log.info(result);
        return result;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody
    String handleFileUpload(@RequestParam("userToken") String userToken,
            @RequestParam("file") MultipartFile file) {

        StringBuilder fileContent = new StringBuilder();
        if (!file.isEmpty()) {
            try {
                File readerFile = File.createTempFile(file.getName(), "1");
                file.transferTo(readerFile);
                CSVReader reader = new CSVReader(new FileReader(readerFile));
                String[] nextLine;
                while ((nextLine = reader.readNext()) != null) {
                    log.info(nextLine[0] + ":" + nextLine[1] + ": etc...");
                    for (String word : nextLine) {
                        fileContent.append(word).append("\n");
                    }
                }
                return "You successfully uploaded " + fileContent + "!";
            } catch (IOException | IllegalStateException e) {
                return "You failed to upload " + userToken + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + userToken + " because the file was empty.";
        }
    }

}
