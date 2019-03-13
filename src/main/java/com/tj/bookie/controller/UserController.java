package com.tj.bookie.controller;

import com.tj.bookie.DAO.UserRepository;
import com.tj.bookie.model.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @ApiOperation(value="获取用户列表", notes="notes here")
    @GetMapping(path="/getUser")
    public String getUser(@RequestParam String wxId) {
        return "Hello World";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }

}