package com.tulingxueyuan.mall.modules.oms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.dto.OrderReturnApplyDTO;
import com.tulingxueyuan.mall.dto.UpdateStatusParamDTO;
import com.tulingxueyuan.mall.modules.oms.entity.OrderReturnApply;
import com.tulingxueyuan.mall.modules.oms.mapper.OrderReturnApplyMapper;
import com.tulingxueyuan.mall.modules.oms.service.OrderReturnApplyService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/**
 * <p>
 * 订单退货申请 服务实现类
 * </p>
 *
 * @author fyl
 * @since 2021-11-23
 */
@Service
public class OrderReturnApplyServiceImpl extends ServiceImpl<OrderReturnApplyMapper, OrderReturnApply> implements OrderReturnApplyService {

    @Override
    public Page list(OrderReturnApplyDTO orderReturnApplyDTO) {
        Page page=new Page(orderReturnApplyDTO.getPageNum(),orderReturnApplyDTO.getPageSize());
        QueryWrapper<OrderReturnApply> queryWrapper =new QueryWrapper<>();
        LambdaQueryWrapper<OrderReturnApply> lambdaQueryWrapper = queryWrapper.lambda();
        //服务单号
        if (orderReturnApplyDTO.getId()!=null){
            lambdaQueryWrapper.eq(OrderReturnApply::getId,orderReturnApplyDTO.getId());
        }
        //处理状态
        if (orderReturnApplyDTO.getStatus()!=null){
            lambdaQueryWrapper.eq(OrderReturnApply::getStatus,orderReturnApplyDTO.getStatus());
        }
        //申请时间
        if (!StrUtil.isBlank(orderReturnApplyDTO.getCreateTime())){
            //传过来的是字符串，需要先转换为时间类型
            String createTime = orderReturnApplyDTO.getCreateTime();
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate= LocalDate.parse(createTime,df);
            lambdaQueryWrapper.like(OrderReturnApply::getCreateTime,localDate);
        }
        //操作人员
        if (!StrUtil.isBlank(orderReturnApplyDTO.getHandleMan())){
            lambdaQueryWrapper.like(OrderReturnApply::getHandleMan,orderReturnApplyDTO.getHandleMan());
        }
        //处理时间
        if (!StrUtil.isBlank(orderReturnApplyDTO.getHandleTime())){
            //传过来的是字符串，需要先转换为时间类型
            String handleTime = orderReturnApplyDTO.getHandleTime();
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate=LocalDate.parse(handleTime,df);
            lambdaQueryWrapper.like(OrderReturnApply::getHandleTime,localDate);
        }
        return this.page(page,lambdaQueryWrapper);
    }

    @Override
    public Boolean updateStatusById(Long id, UpdateStatusParamDTO updateStatusParamDTO) {
        UpdateWrapper<OrderReturnApply> updateWrapper=new UpdateWrapper<>();
        LambdaUpdateWrapper<OrderReturnApply> lambdaUpdateWrapper = updateWrapper.lambda();
        if (id==null&&id<1) {
            return false;
        }
        //公司收货地址id
        if (updateStatusParamDTO.getCompanyAddressId()!=null){
            lambdaUpdateWrapper.set(OrderReturnApply::getCompanyAddressId,updateStatusParamDTO.getCompanyAddressId());
        }
        //处理备注
        if (!StrUtil.isBlank(updateStatusParamDTO.getHandleNote())){
            lambdaUpdateWrapper.set(OrderReturnApply::getHandleNote,updateStatusParamDTO.getHandleNote());
        }
        //处理人员
        if (!StrUtil.isBlank(updateStatusParamDTO.getHandleMan())){
            lambdaUpdateWrapper.set(OrderReturnApply::getHandleMan,updateStatusParamDTO.getHandleMan());
        }
        //收货备注
        if (!StrUtil.isBlank(updateStatusParamDTO.getReceiveNote())){
            lambdaUpdateWrapper.set(OrderReturnApply::getReceiveNote,updateStatusParamDTO.getReceiveNote());
        }
        //收货人员
        if (!StrUtil.isBlank(updateStatusParamDTO.getReceiveMan())){
            lambdaUpdateWrapper.set(OrderReturnApply::getReceiveMan,updateStatusParamDTO.getReceiveMan());
        }
        //退款金额
        if (updateStatusParamDTO.getReturnAmount()!=null){
            lambdaUpdateWrapper.set(OrderReturnApply::getReturnAmount,updateStatusParamDTO.getReturnAmount());
        }
        //申请状态
        if (updateStatusParamDTO.getStatus()!=null){
            lambdaUpdateWrapper.set(OrderReturnApply::getStatus,updateStatusParamDTO.getStatus());
        }
        lambdaUpdateWrapper.eq(OrderReturnApply::getId,id);
        return this.update(lambdaUpdateWrapper);
    }
}
