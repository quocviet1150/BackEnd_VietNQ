package com.example.cafe.dao;

import com.example.cafe.Entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillDao extends JpaRepository<Bill, Integer> {
}
