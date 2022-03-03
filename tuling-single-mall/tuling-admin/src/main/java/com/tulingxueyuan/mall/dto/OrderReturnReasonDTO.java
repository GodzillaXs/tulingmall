package com.tulingxueyuan.mall.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 退货原因设置添加数据
 * @Author 86131
 * @Date 2021/11/24 23:15
 * @Version 1.0
 */
@Data
@EqualsAndHashCode
@ApiModel(value = "退货原因设置添加数据",description = "退货原因设置添加数据")
public class OrderReturnReasonDTO {
    @ApiModelProperty(value = "退货类型")
    private String name;

    private Integer sort;

    @ApiModelProperty(value = "状态：0->不启用；1->启用")
    private Integer status;

    @ApiModelProperty(value = "添加时间")
    private String createTime;
}
