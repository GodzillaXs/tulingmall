package com.tulingxueyuan.mall.modules.oms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.tulingxueyuan.mall.dto.CartItemStockDTO;
import com.tulingxueyuan.mall.modules.oms.entity.CartItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 购物车表 Mapper 接口
 * </p>
 *
 * @author fyl
 * @since 2021-11-28
 */
public interface CartItemMapper extends BaseMapper<CartItem> {

    List<CartItemStockDTO> getCartItemStockDTOList(Long id);

    List<CartItemStockDTO> getCartItemStockByIds(@Param(Constants.WRAPPER) Wrapper Wrapper);
}
