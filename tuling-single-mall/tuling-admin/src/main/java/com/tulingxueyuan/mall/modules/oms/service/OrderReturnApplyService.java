package com.tulingxueyuan.mall.modules.oms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tulingxueyuan.mall.dto.OrderReturnApplyDTO;
import com.tulingxueyuan.mall.dto.UpdateStatusParamDTO;
import com.tulingxueyuan.mall.modules.oms.entity.OrderReturnApply;

/**
 * <p>
 * 订单退货申请 服务类
 * </p>
 *
 * @author fyl
 * @since 2021-11-23
 */
public interface OrderReturnApplyService extends IService<OrderReturnApply> {

    Page list(OrderReturnApplyDTO orderReturnApplyDTO);

    Boolean updateStatusById(Long id, UpdateStatusParamDTO updateStatusParamDTO);
}
