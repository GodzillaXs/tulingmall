package com.tulingxueyuan.mall.modules.oms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.dto.OrderDTO;
import com.tulingxueyuan.mall.dto.OrderTwoDTO;
import com.tulingxueyuan.mall.modules.oms.entity.Order;
import com.tulingxueyuan.mall.modules.oms.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author fyl
 * @since 2021-11-23
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    /**
     * 获取分页数据
     * url:'/order/list',
     * method:'get',
     * params:{
     *          pageNum: 1,
     *          pageSize: 10,
     *          orderSn: null,
     *          receiverKeyword: null,
     *          status: null,
     *          orderType: null,
     *          sourceType: null,
     *          createTime: null,
     *        };
     * 
     * @Date 2021/11/23 13:15
     * @return null 
     */
    @GetMapping("/list")
    public CommonResult getList(OrderDTO orderDTO){
        Page page=orderService.getList(orderDTO);
        //将page转换为commonPage再返回结果
        return CommonResult.success(CommonPage.restPage(page));
    }

    /**
     * 查看订单详情
     * url:'/order/'+id,
     * method:'get'
     *
     * @Date 2021/11/24 14:36
     * @return null
     */
    @GetMapping("/{id}")
    public CommonResult getOrderById(
            @PathVariable("id") Long id
    ){
        Order order = orderService.getById(id);
        return CommonResult.success(order);
    }
    
    /**
     * 发货(批量发货)
     * url:'/order/update/delivery',
     * method:'post',
     * data:
     *      orderId:order.id,
     *      orderSn:order.orderSn,
     *      receiverName:order.receiverName,
     *      receiverPhone:order.receiverPhone,
     *      receiverPostCode:order.receiverPostCode,
     *      address:address,
     *      deliveryCompany:null,
     *      deliverySn:null
     * 
     * @Date 2021/11/24 15:09 
     * @return null 
     */
    @PostMapping("/update/delivery")
    public CommonResult updateDelivery(
            @RequestBody List<OrderTwoDTO> list
    ){
        Boolean result=orderService.updateDelivery(list);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    /**
     * 逻辑删除
     * url:'/order/delete',
     * method:'post',
     * params:params
     *
     * @Date 2021/11/24 16:53
     * @return null
     */
    @PostMapping("/delete")
    public CommonResult deleteById(
            @RequestParam("ids") List<Long> ids
    ){
        boolean result = orderService.removeByIds(ids);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    /**
     * 关闭订单
     * url:'/order/update/close',
     * method:'post',
     * params:params
     *
     * @Date 2021/11/24 17:15
     * @return null
     */
    @PostMapping("/update/close")
    public CommonResult updateClose(
            @RequestParam("ids") List<Long> ids,
            @RequestParam("note") String note
    ){
        Boolean result=orderService.updateClose(ids,note);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

}

