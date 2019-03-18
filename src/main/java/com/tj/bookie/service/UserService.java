package com.tj.bookie.service;

import com.tj.bookie.DAO.UserRepository;
import com.tj.bookie.utility.model.User;
import com.tj.bookie.utility.request.InputUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Transactional
@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public ResponseEntity<?> getUser(String wx_id) {
        return new ResponseEntity<>(userRepository.findByWxId(wx_id).get(), HttpStatus.OK);
    }


    public ResponseEntity<?> createUser(InputUser inputUser) throws ParseException {
        User user = this.parseInputToUser(inputUser);
        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    public ResponseEntity<?> modifyUser(InputUser inputUser) throws ParseException {
        Optional<User> oldUser = userRepository.findByWxId(inputUser.getWx_id());
        if (!oldUser.isPresent()) {
            return new ResponseEntity<>("对象不存在", HttpStatus.NOT_FOUND);
        }
        User user = this.parseInputToUser(inputUser);
        user.setId(oldUser.get().getId());
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * parse the inputUser to user
     * @param inputUser input
     * @return user
     * @throws ParseException
     */
    public User parseInputToUser(InputUser inputUser) throws ParseException{
        User user = new User();
        user.setWxId(inputUser.getWx_id());
        user.setName(inputUser.getName());
        user.setPhone(inputUser.getPhone());
        Date birthday;
        birthday = new SimpleDateFormat("yyyy-MM-dd").parse(inputUser.getBirthday());
        user.setBirthday(birthday);
        if (inputUser.getAddress().size() > 0) {
            user.setProvince(inputUser.getAddress().get(0));
        }
        if (inputUser.getAddress().size() > 1) {
            user.setCity(inputUser.getAddress().get(1));
        }
        if (inputUser.getAddress().size() > 2) {
            user.setDistrict(inputUser.getAddress().get(2));
        }
        if (inputUser.getAddress().size() > 3) {
            user.setAddress(inputUser.getAddress().get(3));
        }
        return user;
    }
}
