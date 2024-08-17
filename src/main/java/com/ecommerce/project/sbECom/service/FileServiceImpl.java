package com.ecommerce.project.sbECom.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{

    @Override
    public String uploadImage(String path, MultipartFile image) throws IOException {
        String OriginalName = image.getOriginalFilename();

        String randomId = UUID.randomUUID().toString();
        String newName = randomId.concat(OriginalName.substring(OriginalName.lastIndexOf('.')));
        String imagePath = path + File.separator + newName; //path + "/" + newName;

        //check if path exist and create
        File folder = new File(path);
        if(!folder.exists()){
            folder.mkdir();
        }
        //upload to server
        Files.copy(image.getInputStream() , Paths.get(imagePath));

        return imagePath;
    }
}
