package com.tj.bookie.controller;

import com.tj.bookie.service.UserService;
import com.tj.bookie.utility.model.User;
import com.tj.bookie.utility.request.InputUser;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.util.*;


@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @ApiOperation(value="获取用户", notes = "如果该用户不存在，会返回null，但HttpCode还是200")
    @GetMapping(path="/get")
    public ResponseEntity<?> getUser(@RequestParam @NotEmpty String wxId) {
        return userService.getUser(wxId);
    }


    @ApiOperation(value="新建用户", notes = "生日按yyyy-MM-dd传，address是String[]，可依次填'省，市，区，详细地址'，下单时可以作为默认值")
    @PostMapping(path="/create")
    public ResponseEntity<?> createUser(@RequestBody @NotNull InputUser inputUser) throws ParseException {
        return userService.createUser(inputUser);
    }


    @ApiOperation(value="更改用户", notes = "生日按yyyy-MM-dd传，address是String[]，可依次填'省，市，区，详细地址'，下单时可以作为默认值")
    @PostMapping(path="/modify")
    public ResponseEntity<?> modifyUser(@RequestBody @NotNull InputUser inputUser) throws ParseException {
        return userService.modifyUser(inputUser);
    }



}