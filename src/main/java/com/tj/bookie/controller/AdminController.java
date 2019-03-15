package com.tj.bookie.controller;

import com.tj.bookie.DAO.AdminRepository;
import com.tj.bookie.DAO.BookRepository;
import com.tj.bookie.model.Admin;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/admin")
@Validated
public class AdminController {
    private final AdminRepository adminRepository;

    @Autowired
    public AdminController(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @ApiOperation(value="管理员登录", notes="notes here")
    @PostMapping(path="/login")
    public ResponseEntity<?> login(@RequestParam @NotEmpty String name,
                                   @RequestParam @NotEmpty String password) {
        Admin admin = adminRepository.findByName(name);
        if (admin == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (password.equals(admin.getPassword())) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
