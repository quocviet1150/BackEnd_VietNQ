package com.example.cafe.restImpl;

import com.example.cafe.constents.CafeConstants;
import com.example.cafe.rest.ImageRest;
import com.example.cafe.service.ImageService;
import com.example.cafe.utils.CafaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
public class ImageRestImpl implements ImageRest {

    @Autowired
    ImageService imageService;

    @Override
    public ResponseEntity<String> uploadImage(String name, String description,MultipartFile file) {
        try {
            return imageService.uploadImage(name, description,file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafaUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteImage(Integer id) {
        try {
            return imageService.deleteImage(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafaUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Object> getImage() {
        try {
            return imageService.getImage();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafaUtils.getResponseEntityVer2(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Object> getImageAll() {
        try {
            return imageService.getImageAll();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafaUtils.getResponseEntityVer2(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            return imageService.update(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafaUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
