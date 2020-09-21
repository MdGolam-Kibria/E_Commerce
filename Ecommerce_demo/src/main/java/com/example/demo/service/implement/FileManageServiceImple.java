package com.example.demo.service.implement;

import com.example.demo.service.FileManageService;
import com.example.demo.view.Response;
import com.example.demo.view.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service("fileManageService")
public class FileManageServiceImple implements FileManageService {

    private int maxFileSize = 100;//i already have define size 100MB  in properties but i want to specified size for this service

    @Override
    public Response saveFile(MultipartFile file, HttpServletRequest request) {

        final Path root = Paths.get("K:\\final E-commerce project\\E_Commerce\\Ecommerce_demo\\uploads\\kibria local server\\products");

        //tro to control file size with MultipartFile object
        long size = file.getSize();//requested file size

        Long fileSize = size / (1024 * 1024);//converted file size requested size type to MB

        if (fileSize > Long.parseLong(String.valueOf(maxFileSize))) {//this logic only workable for offline not online
            return ResponseBuilder.getFailureResponce(HttpStatus.NOT_ACCEPTABLE, "File size should be less then 100 MB");
        }
        //check file already created or not
        if (!Files.exists(root)) {//if don't have Directories in our system OS
            try {
                Files.createDirectories(root);//create Directories in our system OS
            } catch (IOException e) {
                throw new RuntimeException("Could not initialize folder for upload!");
            }
        }
        //we already have Directories now upload our file to this Directories
        try {
            String timeStamp = String.valueOf(System.currentTimeMillis());
            Path filePath = root.resolve(timeStamp + file.getOriginalFilename());
            Files.copy(file.getInputStream(), filePath);

            System.out.println("This is uploaded file path " + filePath);//this is the currently created file path

            return ResponseBuilder.getSuccessResponce(HttpStatus.OK, "file saved", String.valueOf(filePath));
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }

    }
}
