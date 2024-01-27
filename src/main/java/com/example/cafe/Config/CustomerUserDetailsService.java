package com.example.cafe.Config;

import com.example.cafe.Constants.CafeConstants;
import com.example.cafe.DAO.UserDAO;
import com.example.cafe.Utils.ProjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    UserDAO userDao;

    @Value("${upload.path}")
    private String uploadPath;

    private com.example.cafe.Entity.User userDetail;

    private static final Logger log = LoggerFactory.getLogger(CustomerUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Inside loadUserByUserName {}", username);
        userDetail = userDao.findByUserNameId(username);
        if (!Objects.isNull(userDetail)) {
            return new User(userDetail.getUserName(), userDetail.getPassword(), new ArrayList<>());
        } else
            throw new UsernameNotFoundException(("User not found."));
    }

    public com.example.cafe.Entity.User getUserDetailVr1() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof com.example.cafe.Entity.User) {
                return (com.example.cafe.Entity.User) principal;
            }
        }
        return userDetail;
    }

    public UserDetailResponse getUserDetail() throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof User) {
                User userDetail = (User) principal;
                String username = userDetail.getUsername();

                com.example.cafe.Entity.User userFromDB = userDao.findByUserNameId(username);
                if (userFromDB != null) {
                    String fileName = userFromDB.getFileName();
                    String filePath = uploadPath + File.separator + userFromDB.getId() + File.separator + fileName;

                    // Kiểm tra đường dẫn trước khi đọc file
                    File file = new File(filePath);
                    if (file.exists()) {
                        byte[] imageBytes = Files.readAllBytes(file.toPath());
                        String imageBase64 = Base64.getEncoder().encodeToString(imageBytes);

                        UserDetailResponse response = new UserDetailResponse();
                        response.setUserDetail(userFromDB);
                        response.setImageBase64(imageBase64);

                        return response;
                    } else {
                        UserDetailResponse response = new UserDetailResponse();
                        response.setUserDetail(userFromDB);
                        return response;
                    }
                }
            }
        }
        return null;
    }


    public class UserDetailResponse {
        private com.example.cafe.Entity.User userDetail;
        private String imageBase64;

        public com.example.cafe.Entity.User getUserDetail() {
            return userDetail;
        }

        public void setUserDetail(com.example.cafe.Entity.User userDetail) {
            this.userDetail = userDetail;
        }

        public String getImageBase64() {
            return imageBase64;
        }

        public void setImageBase64(String imageBase64) {
            this.imageBase64 = imageBase64;
        }

        // Phương thức để lấy cả userDetail và imageBase64
        public Map<String, Object> getCombinedInfo() {
            Map<String, Object> combinedInfo = new HashMap<>();
            combinedInfo.put("userDetail", userDetail);
            combinedInfo.put("imageBase64", imageBase64);
            return combinedInfo;
        }
    }



}
