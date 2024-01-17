package com.example.cafe.Config;

import com.example.cafe.DAO.UserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    UserDAO userDao;

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

    public com.example.cafe.Entity.User getUserDetail() {
        return userDetail;
    }
}
