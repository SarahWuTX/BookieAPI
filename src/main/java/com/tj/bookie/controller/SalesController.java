package com.tj.bookie.controller;

import com.tj.bookie.service.SalesService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sales")
@Validated
public class SalesController {
    private final SalesService salesService;

    @Autowired
    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }


    @ApiOperation(value="查看周报表", notes = "可以用了")
    @GetMapping(path="/week")
    public ResponseEntity<?> getTrendByWeek() {
        return salesService.getTrendByWeek();
    }


    @ApiOperation(value="查看月报表", notes = "这个大概是能用的，但是没测试好，也没数据")
    @GetMapping(path="/month")
    public ResponseEntity<?> getTrendByMonth() {
        return salesService.getTrendByMonth();
    }


    @ApiOperation(value="查看季报表", notes = "这个还没写")
    @GetMapping(path="/season")
    public ResponseEntity<?> getTrendBySeason() {
        return salesService.getTrendBySeason();
    }
}
