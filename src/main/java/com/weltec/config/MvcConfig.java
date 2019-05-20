package com.weltec.config;

import com.weltec.components.AuthLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;


/**
 * Description:
 * Author: Liu
 * Date: 2019-05-02 16:34
 */
@Configuration
public class MvcConfig {

    @Autowired
    AuthLogin authLogin;

    @Bean
    public WebMvcConfigurer WebMvcConfigurer(){
        WebMvcConfigurer webMvcConfigurer = new WebMvcConfigurer (){
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("login");
                registry.addViewController("/index.html").setViewName("login");
                registry.addViewController ("/dashboard").setViewName ("admin/dashboard");
                registry.addViewController ("/add").setViewName ("admin/addProd");

            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(authLogin).addPathPatterns("/**")
                      .excludePathPatterns("/index.html","/","/manage/login","/api/**","/css/**","/js/**",
                              "/img/**","/webjars/**","/upload/**");
            }
        };
        return  webMvcConfigurer;
    }
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
