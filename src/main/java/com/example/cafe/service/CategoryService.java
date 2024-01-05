package com.example.cafe.service;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface CategoryService {

    ResponseEntity<String> createCategory(Map<String, String> requestMap);

    ResponseEntity<String> deleteCategory(List<Integer> categoryIds);
}
