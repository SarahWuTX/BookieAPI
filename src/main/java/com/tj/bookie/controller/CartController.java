package com.tj.bookie.controller;

import com.tj.bookie.service.CartService;
import com.tj.bookie.utility.request.InputCart;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Range;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;


@RestController
@RequestMapping("/cart")
@Validated
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @ApiOperation(value="保存到购物车", notes = "不用管是新的条目还是旧的条目，都用这个接口")
    @PostMapping(path="/save")
    public ResponseEntity<?> save(@RequestBody InputCart inputCart) {
        return cartService.save(inputCart);
    }


    @ApiOperation(value="查看购物车", notes = "注意第一个分页是0，每页10条")
    @GetMapping(path="/get")
    public ResponseEntity<?> get(@RequestParam @NotEmpty String wxId,
                                 @RequestParam @Range(min = 0) Integer page) throws JSONException {
        return cartService.get(wxId, page);
    }


    @ApiOperation(value="从购物车删除", notes = "返回1或0，1表示成功，0多半是购物车没有那一项，用户不存在会404")
    @DeleteMapping(path="/delete")
    public ResponseEntity<?> delete(@RequestParam @NotEmpty String wxId,
                                 @RequestParam @Range(min = 0) Integer bookId) {
        return cartService.delete(wxId, bookId);
    }
}
