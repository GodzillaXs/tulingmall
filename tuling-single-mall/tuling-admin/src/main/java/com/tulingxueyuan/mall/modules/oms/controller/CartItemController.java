package com.tulingxueyuan.mall.modules.oms.controller;

import com.tulingxueyuan.mall.modules.oms.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 购物车表 前端控制器
 * </p>
 *
 * @author fyl
 * @since 2021-11-23
 */
@RestController
@RequestMapping("/oms/cart-item")
public class CartItemController {

    @Autowired
    CartItemService cartItemService;


}

