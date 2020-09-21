package com.example.demo.controller;

import com.example.demo.annotation.*;

import com.example.demo.service.FileManageService;
import com.example.demo.util.UrlConstraint;
import com.example.demo.view.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@ApiController
@RequestMapping(UrlConstraint.FileManagement.ROOT)
public class FileManageController {
    private final FileManageService fileManageService;

    @Autowired
    public FileManageController(FileManageService fileManageService) {
        this.fileManageService = fileManageService;
    }

    @PostMapping(UrlConstraint.FileManagement.SAVE)
    public Response saveImage(@RequestParam("file") MultipartFile file) {
        return fileManageService.saveFile(file);
    }
}
