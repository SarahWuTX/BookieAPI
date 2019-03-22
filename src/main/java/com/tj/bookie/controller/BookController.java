package com.tj.bookie.controller;

import com.tj.bookie.DAO.HistoryRepository;
import com.tj.bookie.service.BookService;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Range;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;


@RestController
@RequestMapping("/book")
@Validated
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @ApiOperation(value="获取所有书(无序)", notes="可以正常使用，注意第一个分页是0，每页10条")
    @GetMapping(path="/getAll")
    public ResponseEntity<?> getAll(@RequestParam @Range(min = 0) Integer page) {
        return bookService.getAll(page);
    }


    @ApiOperation(value="价格由低到高排序", notes="注意第一个分页是0，每页10条")
    @GetMapping(path="/getByPrice")
    public ResponseEntity<?> getByPrice(@RequestParam @Range(min = 0) Integer page) {
        return bookService.getByPrice(page);
    }


    @ApiOperation(value="销量由高到低排序", notes="注意第一个分页是0，每页10条")
    @GetMapping(path="/getBySales")
    public ResponseEntity<?> getBySales(@RequestParam @Range(min = 0) Integer page) {
        return bookService.getBySales(page);
    }

    @ApiOperation(value="用户偏好推荐(乱序)", notes="注意第一个分页是0，每页10条")
    @GetMapping(path="/getByUser")
    public ResponseEntity<?> getByUser(@RequestParam @Range(min = 0) Integer page,
                                       @RequestParam @NotEmpty String wxId) {
        return bookService.getByUser(page, wxId);
    }


}
