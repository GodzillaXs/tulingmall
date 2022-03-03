package com.tulingxueyuan.mall.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 *      orderId:order.id,
 *      orderSn:order.orderSn,
 *      receiverName:order.receiverName,
 *      receiverPhone:order.receiverPhone,
 *      receiverPostCode:order.receiverPostCode,
 *      address:address,
 *      deliveryCompany:null,
 *      deliverySn:null
 * @Author 86131
 * @Date 2021/11/24 15:17
 * @Version 1.0
 */
@Data
@EqualsAndHashCode
@ApiModel(value = "发货列表筛选条件",description = "用于订单列表发货")
public class OrderTwoDTO {
    @ApiModelProperty(value = "订单id")
    private Long orderId;
//    @ApiModelProperty(value = "订单编号")
//    private String orderSn;
//    @ApiModelProperty(value = "收货人姓名")
//    private String receiverName;
//    @ApiModelProperty(value = "收货人电话")
//    private String receiverPhone;
//    @ApiModelProperty(value = "收货人邮编")
//    private String receiverPostCode;
//    @ApiModelProperty(value = "发货地址")
//    private String address;
    @ApiModelProperty(value = "物流公司(配送方式)")
    private String deliveryCompany;
    @ApiModelProperty(value = "物流单号")
    private String deliverySn;
}
