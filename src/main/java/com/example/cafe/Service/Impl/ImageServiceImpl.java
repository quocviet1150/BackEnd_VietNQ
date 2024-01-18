package com.example.cafe.Service.Impl;

import com.example.cafe.Entity.Image;
import com.example.cafe.Config.JwtFilter;
import com.example.cafe.Constants.CafeConstants;
import com.example.cafe.DAO.ImageDAO;
import com.example.cafe.Service.ImageService;
import com.example.cafe.Utils.ProjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    ImageDAO imageDao;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> uploadImage(String name, String description, MultipartFile file) {
        try {
            if (jwtFilter.isAdmin()) {
                String originalFilename = Objects.requireNonNull(file.getOriginalFilename());
                Path imagePath = Path.of(uploadPath, originalFilename);

                if (Files.exists(imagePath)) {
                    return ProjectUtils.getResponseEntity("File đã tồn tại. Không thể upload trùng lặp.", HttpStatus.BAD_REQUEST);
                }

                Files.copy(file.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

                Image imageEntity = new Image();
                imageEntity.setName(name);
                imageEntity.setDescription(description);
                imageEntity.setImagePath(imagePath.toString());
                imageEntity.setFileName(originalFilename);
                imageEntity.setStatus("true");
                imageDao.save(imageEntity);

                return ProjectUtils.getResponseEntity("Upload ảnh thành công.", HttpStatus.OK);
            } else {
                return ProjectUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return ProjectUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(String name, String description, MultipartFile file) {
        try {
            if (jwtFilter.isAdmin()) {
                String originalFilename = Objects.requireNonNull(file.getOriginalFilename());
                Path imagePath = Path.of(uploadPath, originalFilename);
                Optional<Image> existingImage = imageDao.findByFileName(originalFilename);
                if (existingImage.isPresent()) {
                    Image imageEntity = existingImage.get();
                    imageEntity.setName(name);
                    imageEntity.setDescription(description);
                    imageEntity.setImagePath(imagePath.toString());
                    imageEntity.setStatus("true");
                    imageDao.save(imageEntity);
                    Files.copy(file.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
                    return ProjectUtils.getResponseEntity("Cập nhật ảnh thành công.", HttpStatus.OK);
                } else {
                    return ProjectUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return ProjectUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteImage(Integer id) {
        try {
            if (jwtFilter.isAdmin()) {
                Image imageEntity = imageDao.findByImagePath(id);
                Path imagePath = Path.of(uploadPath, imageEntity.getFileName());
                if (imageEntity != null) {
                    imageDao.delete(imageEntity);
                    if (Files.exists(imagePath)) {
                        Files.delete(imagePath);
                        return ProjectUtils.getResponseEntity("Xóa ảnh và dữ liệu thành công.", HttpStatus.OK);
                    } else {
                        return ProjectUtils.getResponseEntity("Không tìm thấy dữ liệu trong cơ sở dữ liệu.", HttpStatus.NOT_FOUND);
                    }
                } else {
                    return ProjectUtils.getResponseEntity("File không tồn tại.", HttpStatus.BAD_REQUEST);
                }
            } else {
                return ProjectUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ProjectUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Object> getImage() {
        try {
            List<Image> images = imageDao.findByStatusTrue();
            List<Map<String, Object>> responseList = new ArrayList<>();

            for (Image image : images) {
                byte[] imageBytes = Files.readAllBytes(Path.of(uploadPath, image.getFileName()));

                Map<String, Object> response = new HashMap<>();
                response.put("name", image.getName());
                response.put("description", image.getDescription());
                response.put("fileName", image.getFileName());
                response.put("imagePath", image.getImagePath());
                response.put("file", Base64.getEncoder().encodeToString(imageBytes));
                responseList.add(response);
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(responseList);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return ProjectUtils.getResponseEntityObject(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Object> getImageAll() {
        try {
            if (jwtFilter.isAdmin()) {
                List<Image> images = imageDao.findAll();
                List<Map<String, Object>> responseList = new ArrayList<>();

                for (Image image : images) {
                    byte[] imageBytes = Files.readAllBytes(Path.of(uploadPath, image.getFileName()));

                    Map<String, Object> response = new HashMap<>();
                    response.put("id", image.getId());
                    response.put("name", image.getName());
                    response.put("description", image.getDescription());
                    response.put("fileName", image.getFileName());
                    response.put("status", image.getStatus());
                    response.put("file", Base64.getEncoder().encodeToString(imageBytes));
                    responseList.add(response);
                }

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                return ResponseEntity.ok()
                        .headers(headers)
                        .body(responseList);
            } else {
                return ProjectUtils.getResponseEntityObject(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return ProjectUtils.getResponseEntityObject(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                Optional<Image> optional = imageDao.findById(Integer.parseInt(requestMap.get("id")));
                if (!optional.isEmpty()) {
                    imageDao.updateStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
//                    sendMailToAllAdmin(requestMap.get("status"), optional.get().getEmail(), userDao.getAllAdmin());
                    return ProjectUtils.getResponseEntity("Cập nhật trạng thái ảnh thành công.", HttpStatus.OK);
                } else {
                    return ProjectUtils.getResponseEntity("Ảnh không tồn tại.", HttpStatus.BAD_REQUEST);
                }
            } else {
                return ProjectUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ProjectUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
