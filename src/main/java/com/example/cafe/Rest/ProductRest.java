package com.example.cafe.Rest;

import com.example.cafe.DTO.ProductDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/product")
public interface ProductRest {

    @PostMapping(path = "create_product")
    ResponseEntity<String> createProduct(@RequestBody(required = true) Map<String, String> requestMap);

    @GetMapping(path = "/get_product")
    ResponseEntity<List<ProductDTO>> getAllProduct();

    @PostMapping(path = "/update_product")
    ResponseEntity<String> updateProduct(@RequestBody(required = true) Map<String, String> requestMap);

    @PostMapping(path = "/delete_product/{id}")
    ResponseEntity<String> deleteProduct(@PathVariable Integer id);

    @PostMapping(path = "/update_status")
    ResponseEntity<String> updateStatus(@RequestBody(required = true) Map<String, String> requestMap);

    @GetMapping(path = "/get_by_category/{id}")
    ResponseEntity<List<ProductDTO>> getByCategory(@PathVariable Integer id);

    @GetMapping(path = "get_by_id/{id}")
    ResponseEntity<ProductDTO> getByIdProduct(@PathVariable Integer id);

    @PostMapping(path = "/{id}/decrement_quantity/{quantity}")
    ResponseEntity<String> decrementProductQuantity(@PathVariable Integer id, @PathVariable Integer quantity);

    @PostMapping(path = "/{id}/increment_quantity/{quantity}")
    ResponseEntity<String> incrementProductQuantity(@PathVariable Integer id, @PathVariable Integer quantity);

}