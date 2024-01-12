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

@RestController
public class ImageRestImpl implements ImageRest {

    @Autowired
    ImageService imageService;

    @Override
    public ResponseEntity<String> uploadImage(String name, MultipartFile file) {
        try {
            return imageService.uploadImage(name, file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafaUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteImage(String fileName) {
        try {
            return imageService.deleteImage(fileName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafaUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
