package com.example.cafe.Service.Impl;

import com.example.cafe.DAO.*;
import com.example.cafe.Service.DashboardService;
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

    @Override
    public ResponseEntity<Map<String, Object>> getCount() {
        Map<String, Object> map = new HashMap<>();
        map.put("category", categoryDao.count());
        map.put("product", productDao.count());
        map.put("bill", billDao.count());
        map.put("bill", billDao.sumBill());
        map.put("user", userDao.countByRole("user"));
        map.put("image", imageDao.count());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
