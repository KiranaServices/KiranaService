///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
///**
// *
// * @author nikhilvs
// */
//package springfoxdemo.xml.swagger;
//
//import org.springframework.web.WebApplicationInitializer;
//import org.springframework.web.servlet.DispatcherServlet;
//
//import javax.servlet.ServletContext;
//import javax.servlet.ServletRegistration;
//
//public class ApplicationInitializer implements WebApplicationInitializer {
//    @Override
//    public void onStartup(ServletContext container) {
//        ServletRegistration.Dynamic registration = container.addServlet("dispatcher", new DispatcherServlet());
//        registration.setLoadOnStartup(1);
//        registration.addMapping("/*");
//    }
//}