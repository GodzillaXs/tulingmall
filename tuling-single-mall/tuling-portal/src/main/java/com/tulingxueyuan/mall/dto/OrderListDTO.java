package com.tulingxueyuan.mall.dto;

import com.tulingxueyuan.mall.modules.oms.entity.Order;
import com.tulingxueyuan.mall.modules.oms.entity.OrderItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Description:
 * @Author 86131
 * @Date 2021/12/1 20:48
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="我的订单页面初始化数据传输对象", description="我的订单页面初始化数据传输对象")
public class OrderListDTO extends Order{
    @ApiModelProperty(value = "订单商品项列表")
    private List<OrderItem> orderItemList;
}
