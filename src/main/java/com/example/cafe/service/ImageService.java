package com.example.cafe.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    ResponseEntity<String> uploadImage(String name,String description, MultipartFile file);

    ResponseEntity<String> deleteImage(Integer id);

    ResponseEntity<Object> getImage();
}
