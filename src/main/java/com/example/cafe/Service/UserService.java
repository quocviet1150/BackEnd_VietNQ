package com.example.cafe.Service;

import com.example.cafe.DTO.UserDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {

    ResponseEntity<String> signUp(Map<String, String> requestMap);

    ResponseEntity<String> login(Map<String, String> requestMap);

    ResponseEntity<List<UserDTO>> getAllUser();

    ResponseEntity<String> update(Map<String, String> requestMap);

    ResponseEntity<String> checkToken();

    ResponseEntity<String> changePassword(Map<String, String> requestMap);

//    ResponseEntity<String> forgotPassword(Map<String, String> requestMap);

    ResponseEntity<String> deleteUser(Integer userId);

    ResponseEntity<String> updateDecentralization(Map<String, String> requestMap);

}
