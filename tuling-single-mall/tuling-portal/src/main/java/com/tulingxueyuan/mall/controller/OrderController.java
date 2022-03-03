package com.tulingxueyuan.mall.controller;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.common.exception.ApiException;
import com.tulingxueyuan.mall.dto.ConfirmOrderDTO;
import com.tulingxueyuan.mall.dto.OrderDTO;
import com.tulingxueyuan.mall.dto.OrderDetailDTO;
import com.tulingxueyuan.mall.dto.OrderParamDTO;
import com.tulingxueyuan.mall.modules.oms.entity.Order;
import com.tulingxueyuan.mall.modules.oms.service.OrderService;
import com.tulingxueyuan.mall.modules.oms.service.TradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author 86131
 * @Date 2021/11/29 14:53
 * @Version 1.0
 */
@RestController
@Api(tags = "OrderController",description = "订单内容管理")
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    OrderService orderService;

    @ApiModelProperty(value = "为了避免代码都写在orderService显得太杂乱而创建的service，实现生成二维码功能")
    @Autowired
    TradeService tradeService;

    /**
     * 初始化确认订单页面中的数据
     * this.axios.post("/order/generateConfirmOrder",
     *       Qs.stringify({ itemIds: constStore.itemids , productId: constStore.productId ,  skuid: constStore.skuId })
     *       如果是直接从商品详情页来到确认订单的话，id和skuid就有值，不然都是0
     * @Date 2021/11/29 14:52
     * @return null
     */
    @PostMapping("/generateConfirmOrder")
    public CommonResult generateConfirmOrder(
             OrderDTO orderDTO
    ){
        ConfirmOrderDTO confirmOrderDTO=orderService.generateConfirmOrder(orderDTO);
        return CommonResult.success(confirmOrderDTO);
    }


    /**
     *  生成订单(下单）
     * this.axios.post("/order/generateOrder")
     *
     * @Date 2021/11/30 18:44
     * @return
     */
    @RequestMapping(value="/generateOrder",method = RequestMethod.POST)
    public CommonResult generateOrder(@RequestBody OrderParamDTO paramDTO){
        Order order = orderService.generateOrder(paramDTO);
        return CommonResult.success(order.getId());
    }

    /**
     * 订单支付详情页面数据初始化
     * this.axios.get(`/order/orderDetail?orderId=${this.orderId}`)
     *
     * @Date 2021/12/1 15:18
     * @return null
     */
    @GetMapping("/orderDetail")
    public CommonResult getOrderDetail(
            @RequestParam("orderId") Long orderId
    ){
        OrderDetailDTO orderDetailDTO=orderService.getOrderDetail(orderId);
        return CommonResult.success(orderDetailDTO);
    }
    
    /**   
     * 我的订单页面数据初始化
     * this.axios.post('/order/list/userOrder',Qs.stringify({
     *       pageSize:10,
     *       pageNum:this.pageNum
     *      })
     * @Date 2021/12/1 20:43 
     * @return null 
     */
    @PostMapping("/list/userOrder")
    public CommonResult getListUserOrder(
            @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
            @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum
    ){
        Page page=orderService.getListUserOrder(pageNum,pageSize);
        return CommonResult.success(page);
    }
    
    

    /**
     * 生成一次性支付的二维码图片
     *  this.axios.post('/order/tradeQrCode',Qs.stringify({
     *      orderId:this.orderId,
     *      payType:1
     *      })
     * @Date 2021/12/2 15:09
     * @return null
     */
    @ApiOperation(value = "支付接口，目前只实现支付宝，微信暂未支付")
    @PostMapping("/tradeQrCode")
    public CommonResult tradeQrCode(
            @RequestParam("orderId") Long orderId,
            @RequestParam("payType") Integer payType
    ){
        if (payType!=2&&payType!=1){
            throw new ApiException("支付类型参数错误！");
        }
        String qrCodePath = tradeService.tradeQrCode(orderId, payType);
        if (!StrUtil.isEmpty(qrCodePath)){
            return CommonResult.success(qrCodePath);
        }else{
            return CommonResult.failed();
        }
    }

    /**
     *   当用户支付成功后，支付宝沙盒会自动发送Post请求设置好的路径(外网路径)方法，也就是这个方法,并且不需要返回结果给支付宝
     * POST("http://ouzo.gz2vip.91tunnel.com/order/paySuccess/{payType}")
     *      payType:1代表支付宝，2代表微信
     * @Date 2021/12/2 14:25
     * @return null
     */
    @PostMapping("/paySuccess/{payType}")
    public void paySuccess(
            @PathVariable("payType") Integer payType,
            HttpServletRequest request
    ) throws Exception {
        //获取支付宝的反馈信息，固定写法
        Map<String,String> params = new HashMap<>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = iter.next();
            //checkV1方法中两个参数都会自动去除，而CheckV2不会去除sign_type，所以要手动排除sign_type
            if(!name.toLowerCase().equals("sign_type")) {
                String[] values =  requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用
                //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }
        }

        //换支付宝公钥
        String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjccV4GAYQoWzuupvxHIUDzSZX6d16scbhqvc7coC0EUH3cyAmjnTSelF4oHShEw25XLcfFvzaWUjSc7p4sTCGCBN70ui1f3lJJK/eWf743fwr9jhaIKZ+LE1yNVP2CnJo8yCl8HY/qcdEj5Ry8obrbUjRJdNTg3AvxCmQb0p1XqGq6qpGxT4WHfqBOjDyZugX0QpxOcZfaXcCq073+U6HxV7fEoJmYwo4VzoXyJCzFQvd34d9pNDZdyWSa3zGYVY24QPxwFci1x7GOgiIdFLcWDHqCDTGF1Z5AGMRcwl4vXxi0ZYhBcYOqhv+4a0sdCihMnZ1lP+DEg5pSqjijx0HwIDAQAB";

        // 验签  ：去除sign和sign_type 参数 进行验签， checkV1 会在方法中两个参数都去除，而CheckV2不会去除sign_type，所以要手动排除
        boolean signVerified = AlipaySignature.rsaCheckV2(params, alipay_public_key, "utf-8","RSA2"); //调用SDK验证签名

        //——请在这里编写您的程序——
        if(signVerified) {
            // 订单id ,固定获取方式
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            if(StrUtil.isNotBlank(out_trade_no) && NumberUtil.isNumber(out_trade_no)){
                Long orderId=Long.parseLong(out_trade_no);
                orderService.paySuccess(orderId,payType);
                log.info("支付成功");
            }
        }else {
            System.out.println("验签失败");
        }


    }
}
