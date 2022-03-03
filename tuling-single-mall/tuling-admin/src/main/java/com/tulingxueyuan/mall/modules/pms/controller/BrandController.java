package com.tulingxueyuan.mall.modules.pms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.entity.Brand;
import com.tulingxueyuan.mall.modules.pms.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * 品牌表 前端控制器
 * </p>
 *
 * @author fyl
 * @since 2021-11-15
 */
@RestController
@RequestMapping("brand")
public class BrandController {

    @Autowired
    BrandService brandService;

    /**
     * 品牌管理的数据列表
     * url:'/brand/list',
     * method:'get',
     * params:params
     *
     * @Date 2021/11/19 19:57
     * @return null
     */
    @GetMapping("/list")
    public CommonResult list(
            @RequestParam(value = "keyword",defaultValue = "") String keyword,
            @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize
    ){

        Page page = brandService.list(keyword, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(page));
    }

    /**
     * 添加
     * url:'/brand/create',
     * method:'post',
     * data:data
     *
     * @Date 2021/11/20 15:26
     * @return null
     */
    @PostMapping("/create")
    public CommonResult creat(
            @RequestBody Brand brand
    ){
        boolean result=brandService.creat(brand);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    /**
     * 编辑的数据初始化
     * url:'/brand/'+id,
     * method:'get',
     *
     * @Date 2021/11/20 15:50
     * @return null
     */
    @GetMapping("/{id}")
    public CommonResult getById(
            @PathVariable("id") Long id
    ){
        Brand result = brandService.getById(id);
        return CommonResult.success(result);
    }

    /**
     * 修改
     * url:'/brand/update/'+id,
     * method:'post',
     * data:data
     *
     * @Date 2021/11/20 15:46
     * @return null
     */
    @PostMapping("/update/{id}")
    public CommonResult updateById(
            @RequestBody Brand brand
    ){
        boolean result=brandService.updateById(brand);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    /**
     * 删除
     * url:'/brand/delete/'+id,
     * method:'get',
     *
     * @Date 2021/11/20 16:02
     * @return null
     */
    @GetMapping("/delete/{id}")
    public CommonResult deleteById(
            @PathVariable("id") Long id
    ){
        boolean result = brandService.removeById(id);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    /**
     * 品牌制造商状态栏
     *export function updateShowStatus(data) {
     *     return request({
     *       url:'/brand/update/showStatus',
     *       method:'post',
     *       data:data
     *     })
     *   }
     */
    @PostMapping("/update/showStatus")
    public CommonResult updateShowStatus(
            @RequestParam("ids") List<Long> ids,
            @RequestParam("showStatus") Integer showStatus
    ){
        boolean result = brandService.updateStatus(ids,showStatus,Brand::getShowStatus);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    /**
     * 是否显示状态栏
     *    export function updateFactoryStatus(data) {
     *      return request({
     *        url:'/brand/update/factoryStatus',
     *        method:'post',
     *        data:data
     *      })
     *    }
     */
    @PostMapping("/update/factoryStatus")
    public CommonResult updateFactoryStatus(
            @RequestParam("ids") List<Long> ids,
            @RequestParam("factoryStatus") Integer factoryStatus
    ){
        boolean result = brandService.updateStatus(ids,factoryStatus,Brand::getFactoryStatus);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

}

