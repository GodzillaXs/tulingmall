package com.tulingxueyuan.mall.modules.oms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.dto.OrderReturnReasonDTO;
import com.tulingxueyuan.mall.modules.oms.entity.OrderReturnReason;
import com.tulingxueyuan.mall.modules.oms.service.OrderReturnReasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 退货原因表 前端控制器
 * </p>
 *
 * @author fyl
 * @since 2021-11-23
 */
@RestController
@RequestMapping("/returnReason")
public class OrderReturnReasonController {

    @Autowired
    private OrderReturnReasonService orderReturnReasonService;

    /**
     * 分页数据
     * url:'/returnReason/list',
     * method:'get',
     * params:
     *            pageNum: 1,
     *            pageSize: 5
     *
     * @Date 2021/11/24 22:44
     * @return null
     */
    @GetMapping("/list")
    public CommonResult list(
            @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize
    ){
        Page page=orderReturnReasonService.list(pageNum,pageSize);
        return CommonResult.success(CommonPage.restPage(page));
    }

    /**
     * 可用状态栏更改
     * url:'/returnReason/update/status',
     * method:'post',
     * params:params
     *
     * @Date 2021/11/24 22:58
     * @return null
     */
    @PostMapping("/update/status")
    public CommonResult updateStatus(
            @RequestParam("status") Integer status,
            @RequestParam("ids") List<Long> ids
    ){
        Boolean result=orderReturnReasonService.updateStatus(status,ids);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    /**
     * 添加
     * url:'/returnReason/create',
     * method:'post',
     * data:data
     *
     * @Date 2021/11/24 23:14
     * @return null
     */
    @PostMapping("/create")
    public CommonResult create(
            @RequestBody OrderReturnReason orderReturnReason
    ){
        boolean result = orderReturnReasonService.save(orderReturnReason);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    /**
     * 修改前初始化数据
     * url:'/returnReason/'+id,
     * method:'get'
     *
     * @Date 2021/11/24 23:29
     * @return null
     */
    @GetMapping("/{id}")
    public CommonResult getById(
            @PathVariable("id") Long id
    ){
        OrderReturnReason orderReturnReason = orderReturnReasonService.getById(id);
        return CommonResult.success(orderReturnReason);
    }

    /**
     * 修改
     * url:'/returnReason/update/'+id,
     * method:'post',
     * data:data
     *
     * @Date 2021/11/24 23:32
     * @return null
     */
    @PostMapping("/update/{id}")
    public CommonResult updateById(
            @RequestBody OrderReturnReason orderReturnReason
    ){
        boolean result = orderReturnReasonService.updateById(orderReturnReason);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    /**
     * 删除(可批量)
     * url:'/returnReason/delete',
     * method:'post',
     * params:params
     *
     * @Date 2021/11/24 23:39
     * @return null
     */
    @PostMapping("/delete")
    public CommonResult delete(
            @RequestParam("ids") List<Long> ids
    ){
        boolean result = orderReturnReasonService.removeByIds(ids);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }
}

