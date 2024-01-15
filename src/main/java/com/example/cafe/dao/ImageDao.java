package com.example.cafe.dao;

import com.example.cafe.Entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ImageDao extends JpaRepository<Image, Integer> {

    Image findByImagePath(Integer id);

    List<Image> findByStatusTrue();

    @Transactional
    @Modifying
    Integer updateStatus(@Param("status") String status, @Param("id") Integer id);

    Integer countByStatus(String status);

}
