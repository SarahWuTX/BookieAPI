package com.tj.bookie.controller;

import com.tj.bookie.service.CartService;
import com.tj.bookie.service.OrderService;
import com.tj.bookie.utility.request.InputCart;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;


@RestController
@RequestMapping("/order")
@Validated
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @ApiOperation(value="查看所有订单", notes = "注意第一个分页是0，每页10条")
    @GetMapping(path="/getAll")
    public ResponseEntity<?> getAll(@RequestParam @NotEmpty String wx_id,
                                    @RequestParam @Range(min = 0) Integer page) {
        return orderService.getAll(wx_id, page);
    }


    @ApiOperation(value="查看某类订单", notes = "未付款、未到货的可能不多，这个就不分页了")
    @GetMapping(path="/getByStatus")
    public ResponseEntity<?> getByStatus(@RequestParam @NotEmpty String wx_id,
                                         @RequestParam @Range(min = 0) Integer status) {
        return orderService.getByStatus(wx_id, status);
    }
}
