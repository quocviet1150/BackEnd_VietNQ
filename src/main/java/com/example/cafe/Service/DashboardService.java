package com.example.cafe.Service;


import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface DashboardService {

    ResponseEntity<Map<String, Object>> getCount();
    ResponseEntity<Map<String, Object>> getCounts();

}
