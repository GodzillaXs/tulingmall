package com.tulingxueyuan.mall.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @Description:
 * @Author 86131
 * @Date 2021/12/2 15:35
 * @Version 1.0
 */
@Data
@EqualsAndHashCode
@ApiModel(value = "订单支付详情页面之二维码生成的数据传输对象",description = "订单支付详情页面之二维码生成的数据传输对象")
public class OrderDetailTWODTO extends OrderDetailDTO {
    @ApiModelProperty("订单状态")
    private Integer status;
    @ApiModelProperty("订单创建时间")
    private LocalDateTime createTime;
}
