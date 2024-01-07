package com.example.cafe.service;

import com.example.cafe.Entity.Bill;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface BillService {

    ResponseEntity<String> generateReport(Map<String, String> requestMap);

    ResponseEntity<List<Bill>> getBills();
}
