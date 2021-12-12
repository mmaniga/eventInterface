package com.c1exchange.meta.EventsInterface.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebMvc
public class CorsFilterConfiguration  implements WebMvcConfigurer {
    @Value(value = "${app.cors.allowed-origins}")
    private String allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        List<String> allowedOriginsArray = Arrays.asList(allowedOrigins.split(","));
        System.out.println("allowedOriginsArray = " + allowedOrigins.toString());
        //allowedOriginsArray.add("localhost2");
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedOriginPatterns(allowedOriginsArray.toString())
                .allowedMethods("*");
    }

}

