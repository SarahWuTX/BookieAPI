package com.tj.bookie.controller;

import com.tj.bookie.service.CartService;
import com.tj.bookie.service.OrderService;
import com.tj.bookie.utility.request.InputCart;
import com.tj.bookie.utility.request.InputOrder;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


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
    public ResponseEntity<?> getAll(@RequestParam @NotEmpty String wxId,
                                    @RequestParam @Range(min = 0) Integer page) {
        return orderService.getAll(wxId, page);
    }


    @ApiOperation(value="查看某类订单", notes = "未付款、未到货的可能不多，这个就不分页了")
    @GetMapping(path="/getByStatus")
    public ResponseEntity<?> getByStatus(@RequestParam @NotEmpty String wxId,
                                         @RequestParam @Range(min = 0) Integer status) {
        return orderService.getByStatus(wxId, status);
    }


    @ApiOperation(value="创建订单", notes = "地址仍然是传string list，依次是 省-市-区-详细地址，省份不能缺\n" +
            "省份不在delivery表中时快递费是0\n快递费包含在总价中\n订单传cartId的int list\n" +
            "cart或user不存在都会404\n返回order对象，orderId用于支付")
    @PostMapping(path="/create")
    public ResponseEntity<?> create(@RequestBody @NotNull InputOrder inputOrder) {
        return orderService.create(inputOrder);
    }


    @ApiOperation(value="支付订单", notes = "暂时只传orderId就可以，返回更新的order对象")
    @PostMapping(path = "/pay")
    public ResponseEntity<?> pay (@RequestBody @Range(min = 0) Integer orderId) {
        return orderService.pay(orderId);
    }


    @ApiOperation(value="获取某订单详情", notes = "")
    @GetMapping(path = "/get")
    public ResponseEntity<?> get(@RequestParam @Range(min = 0) Integer orderId) {
        return orderService.get(orderId);
    }
}
