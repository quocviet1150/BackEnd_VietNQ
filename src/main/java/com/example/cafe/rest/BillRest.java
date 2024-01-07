package com.example.cafe.rest;

import com.example.cafe.Entity.Bill;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "bill")
public interface BillRest {

    @PostMapping(path = "/generate_report")
    ResponseEntity<String> generateReport(@RequestBody Map<String, String> requestMap);

    @GetMapping(path = "/get_bills")
    ResponseEntity<List<Bill>> getBills();


}
