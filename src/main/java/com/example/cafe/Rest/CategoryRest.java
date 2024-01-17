package com.example.cafe.Rest;

import com.example.cafe.Entity.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/category")
public interface CategoryRest {

    @GetMapping(path = "/get_category")
    ResponseEntity<List<Category>> getAllCategory(@RequestParam(required = false) String filterValue);

    @PostMapping(path = "/create_category")
    ResponseEntity<String> createCategory(@RequestBody(required = true) Map<String, String> requestMap);

//    @DeleteMapping(path = "/delete_category/{categoryIds}")
//    ResponseEntity<String> deleteCategory(@PathVariable List<Integer> categoryIds);

    @PostMapping(path = "/update_category")
    ResponseEntity<String> updateCategory(@RequestBody(required = true) Map<String, String> requestMap);
}
