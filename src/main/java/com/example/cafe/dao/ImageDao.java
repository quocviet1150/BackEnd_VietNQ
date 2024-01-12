package com.example.cafe.dao;

import com.example.cafe.Entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ImageDao extends JpaRepository<Image, Long> {

    Image findByImagePath(Integer id);

    List<Image> findByStatusTrue();

}
