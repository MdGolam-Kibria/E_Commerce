package com.example.demo.service;

import com.example.demo.view.Response;
import org.springframework.web.multipart.MultipartFile;

public interface FileManageService {
     Response saveFile(MultipartFile file);
}
