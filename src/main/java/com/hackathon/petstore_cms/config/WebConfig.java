package com.hackathon.petstore_cms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Get the absolute path to the 'uploaded_images' folder
        String absolutePath = System.getProperty("user.dir") + "/uploaded_images/";
        
        // Map the URL path /images/** to the external folder.
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + absolutePath); 
    }
}