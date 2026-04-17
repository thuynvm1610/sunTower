// com/estate/service/impl/CloudinaryImageStorageService.java
package com.estate.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.estate.service.ImageStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryImageStorageService implements ImageStorageService {

    private final Cloudinary cloudinary;  // bean từ CloudinaryConfig

    @Override
    public String store(MultipartFile file, String folder) throws Exception {
        Map<?, ?> result = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap(
                        "folder", folder,          // "building" hoặc "staff"
                        "resource_type", "image"
                )
        );
        // trả về secure_url để lưu DB
        return (String) result.get("secure_url");
    }
}