package com.example.cafe.Service.Impl;

import com.example.cafe.Component.LoginCounter;
import com.example.cafe.Config.JwtFilter;
import com.example.cafe.Constants.CafeConstants;
import com.example.cafe.DAO.*;
import com.example.cafe.Service.DashboardService;
import com.example.cafe.Utils.ProjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    CategoryDAO categoryDao;

    @Autowired
    ProductDAO productDao;

    @Autowired
    BillDAO billDao;

    @Autowired
    UserDAO userDao;

    @Autowired
    ImageDAO imageDao;

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    LoginCounter loginCounter;

    @Override
    public ResponseEntity<Map<String, Object>> getCount() {
        if (jwtFilter.isAdmin()) {
            Map<String, Object> map = new HashMap<>();
            map.put("category", categoryDao.count());
            map.put("product", productDao.count());
            map.put("bill", billDao.count());
            map.put("billSum", billDao.sumBill());
            map.put("getOne", billDao.getOne());
            map.put("getDateNowProduct", productDao.getDateNowProduct());
            map.put("user", userDao.countByRole("user"));
            map.put("image", imageDao.count());
            map.put("counts", loginCounter.getLoginCount());
            return new ResponseEntity<>(map, HttpStatus.OK);
        } else {
            return ProjectUtils.getResponseEntityMap(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> getCounts() {
        Map<String, Object> map = new HashMap<>();
        map.put("product", productDao.count());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}