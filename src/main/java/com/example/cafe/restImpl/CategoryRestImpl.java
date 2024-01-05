package com.example.cafe.restImpl;

import com.example.cafe.constents.CafeConstants;
import com.example.cafe.rest.CategoryRest;
import com.example.cafe.service.CategoryService;
import com.example.cafe.utils.CafaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class CategoryRestImpl implements CategoryRest {

    @Autowired
    CategoryService categoryService;

    @Override
    public ResponseEntity<String> createCategory(Map<String, String> requestMap) {
        try {
            return categoryService.createCategory(requestMap);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return CafaUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteCategory(List<Integer> categoryIds) {
        try {
            return categoryService.deleteCategory(categoryIds);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return CafaUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
