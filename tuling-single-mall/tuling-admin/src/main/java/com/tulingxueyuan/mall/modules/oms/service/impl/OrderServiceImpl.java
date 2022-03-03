package com.tulingxueyuan.mall.modules.oms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.dto.OrderDTO;
import com.tulingxueyuan.mall.dto.OrderTwoDTO;
import com.tulingxueyuan.mall.modules.oms.entity.Order;
import com.tulingxueyuan.mall.modules.oms.mapper.OrderMapper;
import com.tulingxueyuan.mall.modules.oms.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author fyl
 * @since 2021-11-23
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Override
    public Page getList(OrderDTO orderDTO) {
        Page page=new Page(orderDTO.getPageNum(),orderDTO.getPageSize());
        QueryWrapper<Order> queryWrapper=new QueryWrapper<>();
        LambdaQueryWrapper<Order> lambdaQueryWrapper = queryWrapper.lambda();
        //收货人姓名或者手机号码
        if (!StrUtil.isBlank(orderDTO.getReceiverKeyword())){
            Pattern pattern = Pattern.compile("[0-9]*");
            if(pattern.matcher(orderDTO.getReceiverKeyword()).matches()){
                lambdaQueryWrapper.like(Order::getReceiverPhone,orderDTO.getReceiverKeyword());
            }else {
                lambdaQueryWrapper.like(Order::getReceiverName,orderDTO.getReceiverKeyword());
            }
        }
        //订单编号
        if (!StrUtil.isBlank(orderDTO.getOrderSn())){
            lambdaQueryWrapper.like(Order::getOrderSn,orderDTO.getOrderSn());
        }
        //提交时间
        if (orderDTO.getCreateTime()!=null){
            //传过来的是字符串，需要先转换为时间类型
            String createTime = orderDTO.getCreateTime();
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate= LocalDate.parse(createTime,df);
            lambdaQueryWrapper.like(Order::getCreateTime,localDate);
        }
        //订单状态
        if (orderDTO.getStatus()!=null){
            lambdaQueryWrapper.eq(Order::getStatus,orderDTO.getStatus());
        }
        //订单类型
        if (orderDTO.getOrderType()!=null){
            lambdaQueryWrapper.eq(Order::getOrderType,orderDTO.getOrderType());
        }
        //订单来源
        if (orderDTO.getSourceType()!=null){
            lambdaQueryWrapper.eq(Order::getSourceType,orderDTO.getSourceType());
        }
//        lambdaQueryWrapper.orderByAsc();
        return this.page(page,lambdaQueryWrapper);
    }

    @Override
    @Transactional
    public Boolean updateDelivery(List<OrderTwoDTO> list) {
        for (OrderTwoDTO orderTwoDTO : list) {
            UpdateWrapper<Order> updateWrapper=new UpdateWrapper();
            if (orderTwoDTO.getOrderId()==null){
                return false;
            }
            LambdaUpdateWrapper<Order> lambdaUpdateWrapper = updateWrapper.lambda();
            //订单状态
            lambdaUpdateWrapper.set(Order::getStatus,2);
            //物流公司
            lambdaUpdateWrapper.set(Order::getDeliveryCompany,orderTwoDTO.getDeliveryCompany());
            //物流单号
            lambdaUpdateWrapper.set(Order::getDeliverySn,orderTwoDTO.getDeliverySn());
            lambdaUpdateWrapper.eq(Order::getId,orderTwoDTO.getOrderId());
            this.update(lambdaUpdateWrapper);
        }
        return true;
    }

    @Override
    public Boolean updateClose(List<Long> ids, String note) {
        UpdateWrapper<Order> updateWrapper=new UpdateWrapper<>();
        LambdaUpdateWrapper<Order> lambdaUpdateWrapper = updateWrapper.lambda();
        //订单状态修改为已关闭
        lambdaUpdateWrapper.set(Order::getStatus,4);
        //给被关闭的订单都加上备注
        lambdaUpdateWrapper.set(Order::getNote,note);
        lambdaUpdateWrapper.in(Order::getId,ids);
        return this.update(lambdaUpdateWrapper);
    }
}
