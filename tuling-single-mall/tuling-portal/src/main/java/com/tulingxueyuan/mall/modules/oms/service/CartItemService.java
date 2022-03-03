package com.tulingxueyuan.mall.modules.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tulingxueyuan.mall.dto.AddCarDTO;
import com.tulingxueyuan.mall.dto.CartItemStockDTO;
import com.tulingxueyuan.mall.modules.oms.entity.CartItem;

import java.util.List;

/**
 * <p>
 * 购物车表 服务类
 * </p>
 *
 * @author fyl
 * @since 2021-11-28
 */
public interface CartItemService extends IService<CartItem> {

    boolean add(AddCarDTO addCarDTO);

    Integer getCarProductSum();

    List<CartItemStockDTO> getList();

    boolean updateQuantity(Long id, Integer quantity);

    boolean delete(Long id);

    Integer getQuantity(Long productSkuId, Long productId);
}
