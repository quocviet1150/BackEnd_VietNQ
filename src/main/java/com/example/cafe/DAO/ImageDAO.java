package com.example.cafe.DAO;

import com.example.cafe.Entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ImageDAO extends JpaRepository<Image, Integer> {

    Image findByImagePath(Integer id);

    List<Image> findByStatusTrue();

    @Transactional
    @Modifying
    Integer updateStatus(@Param("status") String status, @Param("id") Integer id);


    Optional<Image> findByFileName(String fileName);

}
