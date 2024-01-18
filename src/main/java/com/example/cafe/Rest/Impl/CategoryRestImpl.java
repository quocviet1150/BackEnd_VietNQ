package com.example.cafe.Rest.Impl;

import com.example.cafe.Entity.Category;
import com.example.cafe.Constants.CafeConstants;
import com.example.cafe.Rest.CategoryRest;
import com.example.cafe.Service.CategoryService;
import com.example.cafe.Utils.ProjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class CategoryRestImpl implements CategoryRest {

    @Autowired
    CategoryService categoryService;

    @Override
    public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
        try {
            return categoryService.getAllCategory(filterValue);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<List<Category>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategoryStatus() {
        try {
            return categoryService.getAllCategoryStatus();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<List<Category>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> createCategory(Map<String, String> requestMap) {
        try {
            return categoryService.createCategory(requestMap);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return ProjectUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @Override
//    public ResponseEntity<String> deleteCategory(List<Integer> categoryIds) {
//        try {
//            return categoryService.deleteCategory(categoryIds);
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//        return CafaUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
        try {
            return categoryService.updateCategory(requestMap);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return ProjectUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            return categoryService.update(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ProjectUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
