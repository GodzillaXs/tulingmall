package com.tulingxueyuan.mall.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 *
 * pageNum: 1,
 * pageSize: 10,
 * orderSn: null,
 * receiverKeyword: null,
 * status: null,
 * orderType: null,
 * sourceType: null,
 * createTime: null,
 * @Author 86131
 * @Date 2021/11/23 13:50
 * @Version 1.0
 */
@Data
@EqualsAndHashCode
@ApiModel(value = "订单列表筛选条件",description = "用于订单列表显示分页数据")
public class OrderDTO {
    @ApiModelProperty(value = "收货人姓名或者手机号码")
    private String receiverKeyword;
    private Integer pageNum;
    private Integer pageSize;
    @ApiModelProperty(value = "订单编号")
    private String orderSn;
    @ApiModelProperty(value = "订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单")
    private Integer status;
    @ApiModelProperty(value = "订单类型：0->正常订单；1->秒杀订单")
    private Integer orderType;
    @ApiModelProperty(value = "订单来源：0->PC订单；1->app订单")
    private Integer sourceType;
    @ApiModelProperty(value = "提交时间")
    private String createTime;


}
