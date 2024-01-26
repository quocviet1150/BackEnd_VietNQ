package com.example.cafe.Rest.Impl;

import com.example.cafe.Config.CustomerUserDetailsService;
import com.example.cafe.Constants.CafeConstants;
import com.example.cafe.Entity.User;
import com.example.cafe.Rest.UserRest;
import com.example.cafe.Service.UserService;
import com.example.cafe.Utils.ProjectUtils;
import com.example.cafe.DTO.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class UserRestImpl implements UserRest {

    @Autowired
    UserService userService;

    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;


    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        try {
            return userService.signUp(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ProjectUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try {
            return userService.login(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ProjectUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<UserDTO>> getAllUser() {
        try {
            return userService.getAllUser();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<List<UserDTO>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            return userService.update(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ProjectUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> checkToken() {
        try {
            return userService.checkToken();
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return ProjectUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try {
            return userService.changePassword(requestMap);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return ProjectUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteUser(Integer userId) {
        try {
            return userService.deleteUser(userId);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return ProjectUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateDecentralization(Map<String, String> requestMap) {
        try {
            return userService.updateDecentralization(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ProjectUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getDetailLogin() throws IOException {
        CustomerUserDetailsService.UserDetailResponse currentUserDetail = customerUserDetailsService.getUserDetail();
        if (currentUserDetail != null) {
            return new ResponseEntity<>(currentUserDetail.getCombinedInfo(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }


    @Override
    public ResponseEntity<String> updateUserDetails(Integer id, Map<String, String> requestMap) {
        try {
            return userService.updateUserDetails(id, requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ProjectUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateUser(Integer id, MultipartFile file) {
        try {
            return userService.updateUser(id, file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ProjectUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @Override
//    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
//        try {
//            return userService.forgotPassword(requestMap);
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//        return CafaUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
//    }


}
