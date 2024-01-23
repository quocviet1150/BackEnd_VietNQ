package com.example.cafe.DAO;

import com.example.cafe.Entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BillDAO extends JpaRepository<Bill, Integer> {

    List<Bill> getAllBills();

    List<Bill> getBillByUserName(@Param("username") String username);

    Integer sumBill();

    Integer getOne();
}
