package com.example.cafe.dao;

import com.example.cafe.Entity.Category;
import com.example.cafe.wrapper.CategoryWrapper;
import com.example.cafe.wrapper.UserWrapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryDao extends JpaRepository<Category, Integer> {

    List<CategoryWrapper> getAllCategory();

}
