package com.example.demo.service;

import com.example.demo.view.Response;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface FileManageService {
     Response saveFile(MultipartFile file, HttpServletRequest request);
}
