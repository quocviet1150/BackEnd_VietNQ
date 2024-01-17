package com.example.cafe.DAO;

import com.example.cafe.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryDAO extends JpaRepository<Category, Integer> {

    List<Category> getAllCategory();

//    List<Integer> deleteCategory();

}
