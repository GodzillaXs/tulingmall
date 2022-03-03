package com.tulingxueyuan.mall.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 *              pageNum: 1,
 *              pageSize: 10,
 *              id: null,
 *              receiverKeyword: null,
 *              status: null,
 *              createTime: null,
 *              handleMan: null,
 *              handleTime: null
 * @Author 86131
 * @Date 2021/11/24 19:12
 * @Version 1.0
 */
@Data
@EqualsAndHashCode
@ApiModel(value = "退货申请处理筛选列表",description = "用于退货申请处理显示分页数据")
public class OrderReturnApplyDTO {
    private Integer pageNum;
    private Integer pageSize;
    @ApiModelProperty(value = "退货申请处理表id")
    private Long id;
    @ApiModelProperty(value = "申请时间")
    private String createTime;
    @ApiModelProperty(value = "申请状态：0->待处理；1->退货中；2->已完成；3->已拒绝")
    private Integer status;
    @ApiModelProperty(value = "处理时间")
    private String handleTime;
    @ApiModelProperty(value = "处理人员")
    private String handleMan;


}
