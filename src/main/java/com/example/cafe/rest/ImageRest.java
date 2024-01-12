package com.example.cafe.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping(path = "image")
public interface ImageRest {

    @PostMapping("/upload")
    ResponseEntity<String> uploadImage(@RequestParam String name, @RequestParam MultipartFile file);

    @DeleteMapping("/delete/{fileName}")
    ResponseEntity<String> deleteImage( @PathVariable String fileName);
}
