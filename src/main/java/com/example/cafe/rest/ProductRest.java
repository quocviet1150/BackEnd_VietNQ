package com.example.cafe.rest;

import com.example.cafe.wrapper.ProductWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/product")
public interface ProductRest {

    @PostMapping(path = "create_product")
    ResponseEntity<String> createProduct(@RequestBody(required = true) Map<String, String> requestMap);

    @GetMapping(path = "/get_product")
    ResponseEntity<List<ProductWrapper>> getAllProduct();

    @PostMapping(path = "/update_product")
    ResponseEntity<String> updateProduct(@RequestBody(required = true) Map<String, String> requestMap);

    @PostMapping(path = "/delete_product/{id}")
    ResponseEntity<String> deleteProduct(@PathVariable Integer id);

    @PostMapping(path = "/update_status")
    ResponseEntity<String> updateStatus(@RequestBody(required = true) Map<String, String> requestMap);

    @GetMapping(path = "/get_by_category/{id}")
    ResponseEntity<List<ProductWrapper>> getByCategory(@PathVariable Integer id);

    @GetMapping(path = "get_by_id/{id}")
    ResponseEntity<ProductWrapper> getByIdProduct(@PathVariable Integer id);
}