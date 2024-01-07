package com.example.cafe.rest;

import com.example.cafe.Entity.Bill;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "bill")
public interface BillRest {

    @PostMapping(path = "/generate_report")
    ResponseEntity<String> generateReport(@RequestBody Map<String, Object> requestMap);

    @GetMapping(path = "/get_bills")
    ResponseEntity<List<Bill>> getBills();

    @PostMapping(path = "/get_pdf")
    ResponseEntity<byte[]> getPDF(@RequestBody Map<String, Object> requestMap);

    @PostMapping(path = "/delete/{id}")
    ResponseEntity<String> deleteBill(@PathVariable Integer id);
}
