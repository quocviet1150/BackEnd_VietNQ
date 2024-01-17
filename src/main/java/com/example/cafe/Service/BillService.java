package com.example.cafe.Service;

import com.example.cafe.Entity.Bill;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface BillService {

    ResponseEntity<String> generateReport(Map<String, Object> requestMap);

    ResponseEntity<List<Bill>> getBills();

    ResponseEntity<byte[]> getPDF(Map<String, Object> requestMap);

    ResponseEntity<String> deleteBill(Integer id);
}
