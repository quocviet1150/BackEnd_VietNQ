package com.example.cafe.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ImageService {

    ResponseEntity<String> uploadImage(String name,String description, MultipartFile file);

    ResponseEntity<String> update(String name,String description, MultipartFile file);

    ResponseEntity<String> deleteImage(Integer id);

    ResponseEntity<Object> getImage();

    ResponseEntity<Object> getImageAll();

    ResponseEntity<String> update(Map<String, String> requestMap);
}
