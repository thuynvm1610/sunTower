package com.estate.service.impl;

import com.estate.service.ImageStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@Profile("local")   // chỉ active khi profile = local
public class LocalImageStorageService implements ImageStorageService {

    // map folder name → upload-dir config
    @Value("${building.image.upload-dir}")
    private String buildingDir;

    @Value("${staff.image.upload-dir}")
    private String staffDir;

    @Override
    public String store(MultipartFile file, String folder) throws Exception {
        String dir = switch (folder) {
            case "building" -> buildingDir;
            case "staff"    -> staffDir;
            default         -> throw new IllegalArgumentException("Unknown folder: " + folder);
        };

        String originalName = file.getOriginalFilename() != null
                ? file.getOriginalFilename().toLowerCase() : "";
        String ext = originalName.substring(originalName.lastIndexOf('.'));
        String newFilename = UUID.randomUUID().toString().replace("-", "") + ext;

        Path uploadPath = Paths.get(dir).toAbsolutePath();
        Files.createDirectories(uploadPath);
        Files.copy(file.getInputStream(), uploadPath.resolve(newFilename),
                StandardCopyOption.REPLACE_EXISTING);

        return newFilename;   // trả về filename
    }
}