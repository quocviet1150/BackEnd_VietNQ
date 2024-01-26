package com.example.cafe.Rest;

import com.example.cafe.DTO.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/user")
public interface UserRest {

    @PostMapping(path = "/signup")
    ResponseEntity<String> signUp(@RequestBody(required = true) Map<String, String> requestMap);

    @PostMapping(path = "/login")
    ResponseEntity<String> login(@RequestBody(required = true) Map<String, String> requestMap);

    @GetMapping(path = "/get")
    ResponseEntity<List<UserDTO>> getAllUser();

    @PostMapping(path = "/update")
    ResponseEntity<String> update(@RequestBody(required = true) Map<String, String> requestMap);

    @GetMapping(path = "/checkToken")
    ResponseEntity<String> checkToken();

    @PostMapping(path = "/change_password")
    ResponseEntity<String> changePassword(@RequestBody(required = true) Map<String, String> requestMap);

//    @PostMapping(path = "/forgotPassword")
//    ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> requestMap);

    @PostMapping(path = "/delete_user/{userId}")
    ResponseEntity<String> deleteUser(@PathVariable Integer userId);

    @PostMapping(path = "/update_decentralization")
    ResponseEntity<String> updateDecentralization(@RequestBody(required = true) Map<String, String> requestMap);

    @GetMapping(path = "/get_detail_login")
    ResponseEntity<com.example.cafe.Entity.User> getDetailLogin();

    @PostMapping("/update_information/{id}")
    ResponseEntity<String> updateUserDetails(@PathVariable("id") Integer id, @RequestBody(required = true) Map<String, String> requestMap);

    @PostMapping("/update_image/{id}")
    ResponseEntity<String> updateUser(@PathVariable("id") Integer id,  @RequestParam MultipartFile file);

}
