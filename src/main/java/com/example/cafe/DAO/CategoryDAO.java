package com.example.cafe.DAO;

import com.example.cafe.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CategoryDAO extends JpaRepository<Category, Integer> {

    List<Category> getAllCategory();

    List<Category> getAllCategoryStatus();

    //    List<Integer> deleteCategory();
    @Transactional
    @Modifying
    Integer updateStatus(@Param("status") String status, @Param("id") Integer id);


}
