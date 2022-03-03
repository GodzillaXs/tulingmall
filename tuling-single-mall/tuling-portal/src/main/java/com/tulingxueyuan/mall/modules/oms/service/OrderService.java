package com.tulingxueyuan.mall.modules.oms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tulingxueyuan.mall.dto.*;
import com.tulingxueyuan.mall.modules.oms.entity.Order;

import java.util.List;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author fyl
 * @since 2021-11-29
 */
public interface OrderService extends IService<Order> {

    ConfirmOrderDTO generateConfirmOrder(OrderDTO orderDTO);

    Order generateOrder(OrderParamDTO paramDTO);

    OrderDetailDTO getOrderDetail(Long orderId);

    OrderDetailTWODTO getOrderDetailTwo(Long orderId);

    void cancelOverTimeOrder();

    Page getListUserOrder(Integer pageNum, Integer pageSize);


    void paySuccess(Long orderId, Integer payType);
}
