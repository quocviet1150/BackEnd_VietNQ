package com.example.cafe.DAO;

import com.example.cafe.Entity.Product;
import com.example.cafe.DTO.ProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ProductDAO extends JpaRepository<Product, Integer> {

    List<ProductDTO> getAllProduct();

    @Modifying
    @Transactional
    Integer updateStatus(@Param("status") String status, @Param("id") Integer id);

    List<ProductDTO> getProductByCategory(@Param("id") Integer id);

    ProductDTO getByIdProduct(@Param("id") Integer id);
}
