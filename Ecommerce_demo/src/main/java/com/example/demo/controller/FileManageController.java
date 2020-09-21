package com.example.demo.controller;

import com.example.demo.annotation.*;

import com.example.demo.service.FileManageService;
import com.example.demo.util.UrlConstraint;
import com.example.demo.view.Response;
import com.example.demo.view.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@ApiController
@RequestMapping(UrlConstraint.FileManagement.ROOT)
public class FileManageController {
    private final FileManageService fileManageService;

    @Autowired
    public FileManageController(FileManageService fileManageService) {
        this.fileManageService = fileManageService;
    }

    @IsAdmin
    @PostMapping(UrlConstraint.FileManagement.SAVE)
    public Response saveImage(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        return fileManageService.saveFile(file, request);
    }
}
