package com.tj.bookie.controller;

import com.tj.bookie.DAO.AdminRepository;
import com.tj.bookie.service.AdminService;
import com.tj.bookie.utility.model.Admin;
import com.tj.bookie.utility.request.AdminLogin;
import io.swagger.annotations.ApiOperation;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.validation.constraints.NotEmpty;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@Validated
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @ApiOperation(value="管理员登录", notes="目前只有一个管理员，用户名和密码都是admin")
    @PostMapping(path="/login")
    public ResponseEntity<?> login(@RequestBody AdminLogin adminLogin) throws JSONException {
        return adminService.login(adminLogin);
    }


}
