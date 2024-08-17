package com.ecommerce.project.sbECom.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String uploadImage(String path, MultipartFile image) throws IOException;
}
