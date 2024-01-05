package com.example.cafe.serviceImpl;

import com.example.cafe.Entity.Category;
import com.example.cafe.JWT.JwtFilter;
import com.example.cafe.constents.CafeConstants;
import com.example.cafe.dao.CategoryDao;
import com.example.cafe.service.CategoryService;
import com.example.cafe.utils.CafaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> createCategory(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validate(requestMap, false)) {
                    categoryDao.save(getCategoryFromMap(requestMap, false));
                    return CafaUtils.getResponseEntity("Category create Succesfully", HttpStatus.OK);
                }
            } else {
                return CafaUtils.getResponse(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafaUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteCategory(Integer categoryId) {
        try {
            if (jwtFilter.isAdmin()) {
                Optional<Category> categoryOptional = categoryDao.findById(categoryId);

                if (categoryOptional.isPresent()) {
                    Category category = categoryOptional.get();
                    categoryDao.delete(category);
                    return CafaUtils.getResponseEntity("Category deleted successfully", HttpStatus.OK);
                } else {
                    return CafaUtils.getResponseEntity("Category not found", HttpStatus.NOT_FOUND);
                }
            } else {
                return CafaUtils.getResponse(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafaUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validate(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("name")) {
            if (requestMap.containsKey("id") && validateId) {
                return true;
            } else if (!validateId) {
                return true;
            }
        }
        return false;
    }

    private Category getCategoryFromMap(Map<String, String> requestMap, boolean isAdd) {
        Category category = new Category();
        if (isAdd) {
            category.setId(Integer.parseInt(requestMap.get("id")));
        }
        category.setName(requestMap.get("name"));
        return category;
    }

}
