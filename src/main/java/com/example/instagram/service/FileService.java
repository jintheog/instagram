package com.example.instagram.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileService {
    String saveFile(MultipartFile file);
}
