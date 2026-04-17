package com.estate.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ImageUrlResolver {

    @Value("${app.image.base-url:}")
    private String baseUrl;

    public String resolve(String image, String folder) {
        if (image == null) return null;
        if (image.startsWith("http")) return image;
        return baseUrl + "/images/" + folder + "_img/" + image;
    }
}