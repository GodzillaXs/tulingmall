package com.tulingxueyuan.mall.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 *退货申请处理交互数据类
 *     updateStatusParam: {
 *          companyAddressId: null,
 *          handleMan: 'admin',
 *          handleNote: null,
 *          receiveMan: 'admin',
 *          receiveNote: null,
 *          returnAmount: 0,
 *          status: 0
 *      }
 * @Author 86131
 * @Date 2021/11/24 21:11
 * @Version 1.0
 */
@Data
@EqualsAndHashCode
@ApiModel(value = "退货申请处理接收数据",description = "用于退货申请处理同意退货")
public class UpdateStatusParamDTO {
    @ApiModelProperty(value = "公司收发货地址表id")
    private Long companyAddressId;
    @ApiModelProperty(value = "处理备注")
    private String handleNote;
    @ApiModelProperty(value = "处理人员")
    private String handleMan;
    @ApiModelProperty(value = "收货人")
    private String receiveMan;
    @ApiModelProperty(value = "收货备注")
    private String receiveNote;
    @ApiModelProperty(value = "退款金额")
    private BigDecimal returnAmount;
    @ApiModelProperty(value = "申请状态：0->待处理；1->退货中；2->已完成；3->已拒绝")
    private Integer status;
}
