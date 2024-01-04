package com.aracan.clothes.serviceImpl;

import com.aracan.clothes.POJO.User;
import com.aracan.clothes.constants.AracanConstants;
import com.aracan.clothes.dao.UserDao;
import com.aracan.clothes.service.UserService;
import com.aracan.clothes.utils.AracanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside signup {}", requestMap);
        try {
            if (validateSignUpMap(requestMap)) { // Checks if the sign-up has been completed properly
                User user = userDao.findByEmailId(requestMap.get("email"));
                if (Objects.isNull(user)) {
                    userDao.save(getUserFromMap(requestMap));
                    return AracanUtils.getResponseEntity("Successfully Registered!", HttpStatus.OK);
                } else {
                    return AracanUtils.getResponseEntity("Email already exists.", HttpStatus.BAD_REQUEST);
                }
            }
            return AracanUtils.getResponseEntity(AracanConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AracanUtils.getResponseEntity(AracanConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Checks if the user properly signs up. User must include their name, contactNumber, email, and password when signing up
     *
     * @param requestMap Is the "request" the user makes. In other words, the collection of items the user used to sign up with
     * @return boolean
     */
    private boolean validateSignUpMap(Map<String, String> requestMap) {
        return requestMap.containsKey("name") && requestMap.containsKey("contactNumber")
                && requestMap.containsKey("email") && requestMap.containsKey("password");
    }

    private User getUserFromMap(Map<String, String> requestMap) {
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("false");
        user.setRole("user");
        return user;
    }
}
