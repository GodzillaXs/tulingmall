package com.tulingxueyuan.mall.modules.oms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tulingxueyuan.mall.dto.OrderDTO;
import com.tulingxueyuan.mall.dto.OrderTwoDTO;
import com.tulingxueyuan.mall.modules.oms.entity.Order;

import java.util.List;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author fyl
 * @since 2021-11-23
 */
public interface OrderService extends IService<Order> {

    Page getList(OrderDTO orderDTO);

    Boolean updateDelivery(List<OrderTwoDTO> list);

    Boolean updateClose(List<Long> ids, String note);
}
