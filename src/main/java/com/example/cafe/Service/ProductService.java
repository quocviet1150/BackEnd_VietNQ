package com.example.cafe.Service;

import com.example.cafe.DTO.ProductDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ProductService {
    ResponseEntity<String> createProduct(Map<String, String> requestMap);

    ResponseEntity<List<ProductDTO>> getAllProduct();

    ResponseEntity<String> updateProduct(Map<String, String> requestMap);

    ResponseEntity<String> deleteProduct(Integer id);

    ResponseEntity<String> updateStatus(Map<String, String> requestMap);

    ResponseEntity<List<ProductDTO>> getByCategory(Integer id);

    ResponseEntity<ProductDTO> getByIdProduct(Integer id);

}
