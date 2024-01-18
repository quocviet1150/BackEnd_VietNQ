package com.example.cafe.Service.Impl;

import com.example.cafe.Entity.Category;
import com.example.cafe.Config.JwtFilter;
import com.example.cafe.Constants.CafeConstants;
import com.example.cafe.DAO.CategoryDAO;
import com.example.cafe.Entity.User;
import com.example.cafe.Service.CategoryService;
import com.example.cafe.Utils.ProjectUtils;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryDAO categoryDao;

    @Autowired
    JwtFilter jwtFilter;

    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Override
    public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
        try {
            if (!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")) {
                log.info("Inside if");
                return new ResponseEntity<List<Category>>(categoryDao.getAllCategory(), HttpStatus.OK);
            }
            return new ResponseEntity<>(categoryDao.findAll(), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<List<Category>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategoryStatus() {
        try {
            return new ResponseEntity<List<Category>>(categoryDao.getAllCategoryStatus(), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<List<Category>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> createCategory(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validate(requestMap, false)) {
                    categoryDao.save(getCategoryFromMap(requestMap, false));
                    return ProjectUtils.getResponseEntity("Category create Succesfully", HttpStatus.OK);
                }
            } else {
                return ProjectUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ProjectUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @Override
//    public ResponseEntity<String> deleteCategory(List<Integer> categoryIds) {
//        try {
//            if (jwtFilter.isAdmin()) {
//                for (Integer categoryId : categoryIds) {
//                    Optional<Category> categoryOptional = categoryDao.findById(categoryId);
//                    if (categoryOptional.isPresent()) {
//                        Category category = categoryOptional.get();
//
//                        categoryDao.delete(category);
//                    }
//                }
//                return CafaUtils.getResponseEntity("Categories deleted successfully", HttpStatus.OK);
//            } else {
//                return CafaUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return CafaUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validate(requestMap, true)) {
                    Optional optional = categoryDao.findById(Integer.parseInt(requestMap.get("id")));
                    if (!optional.isEmpty()) {
                        categoryDao.save(getCategoryFromMap(requestMap, true));
                        return ProjectUtils.getResponseEntity("Categories Updated successfully", HttpStatus.OK);
                    } else {
                        return ProjectUtils.getResponseEntity("Categories is does not exist", HttpStatus.OK);
                    }
                }
                return ProjectUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            } else {
                return ProjectUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ProjectUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
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
        category.setStatus("true");
        return category;
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                Optional<Category> optional = categoryDao.findById(Integer.parseInt(requestMap.get("id")));
                if (!optional.isEmpty()) {
                    categoryDao.updateStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
//                    sendMailToAllAdmin(requestMap.get("status"), optional.get().getEmail(), userDao.getAllAdmin());
                    return ProjectUtils.getResponseEntity("Cập nhật trạng thái người dùng thành công.", HttpStatus.OK);
                } else {
                    return ProjectUtils.getResponseEntity("Người dùng không tồn tại.", HttpStatus.BAD_REQUEST);
                }
            } else {
                return ProjectUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ProjectUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
