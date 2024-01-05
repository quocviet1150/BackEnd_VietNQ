package com.example.cafe.service;

import com.example.cafe.wrapper.CategoryWrapper;
import com.example.cafe.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface CategoryService {

    ResponseEntity<List<CategoryWrapper>> getAllCategory();

    ResponseEntity<String> createCategory(Map<String, String> requestMap);

    ResponseEntity<String> deleteCategory(List<Integer> categoryIds);
}
