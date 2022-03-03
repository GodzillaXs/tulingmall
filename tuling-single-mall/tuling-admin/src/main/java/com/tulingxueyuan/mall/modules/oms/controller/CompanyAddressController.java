package com.tulingxueyuan.mall.modules.oms.controller;


import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.oms.entity.CompanyAddress;
import com.tulingxueyuan.mall.modules.oms.service.CompanyAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 公司收发货地址表 前端控制器
 * </p>
 *
 * @author fyl
 * @since 2021-11-23
 */
@RestController
@RequestMapping("/companyAddress")
public class CompanyAddressController {

    @Autowired
    private CompanyAddressService companyAddressService;

    /**
     * 获取列表数据
     * url:'/companyAddress/list',
     * method:'get'
     *
     * @Date 2021/11/24 20:17
     * @return null
     */
    @GetMapping("/list")
    public CommonResult list(){
        List<CompanyAddress> list = companyAddressService.list();
        return CommonResult.success(list);
    }

}

