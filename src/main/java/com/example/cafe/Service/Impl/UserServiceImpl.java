package com.example.cafe.Service.Impl;

import com.example.cafe.Component.LoginCounter;
import com.example.cafe.Config.CustomerUserDetailsService;
import com.example.cafe.Config.JwtFilter;
import com.example.cafe.Config.JwtUtil;
import com.example.cafe.Entity.User;
import com.example.cafe.Constants.CafeConstants;
import com.example.cafe.DAO.UserDAO;
import com.example.cafe.Service.UserService;
import com.example.cafe.Utils.ProjectUtils;
import com.example.cafe.Utils.EmailUtils;
import com.example.cafe.DTO.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDAO userDao;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    EmailUtils emailUtils;

    @Autowired
    LoginCounter loginCounter;

    @Value("${upload.path}")
    private String uploadPath;
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside signup {} ", requestMap);
        try {

            if (validateSignUp(requestMap)) {

                User user = userDao.findByUserNameId(requestMap.get("userName"));
                if (Objects.isNull(user)) {
                    userDao.save(getUserFromMap(requestMap));
                    return ProjectUtils.getResponseEntity("Đăng ký tài khoản thành công.", HttpStatus.OK);
                } else {
                    return ProjectUtils.getResponseEntity("Đăng ký tài khoản không thành công, Tên đăng nhập đã được sử dụng.", HttpStatus.BAD_REQUEST);
                }

            } else {
                return ProjectUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ProjectUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateSignUp(Map<String, String> requestMap) {
        if (requestMap.containsKey("name") && requestMap.containsKey("contactNumber") &&
                requestMap.containsKey("userName") && requestMap.containsKey("password")) {
            return true;
        }
        return false;
    }

    private User getUserFromMap(Map<String, String> requestMap) {
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setCreatedDate(new Date());
        user.setUserName(requestMap.get("userName"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("true");
        user.setRole("user");
        return user;
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("inside login");
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("userName"), requestMap.get("password"))
            );
            if (auth.isAuthenticated()) {
                loginCounter.incrementLoginCount();
                if (customerUserDetailsService.getUserDetailVr1().getStatus().equalsIgnoreCase("true")) {
                    return new ResponseEntity<>("{\"token\":\"" +
                            jwtUtil.generateToken(customerUserDetailsService.getUserDetailVr1().getUserName(),
                                    customerUserDetailsService.getUserDetailVr1().getRole()) + "\"}",
                            HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("{\"message\":\"" + "Tài khoản quý khách đã ngưng hoạt động" + "\"}", HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>("{\"message\":\"" + "Chờ sự phê duyệt của quản trị viên." + "\"}",
                        HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>("{\"message\":\"" + "Sai mật khẩu hoặc tài khoản." + "\"}",
                HttpStatus.BAD_REQUEST);
    }


    @Override
    public ResponseEntity<List<UserDTO>> getAllUser() {
        try {
            if (jwtFilter.isAdmin()) {
                return new ResponseEntity<>(userDao.getAllUser(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                Optional<User> optional = userDao.findById(Integer.parseInt(requestMap.get("id")));
                if (!optional.isEmpty()) {
                    userDao.updateStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
//                    sendMailToAllAdmin(requestMap.get("status"), optional.get().getEmail(), userDao.getAllAdmin());
                    return ProjectUtils.getResponseEntity("Cập nhật trạng thái người dùng thành công.", HttpStatus.OK);
                } else {
                    return ProjectUtils.getResponseEntity("Người dùng không tồn tại.", HttpStatus.BAD_REQUEST);
                }
            } else {
                return ProjectUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ProjectUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    private void sendMailToAllAdmin(String status, String user, List<String> allAdmin) {
//        allAdmin.remove(jwtFilter.getCurrentUser());
//        if (status != null && status.equalsIgnoreCase("true")) {
//            emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(), "Account Approved", "USER: - " + user
//                    + " \n is approved by \nADMIN:- " + jwtFilter.getCurrentUser(), allAdmin);
//        } else {
//            emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(), "Account Disable", "USER: - " + user
//                    + " \n is disable by \nADMIN:- " + jwtFilter.getCurrentUser(), allAdmin);
//        }
//    }

    @Override
    public ResponseEntity<String> checkToken() {
        return ProjectUtils.getResponseEntity("true", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try {
            User userObj = userDao.findByUserName(jwtFilter.getCurrentUser());
            if (!userObj.equals(null)) {
                if (userObj.getPassword().equals(requestMap.get("oldPassword"))) {
                    userObj.setPassword(requestMap.get("newPassword"));
                    userDao.save(userObj);
                    return ProjectUtils.getResponseEntity("Cập nhật mật khẩu thành công.", HttpStatus.OK);
                }
                return ProjectUtils.getResponseEntity("Mật mã cũ không chính xác.", HttpStatus.BAD_REQUEST);
            }
            return ProjectUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ProjectUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


//    @Override
//    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
//        try {
//            User user =userDao.findByUserName(requestMap.get("userName"));
//            if(!Objects.isNull(user) && !Strings.isNullOrEmpty(user.getUserName())){
//
//                return CafaUtils.getResponseEntity("check user for credentials", HttpStatus.OK);
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return CafaUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @Override
    public ResponseEntity<String> deleteUser(Integer userId) {
        try {
            if (jwtFilter.isAdmin()) {
                Optional<User> userOptional = userDao.findById(userId);

                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    userDao.delete(user);
                    return ProjectUtils.getResponseEntity("Đã xóa thành công người dùng.", HttpStatus.OK);
                } else {
                    return ProjectUtils.getResponseEntity("Không tìm thấy người dùng.", HttpStatus.NOT_FOUND);
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
    public ResponseEntity<String> updateDecentralization(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                Optional<User> optional = userDao.findById(Integer.parseInt(requestMap.get("id")));
                if (!optional.isEmpty()) {
                    userDao.updateDecentralization(requestMap.get("role"), Integer.parseInt(requestMap.get("id")));
//                    sendMailToAllAdmin(requestMap.get("status"), optional.get().getEmail(), userDao.getAllAdmin());
                    return ProjectUtils.getResponseEntity("Cập nhật quyền người dùng thành công.", HttpStatus.OK);
                } else {
                    return ProjectUtils.getResponseEntity("Người dùng không tồn tại.", HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<String> updateUserDetails(Integer id, Map<String, String> requestMap) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                Object principal = authentication.getPrincipal();
                if (principal instanceof UserDetails) {
                    UserDetails userDetails = (UserDetails) principal;
                    String username = userDetails.getUsername();

                    User currentUser = userDao.findByUserName(username);
                    if (currentUser != null) {
                        String name = requestMap.get("name");
                        String contactNumber = requestMap.get("contactNumber");
                        updateUserDetails(currentUser, name, contactNumber);
                        return ResponseEntity.ok("Chi tiết người dùng được cập nhật thành công.");
                    } else {
                        return ProjectUtils.getResponseEntity("Không tìm thấy dữ liệu trong cơ sở dữ liệu.", HttpStatus.NOT_FOUND);
                    }
                }
            }
            return ProjectUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ProjectUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> updateUser(Integer id, MultipartFile file) {
        try {
            String originalFilename = Objects.requireNonNull(file.getOriginalFilename());
            Path userFolderPath = Paths.get(uploadPath, String.valueOf(id));
            if (!Files.exists(userFolderPath)) {
                Files.createDirectories(userFolderPath);
            }

            Path imagePath = userFolderPath.resolve(originalFilename);

            if (Files.exists(imagePath)) {
                return ProjectUtils.getResponseEntity("File đã tồn tại. Không thể upload trùng lặp.", HttpStatus.BAD_REQUEST);
            }

            Files.copy(file.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                Object principal = authentication.getPrincipal();
                if (principal instanceof UserDetails) {
                    UserDetails userDetails = (UserDetails) principal;
                    String username = userDetails.getUsername();

                    User currentUser = userDao.findByUserName(username);
                    if (currentUser != null) {
                        currentUser.setImagePath(imagePath.toString());
                        currentUser.setFileName(originalFilename);
                        userDao.save(currentUser);
                        return ProjectUtils.getResponseEntity("Đã cập nhật thành công người dùng.", HttpStatus.OK);
                    } else {
                        return ProjectUtils.getResponseEntity("Không tìm thấy dữ liệu trong cơ sở dữ liệu.", HttpStatus.NOT_FOUND);
                    }
                }
            }
            return ProjectUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ProjectUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    private void updateUserDetails(User user, String name, String contactNumber) {
        if (name != null && !name.isEmpty()) {
            user.setName(name);
        }
        if (contactNumber != null && !contactNumber.isEmpty()) {
            user.setContactNumber(contactNumber);
        }

        userDao.save(user);
    }


}
