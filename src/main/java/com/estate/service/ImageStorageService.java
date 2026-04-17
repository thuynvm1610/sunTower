package com.estate.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageStorageService {
    /**
     * Lưu file ảnh, trả về filename/url để lưu vào DB.
     */
    String store(MultipartFile file, String folder) throws Exception;
}