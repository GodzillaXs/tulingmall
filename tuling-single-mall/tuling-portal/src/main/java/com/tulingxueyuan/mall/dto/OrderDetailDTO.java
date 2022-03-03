package com.tulingxueyuan.mall.dto;

import com.tulingxueyuan.mall.modules.oms.entity.OrderItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description:
 * @Author 86131
 * @Date 2021/12/1 15:28
 * @Version 1.0
 */
@Data
@EqualsAndHashCode
@ApiModel(value = "订单支付详情页面数据传输对象",description = "订单支付详情页面数据传输对象")
public class OrderDetailDTO {
    @ApiModelProperty(value = "订单id")
    private Long id;

    @ApiModelProperty(value = "应付金额（实际支付金额）")
    private BigDecimal payAmount;

    @ApiModelProperty(value = "省份/直辖市")
    private String receiverProvince;

    @ApiModelProperty(value = "城市")
    private String receiverCity;

    @ApiModelProperty(value = "区")
    private String receiverRegion;

    @ApiModelProperty(value = "详细地址")
    private String receiverDetailAddress;

    @ApiModelProperty(value = "订单编号")
    private String orderSn;

    @ApiModelProperty(value = "订单商品列表")
    private List<OrderItem> orderItemList;

    @ApiModelProperty(value = "正常订单超时时间(分)")
    private Integer normalOrderOvertime;

}
