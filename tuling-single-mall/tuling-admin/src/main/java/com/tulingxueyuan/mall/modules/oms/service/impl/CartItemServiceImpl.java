package com.tulingxueyuan.mall.modules.oms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.modules.oms.entity.CartItem;
import com.tulingxueyuan.mall.modules.oms.mapper.CartItemMapper;
import com.tulingxueyuan.mall.modules.oms.service.CartItemService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 购物车表 服务实现类
 * </p>
 *
 * @author fyl
 * @since 2021-11-23
 */
@Service
public class CartItemServiceImpl extends ServiceImpl<CartItemMapper, CartItem> implements CartItemService {

}
