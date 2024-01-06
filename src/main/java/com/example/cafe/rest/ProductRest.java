package com.example.cafe.rest;

import com.example.cafe.wrapper.ProductWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/product")
public interface ProductRest {

    @PostMapping(path = "create_product")
    ResponseEntity<String> createProduct(@RequestBody(required = true) Map<String, String> requestMap);

    @GetMapping(path = "/get_product")
    ResponseEntity<List<ProductWrapper>> getAllProduct();
}
