package com.example.cafe.dao;

import com.example.cafe.Entity.Product;
import com.example.cafe.wrapper.ProductWrapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, Integer> {

    List<ProductWrapper> getAllProduct();
}
