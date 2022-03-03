package com.tulingxueyuan.mall.modules.oms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.dto.OrderReturnApplyDTO;
import com.tulingxueyuan.mall.dto.UpdateStatusParamDTO;
import com.tulingxueyuan.mall.modules.oms.entity.OrderReturnApply;
import com.tulingxueyuan.mall.modules.oms.service.OrderReturnApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 订单退货申请 前端控制器
 * </p>
 *
 * @author fyl
 * @since 2021-11-23
 */
@RestController
@RequestMapping("/returnApply")
public class OrderReturnApplyController {

    @Autowired
    OrderReturnApplyService orderReturnApplyService;

    /**
     * 获取数据
     * url:'/returnApply/list',
     * method:'get',
     * params:
     *              pageNum: 1,
     *              pageSize: 10,
     *              id: null,
     *              receiverKeyword: null,
     *              status: null,
     *              createTime: null,
     *              handleMan: null,
     *              handleTime: null
     *
     * @Date 2021/11/24 19:03
     * @return null
     */
    @GetMapping("/list")
    public CommonResult list(
            OrderReturnApplyDTO orderReturnApplyDTO
    ){
        Page page=orderReturnApplyService.list(orderReturnApplyDTO);
        return CommonResult.success(CommonPage.restPage(page));
    }

    /**
     * 查看详情
     * url:'/returnApply/'+id,
     * method:'get'
     *
     * @Date 2021/11/24 20:03
     * @return null
     */
    @GetMapping("/{id}")
    public CommonResult getById(
            @PathVariable("id") Long id
    ){
        OrderReturnApply orderReturnApply = orderReturnApplyService.getById(id);
        return CommonResult.success(orderReturnApply);
    }

    /**
     * 修改
     * url:'/returnApply/update/status/'+id,
     * method:'post',
     * data:
     *      updateStatusParam: {
     *          companyAddressId: null,
     *          handleMan: 'admin',
     *          handleNote: null,
     *          receiveMan: 'admin',
     *          receiveNote: null,
     *          returnAmount: 0,
     *          status: 0
     *      }
     *
     * @Date 2021/11/24 20:53
     * @return null
     */
    @PostMapping("/update/status/{id}")
    public CommonResult updateStatusById(
            @PathVariable("id") Long id,
            @RequestBody UpdateStatusParamDTO updateStatusParamDTO
    ){
        Boolean result=orderReturnApplyService.updateStatusById(id,updateStatusParamDTO);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

}

