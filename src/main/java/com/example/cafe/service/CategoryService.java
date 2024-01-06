package com.example.cafe.service;

import com.example.cafe.Entity.Category;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface CategoryService {

    ResponseEntity<List<Category>> getAllCategory(String filterValue);

    ResponseEntity<String> createCategory(Map<String, String> requestMap);

//    ResponseEntity<String> deleteCategory(List<Integer> categoryIds);

    ResponseEntity<String> updateCategory(Map<String, String> requestMap);
}