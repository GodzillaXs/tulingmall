package com.tulingxueyuan.mall.dto;

import com.tulingxueyuan.mall.modules.oms.entity.CartItem;
import com.tulingxueyuan.mall.modules.ums.entity.MemberReceiveAddress;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description:
 * @Author 86131
 * @Date 2021/11/29 15:13
 * @Version 1.0
 */
@Data
@EqualsAndHashCode
@ApiModel(value = "确认订单页面的数据传输对象",description = "确认订单页面的数据传输对象")
public class ConfirmOrderDTO {
    @ApiModelProperty(value = "商品列表")
    private List<CartItem> cartItemList;
    @ApiModelProperty(value="商品数量")
    private Integer productTotal;
    @ApiModelProperty(value="总价")
    private BigDecimal priceTotal;
    @ApiModelProperty(value="运费")
    private BigDecimal freightAmount;
    @ApiModelProperty(value="实际应付总额")
    private BigDecimal payAmount;
    @ApiModelProperty(value = "用户的收货地址列表")
    private List<MemberReceiveAddress>  addressList;
}
