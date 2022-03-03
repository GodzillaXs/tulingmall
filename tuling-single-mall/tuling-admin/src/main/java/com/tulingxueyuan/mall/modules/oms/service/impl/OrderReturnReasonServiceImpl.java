package com.tulingxueyuan.mall.modules.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.dto.OrderReturnReasonDTO;
import com.tulingxueyuan.mall.modules.oms.entity.OrderReturnReason;
import com.tulingxueyuan.mall.modules.oms.mapper.OrderReturnReasonMapper;
import com.tulingxueyuan.mall.modules.oms.service.OrderReturnReasonService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * <p>
 * 退货原因表 服务实现类
 * </p>
 *
 * @author fyl
 * @since 2021-11-23
 */
@Service
public class OrderReturnReasonServiceImpl extends ServiceImpl<OrderReturnReasonMapper, OrderReturnReason> implements OrderReturnReasonService {

    @Override
    public Page list(Integer pageNum, Integer pageSize) {
        Page page=new Page(pageNum,pageSize);
        return this.page(page);
    }

    @Override
    public Boolean updateStatus(Integer status, List<Long> ids) {
        UpdateWrapper<OrderReturnReason> updateWrapper=new UpdateWrapper<>();
        updateWrapper.lambda().set(OrderReturnReason::getStatus,status);
        updateWrapper.lambda().in(OrderReturnReason::getId,ids);
        updateWrapper.lambda().orderByAsc(OrderReturnReason::getSort);
        return this.update(updateWrapper);
    }
}
