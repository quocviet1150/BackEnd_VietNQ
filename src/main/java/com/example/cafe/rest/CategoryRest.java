package com.example.cafe.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping(path = "/category")
public interface CategoryRest {

    @PostMapping(path = "/create_category")
    ResponseEntity<String> createCategory(@RequestBody(required = true) Map<String, String> requestMap);

    @DeleteMapping(path = "/delete_category/{categoryId}")
    ResponseEntity<String> deleteCategory(@PathVariable Integer categoryId);
}
