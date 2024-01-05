package com.example.cafe.rest;

import com.example.cafe.wrapper.CategoryWrapper;
import com.example.cafe.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/category")
public interface CategoryRest {

    @GetMapping(path = "/get_category")
    ResponseEntity<List<CategoryWrapper>> getAllCategory();

    @PostMapping(path = "/create_category")
    ResponseEntity<String> createCategory(@RequestBody(required = true) Map<String, String> requestMap);

    @DeleteMapping(path = "/delete_category/{categoryIds}")
    ResponseEntity<String> deleteCategory(@PathVariable List<Integer> categoryIds);
}
