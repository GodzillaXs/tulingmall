package com.tulingxueyuan.mall.modules.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.dto.OrderDetailDTO;
import com.tulingxueyuan.mall.dto.OrderDetailTWODTO;
import com.tulingxueyuan.mall.modules.oms.entity.Order;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author fyl
 * @since 2021-11-29
 */
public interface OrderMapper extends BaseMapper<Order> {

    OrderDetailDTO getOrderDetail(Long orderId);

    OrderDetailTWODTO getOrderDetailTwo(Long orderId);

    Page getOrderPage(Page page, Long id);


}
