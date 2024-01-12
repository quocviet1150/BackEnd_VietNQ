package com.example.cafe.serviceImpl;

import com.example.cafe.Entity.Image;
import com.example.cafe.JWT.JwtFilter;
import com.example.cafe.constents.CafeConstants;
import com.example.cafe.dao.ImageDao;
import com.example.cafe.service.ImageService;
import com.example.cafe.utils.CafaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class ImageServiceImpl implements ImageService {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    ImageDao imageDao;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> uploadImage(String name,String description, MultipartFile file) {
        try {
            if (jwtFilter.isAdmin()) {
                String originalFilename = Objects.requireNonNull(file.getOriginalFilename());
                Path imagePath = Path.of(uploadPath, originalFilename);

                if (Files.exists(imagePath)) {
                    return CafaUtils.getResponseEntity("File đã tồn tại. Không thể upload trùng lặp.", HttpStatus.BAD_REQUEST);
                }

                Files.copy(file.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

                Image imageEntity = new Image();
                imageEntity.setName(name);
                imageEntity.setDescription(description);
                imageEntity.setImagePath(imagePath.toString());
                imageEntity.setFileName(originalFilename);
                imageDao.save(imageEntity);

                return CafaUtils.getResponseEntity("Upload ảnh thành công.", HttpStatus.OK);
            } else {
                return CafaUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return CafaUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteImage(Integer id) {
        try {
            if (jwtFilter.isAdmin()) {
                Image imageEntity = imageDao.findByImagePath(id);
                Path imagePath = Path.of(uploadPath,imageEntity.getFileName());
                if (imageEntity != null) {
                    imageDao.delete(imageEntity);
                    if (Files.exists(imagePath)) {
                        Files.delete(imagePath);
                        return CafaUtils.getResponseEntity("Xóa ảnh và dữ liệu thành công.", HttpStatus.OK);
                    } else {
                        return CafaUtils.getResponseEntity("Không tìm thấy dữ liệu trong cơ sở dữ liệu.", HttpStatus.NOT_FOUND);
                    }
                } else {
                    return CafaUtils.getResponseEntity("File không tồn tại.", HttpStatus.BAD_REQUEST);
                }
            } else {
                return CafaUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafaUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Object> getImage(Integer id) {
        try {
            Optional<Image> optionalImage = Optional.ofNullable(imageDao.findByImagePath(id));
            if (optionalImage.isPresent()) {
                Image image = optionalImage.get();
                Image imageEntity = imageDao.findByImagePath(id);
                byte[] imageBytes = Files.readAllBytes(Path.of(uploadPath, imageEntity.getFileName()));
                Map<String, Object> response = new HashMap<>();
                response.put("name", image.getName());
                response.put("description", image.getDescription());
                response.put("fileName", image.getFileName());
                response.put("file", Base64.getEncoder().encodeToString(imageBytes));
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return ResponseEntity.ok()
                        .headers(headers)
                        .body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File không tồn tại.");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return CafaUtils.getResponseEntityVer2(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
