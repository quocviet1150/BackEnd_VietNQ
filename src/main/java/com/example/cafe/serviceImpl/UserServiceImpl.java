package com.example.cafe.serviceImpl;

import com.example.cafe.JWT.CustomerUserDetailsService;
import com.example.cafe.JWT.JwtFilter;
import com.example.cafe.JWT.JwtUtil;
import com.example.cafe.Entity.User;
import com.example.cafe.constents.CafeConstants;
import com.example.cafe.dao.UserDao;
import com.example.cafe.service.UserService;
import com.example.cafe.utils.CafaUtils;
import com.example.cafe.utils.EmailUtils;
import com.example.cafe.wrapper.UserWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    EmailUtils emailUtils;

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside signup {} ", requestMap);
        try {

            if (validateSignUp(requestMap)) {

                User user = userDao.findByUserNameId(requestMap.get("userName"));
                if (Objects.isNull(user)) {
                    userDao.save(getUserFromMap(requestMap));
                    return CafaUtils.getResponseEntity("save data.", HttpStatus.OK);
                } else {
                    return CafaUtils.getResponseEntity("UserName already exits.", HttpStatus.BAD_REQUEST);
                }

            } else {
                return CafaUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafaUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateSignUp(Map<String, String> requestMap) {
        if (requestMap.containsKey("name") && requestMap.containsKey("contactNumber") &&
                requestMap.containsKey("userName") && requestMap.containsKey("password")) {
            return true;
        }
        return false;
    }

    private User getUserFromMap(Map<String, String> requestMap) {
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setUserName(requestMap.get("userName"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("false");
        user.setRole("user");
        return user;
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("inside login");
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("userName"), requestMap.get("password"))
            );
            if (auth.isAuthenticated()) {
                if (customerUserDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")) {
                    return new ResponseEntity<String>("{\"token\":\"" +
                            jwtUtil.generateToken(customerUserDetailsService.getUserDetail().getUserName(),
                                    customerUserDetailsService.getUserDetail().getRole()) + "\"}",
                            HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<String>("{\"message\":\"" + "Wait for admin approval." + "\"}",
                        HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<String>("{\"message\":\"" + "Bad credentials." + "\"}",
                HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        try {
            if (jwtFilter.isAdmin()) {
                return new ResponseEntity<>(userDao.getAllUser(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                Optional<User> optional = userDao.findById(Integer.parseInt(requestMap.get("id")));
                if (!optional.isEmpty()) {
                    userDao.updateStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
//                    sendMailToAllAdmin(requestMap.get("status"), optional.get().getEmail(), userDao.getAllAdmin());
                    return CafaUtils.getResponseEntity("User status update successfully", HttpStatus.OK);
                } else {
                    return CafaUtils.getResponseEntity("User is doesn't not exist", HttpStatus.BAD_REQUEST);
                }
            } else {
                return CafaUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafaUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    private void sendMailToAllAdmin(String status, String user, List<String> allAdmin) {
//        allAdmin.remove(jwtFilter.getCurrentUser());
//        if (status != null && status.equalsIgnoreCase("true")) {
//            emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(), "Account Approved", "USER: - " + user
//                    + " \n is approved by \nADMIN:- " + jwtFilter.getCurrentUser(), allAdmin);
//        } else {
//            emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(), "Account Disable", "USER: - " + user
//                    + " \n is disable by \nADMIN:- " + jwtFilter.getCurrentUser(), allAdmin);
//        }
//    }

    @Override
    public ResponseEntity<String> checkToken() {
        return CafaUtils.getResponseEntity("true", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try {
            User userObj = userDao.findByUserName(jwtFilter.getCurrentUser());
            if (!userObj.equals(null)) {
                if (userObj.getPassword().equals(requestMap.get("oldPassword"))) {
                    userObj.setPassword(requestMap.get("newPassword"));
                    userDao.save(userObj);
                    return CafaUtils.getResponseEntity("Password Updated SuccessFully", HttpStatus.OK);
                }
                return CafaUtils.getResponseEntity("Incorrect Old password", HttpStatus.BAD_REQUEST);
            }
            return CafaUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafaUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @Override
//    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
//        try {
//            User user =userDao.findByUserName(requestMap.get("userName"));
//            if(!Objects.isNull(user) && !Strings.isNullOrEmpty(user.getUserName())){
//
//                return CafaUtils.getResponseEntity("check user for credentials", HttpStatus.OK);
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return CafaUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
