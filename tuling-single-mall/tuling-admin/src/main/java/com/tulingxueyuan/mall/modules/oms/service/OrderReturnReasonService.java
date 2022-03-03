package com.tulingxueyuan.mall.modules.oms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tulingxueyuan.mall.dto.OrderReturnReasonDTO;
import com.tulingxueyuan.mall.modules.oms.entity.OrderReturnReason;

import java.util.List;

/**
 * <p>
 * 退货原因表 服务类
 * </p>
 *
 * @author fyl
 * @since 2021-11-23
 */
public interface OrderReturnReasonService extends IService<OrderReturnReason> {

    Page list(Integer pageNum, Integer pageSize);

    Boolean updateStatus(Integer status, List<Long> ids);

}
