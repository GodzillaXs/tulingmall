package com.tulingxueyuan.mall.modules.oms.controller;


import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.oms.entity.OrderSetting;
import com.tulingxueyuan.mall.modules.oms.service.OrderSettingService;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 订单设置表 前端控制器
 * </p>
 *
 * @author fyl
 * @since 2021-11-23
 */
@RestController
@RequestMapping("/orderSetting")
public class OrderSettingController {

    @Autowired
    private OrderSettingService orderSettingService;

    /**
     * 数据初始化
     * url:'/orderSetting/'+id,
     * method:'get',
     *
     * @Date 2021/11/24 18:49
     * @return null
     */
    @GetMapping("/{id}")
    public CommonResult getById(
            @PathVariable("id") Long id
    ){
        OrderSetting orderSetting = orderSettingService.getById(id);
        return CommonResult.success(orderSetting);
    }

    /**
     * 修改
     * url:'/orderSetting/update/'+id,
     * method:'post',
     * data:data
     *
     * @Date 2021/11/24 18:53
     * @return null
     */
    @PostMapping("/update/{id}")
    public CommonResult updateById(
           @RequestBody OrderSetting orderSetting
    ){
        boolean result = orderSettingService.updateById(orderSetting);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }
}

