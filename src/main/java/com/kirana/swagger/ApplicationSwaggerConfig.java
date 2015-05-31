package com.kirana.swagger;

///*

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
///**
// *
// * @author nikhilvs
// */


@Configuration
@EnableSwagger //Loads the spring beans required by the framework
public class ApplicationSwaggerConfig {

    
    private SpringSwaggerConfig springSwaggerConfig;

   /**
    * Required to autowire SpringSwaggerConfig
     * @param springSwaggerConfig
    */
   @Autowired
   public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
      this.springSwaggerConfig = springSwaggerConfig;
   }

   /**
    * Every SwaggerSpringMvcPlugin bean is picked up by the swagger-mvc framework - allowing for multiple
    * swagger groups i.e. same code base multiple swagger resource listings.
     * @return 
    */
   @Bean
   public SwaggerSpringMvcPlugin customImplementation(){
      return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
//              .apiInfo(null)
              .includePatterns(".*v1.*");
   }
   
//   @Bean
//   public SwaggerSpringMvcPlugin customImplementation1(){
//      return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
//              .includePatterns(".*shop.*");
//   }

}