package com.example.instagram.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private static final List<String> ALLOWED_EXTENSIONS =
            Arrays.asList(".jpg", ".jpeg", ".png", ".gif");

    @Override
    public String saveFile(MultipartFile file) {
        try{
            if (file == null || file.isEmpty()) {
                return null;
            }

            String originalFilename = file.getOriginalFilename();
            String extension = getExtension(originalFilename);

            if(!ALLOWED_EXTENSIONS.contains(extension.toLowerCase())){
                throw new RuntimeException("Invalid file extension");
            }

            Path uploadPath = Paths.get(uploadDir);
            if(!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }
            String savedFileName = UUID.randomUUID() + extension;

            Path filePath = uploadPath.resolve(savedFileName); // uploads/78132ghf-1789ghfa.jpg
            Files.copy(file.getInputStream(), filePath);

            return savedFileName;


        } catch (IOException e) {
            throw new RuntimeException("파일 저장중 오류 발생");
        }
    }

    private String getExtension(String filename) {
        if(filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf("."));
    }
}
