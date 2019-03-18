package com.tj.bookie.controller;


import com.tj.bookie.service.StockService;
import com.tj.bookie.utility.model.Book;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Range;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@RequestMapping("/stock")
@Validated
public class StockController {
    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }


    @ApiOperation(value="查看所有书的库存", notes = "注意第一个分页是0")
    @GetMapping(path="/getAll")
    public ResponseEntity<?> getAll(@RequestParam @Range(min = 0) Integer page,
                                    @RequestParam @Range(min = 1) Integer limit) throws JSONException {
        return stockService.getAll(page, limit);
    }


    @ApiOperation(value="根据isbn查库存", notes = "")
    @GetMapping(path="/getByIsbn")
    public ResponseEntity<?> getByIsbn(@RequestParam @NotEmpty String isbn) throws JSONException {
        return stockService.getByIsbn(isbn);
    }


    @ApiOperation(value="查看低库存的书", notes = "注意第一个分页是0")
    @GetMapping(path="/notification")
    public ResponseEntity<?> notification(@RequestParam @Range(min = 0) Integer page,
                                          @RequestParam @Range(min = 1) Integer limit,
                                          @RequestParam @Range(min = 0) Integer left) throws JSONException {
        return stockService.notification(page, limit, left);
    }
}
