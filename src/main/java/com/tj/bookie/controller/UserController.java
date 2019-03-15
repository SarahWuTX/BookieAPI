package com.tj.bookie.controller;

import com.tj.bookie.DAO.UserRepository;
import com.tj.bookie.model.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @ApiOperation(value="获取用户", notes="notes here")
    @GetMapping(path="/get")
    public ResponseEntity<?> getUser(@RequestParam @NotEmpty String wxId) {
        User user = userRepository.findByWxId(wxId);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @ApiOperation(value="新建用户", notes="notes here")
    @PostMapping(path="/create")
    public ResponseEntity<?> createUser(@RequestParam @NotEmpty String wxId,
                                        @RequestParam @NotEmpty String name,
                                        @RequestParam @NotEmpty String phone,
                                        @RequestParam(required = false) @NotNull Date birthday,
                                        @RequestParam(required = false) @NotEmpty String province) {
        if (userRepository.findByWxId(wxId) != null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        userRepository.save(new User(wxId, name, phone, birthday, province));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value="更改用户", notes="notes here")
    @PostMapping(path="/modify")
    public ResponseEntity<?> modifyUser(User user) {
        Optional<User> user1 = userRepository.findById(user.getId());
        if (!user1.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}