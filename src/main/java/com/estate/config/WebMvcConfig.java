package com.estate.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
@Profile("local")  // ← Thêm dòng này
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${building.image.upload-dir}")
    private String buildingDir;

    @Value("${staff.image.upload-dir}")
    private String staffDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/building_img/**")
                .addResourceLocations(
                        "file:" + new File(buildingDir).getAbsolutePath() + File.separator
                );

        registry.addResourceHandler("/images/staff_img/**")
                .addResourceLocations(
                        "file:" + new File(staffDir).getAbsolutePath() + File.separator
                );
    }
}