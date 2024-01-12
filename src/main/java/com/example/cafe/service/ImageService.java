package com.example.cafe.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    ResponseEntity<String> uploadImage(String name, MultipartFile file);

    ResponseEntity<String> deleteImage(String fileName);

    ResponseEntity<Object> getImage(String fileName);
}
