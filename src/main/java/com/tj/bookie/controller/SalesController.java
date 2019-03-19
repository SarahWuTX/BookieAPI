package com.tj.bookie.controller;

import com.tj.bookie.service.SalesService;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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


    @ApiOperation(value="查看周报表", notes = "可以用了，表示时间的数组是timeline")
    @GetMapping(path="/week")
    public ResponseEntity<?> getTrendByWeek() {
        return salesService.getTrendByWeek();
    }


    @ApiOperation(value="查看月报表", notes = "可以用了，表示时间的数组是timeline")
    @GetMapping(path="/month")
    public ResponseEntity<?> getTrendByMonth() {
        return salesService.getTrendByMonth();
    }


    @ApiOperation(value="查看季报表", notes = "可以用了，表示时间的数组是timeline")
    @GetMapping(path="/season")
    public ResponseEntity<?> getTrendBySeason() {
        return salesService.getTrendBySeason();
    }


    @ApiOperation(value="查询某季度每类书的销量分布", notes = "参数表示前n个季度，例如当季是0，上个季度是1，上上个季度是2…")
    @GetMapping(path="/distribution/category")
    public ResponseEntity<?> getDistributionBySeason(@RequestParam @Range(min = 0) Integer season){
        return salesService.getDistributionBySeason(season);
    }

    @ApiOperation(value="查询用户购书量分布", notes = "返回int[n][2]，表示有n个int[2]数组\n" +
            "对于每一个int[2]，int[0]是购书量，int[1]是用户数量，这n组按照购书量升序排序，你看看能不能做个折线图之类的")
    @GetMapping(path="/distribution/user")
    public ResponseEntity<?> getDistributionByCount() {
        return salesService.getDistributionByCount();
    }
}
