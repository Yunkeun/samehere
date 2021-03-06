package com.giggle.samehere.config;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${cards.profile.image.upload.folder}")
    private String cardUploadFolderName;

    @Value("${cors.allowed.origins}")
    private String[] allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns(allowedOrigins)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3000);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        final Path fileRoot = Paths.get("./" + cardUploadFolderName).toAbsolutePath().normalize();
        registry
                .addResourceHandler("/" + cardUploadFolderName + "/**")
                .addResourceLocations(fileRoot.toUri().toString())
                .resourceChain(true)
                .addResolver(new Utf8DecodeResourceResolver());
    }
}

