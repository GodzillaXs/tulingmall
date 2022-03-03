package com.tulingxueyuan.mall.controller;

import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.ums.entity.MemberReceiveAddress;
import com.tulingxueyuan.mall.modules.ums.service.MemberReceiveAddressService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:
 * @Author 86131
 * @Date 2021/11/30 15:28
 * @Version 1.0
 */
@RestController
@Api(tags = "MemberAddressController",description = "用户收货地址内容管理")
@RequestMapping("/member/address")
public class MemberAddressController {
    @Autowired
    MemberReceiveAddressService memberReceiveAddressService;

    /**
     * 添加地址
     * (method = "post"), (url = "/member/address/add");
     * @Date 2021/11/30 15:35
     * @return null
     */
    @PostMapping("/add")
    public CommonResult add(
            @RequestBody MemberReceiveAddress memberReceiveAddress
            ){
        boolean result=memberReceiveAddressService.add(memberReceiveAddress);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }
    /**
     * 修改地址
     * (method = "post"), (url = `/member/address/update/${checkedItem.id}`);
     * @Date 2021/11/30 15:35
     * @return null
     */
    @PostMapping("/update/{id}")
    public CommonResult update(
            @PathVariable("id") Long id,
            @RequestBody MemberReceiveAddress memberReceiveAddress
    ){
        memberReceiveAddress.setId(id);
        boolean result=memberReceiveAddressService.update(memberReceiveAddress);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }
    /**
     * 删除地址
     * (method = "post"), (url = `/member/address/delete/${checkedItem.id}`);
     * @Date 2021/11/30 15:35
     * @return null
     */
    @PostMapping("/delete/{id}")
    public CommonResult delete(
            @PathVariable("id") Long id
    ){
        boolean result=memberReceiveAddressService.delete(id);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    /**
     *  刷新当前用户的收货地址列表
     *  this.axios.get("/member/address/list")
     * @Date 2021/11/30 15:46
     * @return null
     */
    @GetMapping("/list")
    public CommonResult getList(){
        List<MemberReceiveAddress> addressList=memberReceiveAddressService.getList();
        return CommonResult.success(addressList);
    }

}
