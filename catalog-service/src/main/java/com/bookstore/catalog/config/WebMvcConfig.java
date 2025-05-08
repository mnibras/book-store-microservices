package com.bookstore.catalog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
class WebMvcConfig implements WebMvcConfigurer {

    /**
     * NOTE: This CORS config is ONLY needed when this service is access through swagger-ui
     *       When this service is invoked from api-gateway, there is NO CORS issue (Not browser request)
     *       When frontend invoke this service, CORS configs are added in the api-gateway properties file to allow requests from browser
     * @param registry CorsRegistry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowedOriginPatterns("*")
                .allowCredentials(false);
    }
}
