package com.example.cafe.Utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CafaUtils {

    private CafaUtils() {

    }

    public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus) {
        return new ResponseEntity<String>("{\"message\":\"" + responseMessage + "\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
